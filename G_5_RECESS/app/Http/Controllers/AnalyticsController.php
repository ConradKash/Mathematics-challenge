<?php

namespace App\Http\Controllers;

use App\Models\Challenge;
use App\Models\Participant;
use App\Models\ParticipantChallengeAttempt;
use App\Models\QuestionAnswerRecord;
use App\Models\School;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Carbon\Carbon; // Import Carbon from the Carbon namespace

class AnalyticsController extends Controller
{
    /**
     * Calculate and display average scores across challenges.
     *
     * @return \Illuminate\View\View
     */
    public function averageScores()
    {
        $averageScores = ParticipantChallengeAttempt::with('challenge')
                            ->selectRaw('challenge_id, avg(score) as average_score')
                            ->groupBy('challenge_id')
                            ->get();

        return view('analytics.averageScores', compact('averageScores'));
    }

    /**
     * Retrieve the most correctly answered questions.
     *
     * @return \Illuminate\View\View
     */
    public function mostCorrectlyAnsweredQuestions()
{
    // Aggregate data to find the most correctly answered questions
    $questions = QuestionAnswerRecord::withCount(['attempts as correct_answers' => function($query) {
            $query->where('score', '>', 0);
        }, 'attempts as total_attempts'])
        ->get()
        ->map(function($question) {
            $question->correct_percentage = ($question->total_attempts > 0) ?
                ($question->correct_answers / $question->total_attempts) * 100 : 0;
            return $question;
        })
        ->sortByDesc('correct_percentage')
        ->take(10);

    return view('analytics.correctansweredquestions', compact('questions'));
}


    /**
     * Retrieve school rankings based on total scores.
     *
     * @return \Illuminate\View\View
     */
    public function schoolRankings()
    {
        $topPerformingSchools = School::with(['attempts' => function ($query) {
            $query->where('completed', true);
        }])
        ->get()
        ->sortByDesc(function ($school) {
            return $school->attempts->avg('score');
        })
        ->take(10);

        return view('pages.schoolRankings', compact('topPerformingSchools'));
    }

    /**
     * Retrieve performance over time by year.
     *
     * @return \Illuminate\View\View
     */
    public function performanceOverTime()
{
    $years = range(Carbon::now()->year - 10, Carbon::now()->year); // Last 10 years
    $performanceData = [];

    foreach ($years as $year) {
        $yearlyData = ParticipantChallengeAttempt::whereYear('participant_challenge_attempts.created_at', $year)
            ->join('participants', 'participant_challenge_attempts.participant_id', '=', 'participants.participant_id')
            ->join('schools', 'participants.registration_number', '=', 'schools.registration_number')
            ->selectRaw('schools.name as school_name, AVG(participant_challenge_attempts.score) as average_score')
            ->groupBy('schools.name')
            ->get();

        foreach ($yearlyData as $data) {
            if (!isset($performanceData[$data->school_name])) {
                $performanceData[$data->school_name] = [];
            }
            $performanceData[$data->school_name][$year] = $data->average_score;
        }
    }

    // Ensure all years are present for each school
    foreach ($performanceData as $schoolName => &$scores) {
        foreach ($years as $year) {
            if (!isset($scores[$year])) {
                $scores[$year] = 0;
            }
        }
        ksort($scores); // Sort by year
    }

    // Prepare data for Chart.js
    $chartData = [
        'labels' => $years,
        'datasets' => array_map(function($schoolName, $scores) {
            return [
                'label' => $schoolName,
                'data' => array_values($scores),
                'borderColor' => 'rgba(' . rand(0, 255) . ', ' . rand(0, 255) . ', ' . rand(0, 255) . ', 1)',
                'backgroundColor' => 'rgba(' . rand(0, 255) . ', ' . rand(0, 255) . ', ' . rand(0, 255) . ', 0.2)',
                'fill' => false,
                'tension' => 0.1
            ];
        }, array_keys($performanceData), $performanceData)
    ];

    return view('analytics.performance', compact('chartData'));
}

    
    


    /**
     * Calculate percentage repetition of questions for a given participant across attempts.
     *
     * @param  int  $participantId
     * @return \Illuminate\View\View
     */
    public function questionRepetition($participantId)
    {
        $repetitionPercentage = ParticipantChallengeAttempt::where('participant_id', $participantId)
            ->select('question_id', DB::raw('COUNT(*) as attempts_count'))
            ->groupBy('question_id')
            ->orderByDesc('attempts_count')
            ->get();

        return view('analytics.questionRepetition', compact('participantId', 'repetitionPercentage'));
    }

