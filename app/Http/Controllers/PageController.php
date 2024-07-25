<?php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\School;
use App\Models\Challenge;
use App\Models\ParticipantChallengeAttempt;

class PageController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index($page, $challengeId = null)
    {
        if (view()->exists("pages.{$page}")) {
            switch ($page) {
                case 'school':
                    // Fetch the schools data from the database
                    $schools = School::all();
                    // Pass the data to the view
                    return view("pages.{$page}", compact('schools'));
                
                case 'questAnswer':
                    $data['title'] = 'Your Quest Answer Page Title';
                    return view("pages.{$page}", $data);

                case 'setup':
                    return view("pages.{$page}");

                case 'challenges':
                    $challenges = Challenge::all();
                    // Pass the challenges to the view
                    return view("pages.{$page}", compact('challenges'));
                
                case 'averageScores':
                    // Use Eloquent to calculate average scores per challenge
                    $averageScores = ParticipantChallengeAttempt::with('challenge')
                                        ->selectRaw('challenge_id, avg(score) as average_score')
                                        ->groupBy('challenge_id')
                                        ->get();

                    return view("pages.{$page}", compact('averageScores'));
                
                case 'schoolRankings':
                    $topPerformingSchools = School::with(['participants.challengeAttempts.challenge'])
                        ->get()
                        ->map(function ($school) {
                            $attempts = $school->participants->flatMap->challengeAttempts;
                            $totalScore = $attempts->sum('score');
                            $count = $attempts->count();
                            $averageScore = $count ? $totalScore / $count : 0;
                
                            return [
                                'name' => $school->name,
                                'average_score' => $averageScore,
                                'attempts' => $attempts
                            ];
                        })
                        ->sortByDesc('average_score')
                        ->take(10);
                
                    return view("pages.{$page}", compact('topPerformingSchools'));

                case 'worstPerformingSchools':
                    if ($challengeId) {
                        $worstPerformingSchools = School::select('schools.id', 'schools.name')
                            ->leftJoin('participants', 'schools.registration_number', '=', 'participants.registration_number')
                            ->leftJoin('participant_challenge_attempts', 'participants.participant_id', '=', 'participant_challenge_attempts.participant_id')
                            ->where('participant_challenge_attempts.challenge_id', $challengeId) // Filter by challenge_id
                            ->selectRaw('COUNT(DISTINCT participants.participant_id) as participant_count')
                            ->selectRaw('SUM(participant_challenge_attempts.score) as total_score')
                            ->selectRaw('SUM(participant_challenge_attempts.score) / COUNT(DISTINCT participants.participant_id) as average_score') // Calculate average score
                            ->groupBy('schools.id', 'schools.name')
                            ->orderBy('average_score') // Order by average_score ascending to get worst-performing schools first
                            ->get();
        
                        $activeButton = 'worstPerformingSchools'; // Define the variable or set the appropriate value
        
                        return view('pages.worstPerformingSchools', compact('worstPerformingSchools', 'activeButton'));
                    }
                    return abort(404, 'Challenge ID is required for worst performing schools.');

                case 'analyticsAll':
                    // Fetch the necessary analytics data
                    $challenges = Challenge::all();
                    $averageScores = ParticipantChallengeAttempt::with('challenge')
                                    ->selectRaw('challenge_id, avg(score) as average_score')
                                    ->groupBy('challenge_id')
                                    ->get();

                    // You can add more analytics data here
                    
                    return view('pages.analyticsAll', compact('challenges', 'averageScores'));

                default:
                    return view("pages.{$page}");
            }
        }

        return abort(404);
    }
}