    /**
     * Retrieve the list of worst performing schools for a given challenge.
     *
     * @param  int  $challengeId
     * @return \Illuminate\View\View
     */
    public function worstPerformingSchools($challengeId)
    {
        $worstPerformingSchools = School::select('schools.id', 'schools.name')
            ->leftJoin('participants', 'schools.registration_number', '=', 'participants.registration_number')
            ->leftJoin('participant_challenge_attempts', 'participants.participant_id', '=', 'participant_challenge_attempts.participant_id')
            ->where('participant_challenge_attempts.challenge_id', $challengeId) // Filter by challenge_id
            ->selectRaw('COUNT(DISTINCT participants.participant_id) as participant_count')
            ->selectRaw('SUM(participant_challenge_attempts.score) as total_score')
            ->groupBy('schools.id', 'schools.name')
            ->orderBy('total_score') // Order by total_score ascending to get worst-performing schools first
            ->get();
    
        $activeButton = 'worstPerformingSchools'; // Define the variable or set the appropriate value
    
        return view('analytics.worstPerformingSchools', compact('worstPerformingSchools'));
    }
    


    /**
     * Retrieve the list of best performing schools across all challenges.
     *
     * @return \Illuminate\View\View
     */
    public function bestPerformingSchools()
    {
        $bestPerformingSchools = School::select('schools.id', 'schools.name')
            ->leftJoin('participants', 'schools.registration_number', '=', 'participants.registration_number')
            ->leftJoin('participant_challenge_attempts', 'participants.participant_id', '=', 'participant_challenge_attempts.participant_id')
            ->selectRaw('COUNT(DISTINCT participants.participant_id) as participant_count')
            ->selectRaw('SUM(participant_challenge_attempts.score) as total_score')
            ->groupBy('schools.id', 'schools.name')
            ->orderByDesc('total_score')
            ->get();
    
        return view('analytics.bestPerformingSchools', compact('bestPerformingSchools'));
    }
    



    /**
     * Retrieve the list of participants with incomplete challenges.
     *
     * @return \Illuminate\View\View
     */
    public function incompleteChallenges()
    {
        // Fetch all participants with their challenge attempts
        $allParticipants = Participant::with('challengeAttempts')->get();

        // Fetch all challenges
        $allChallenges = Challenge::all();

        // Filter participants who haven't attempted any of the given challenges
        $incompleteParticipants = $allParticipants->filter(function ($participant) use ($allChallenges) {
            $attemptedChallenges = $participant->challengeAttempts->pluck('challenge_id')->toArray();
            $incompleteChallenges = $allChallenges->filter(function ($challenge) use ($attemptedChallenges) {
                return !in_array($challenge->id, $attemptedChallenges);
            });

            if ($incompleteChallenges->isNotEmpty()) {
                $participant->incomplete_challenges = $incompleteChallenges;
                return true;
            }
            return false;
        });

        return view('analytics.incompleteChallenges', compact('incompleteParticipants'));
    }


    public function schoolPerformance(Request $request)
{
    $registrationNumber = $request->input('registration_number');

    $years = range(Carbon::now()->year - 10, Carbon::now()->year); // Last 10 years
    $performanceData = [];

    foreach ($years as $year) {
        $yearlyData = ParticipantChallengeAttempt::whereYear('participant_challenge_attempts.created_at', $year)
            ->join('participants', 'participant_challenge_attempts.participant_id', '=', 'participants.participant_id')
            ->join('schools', 'participants.registration_number', '=', 'schools.registration_number')
            ->selectRaw('schools.name as school_name, AVG(participant_challenge_attempts.score) as average_score')
            ->where('schools.registration_number', $registrationNumber)
            ->groupBy('schools.name')
            ->get();

        foreach ($yearlyData as $data) {
            if (!isset($performanceData[$data->school_name])) {
                $performanceData[$data->school_name] = [];
            }
            $performanceData[$data->school_name][$year] = $data->average_score;
        }
    }

    // Ensure all years are present for each school
    foreach ($performanceData as $schoolName => &$scores) {
        foreach ($years as $year) {
            if (!isset($scores[$year])) {
                $scores[$year] = 0;
            }
        }
        ksort($scores); // Sort by year
    }

    // Prepare data for Chart.js
    $chartData = [
        'labels' => $years,
        'datasets' => array_map(function($schoolName, $scores) {
            return [
                'label' => $schoolName,
                'data' => array_values($scores),
                'borderColor' => 'rgba(' . rand(0, 255) . ', ' . rand(0, 255) . ', ' . rand(0, 255) . ', 1)',
                'backgroundColor' => 'rgba(' . rand(0, 255) . ', ' . rand(0, 255) . ', ' . rand(0, 255) . ', 0.2)',
                'fill' => false,
                'tension' => 0.1
            ];
        }, array_keys($performanceData), $performanceData)
    ];

    return view('analytics.performance', compact('chartData'));
}


    // Add more analytics methods as per your project's requirements
}
