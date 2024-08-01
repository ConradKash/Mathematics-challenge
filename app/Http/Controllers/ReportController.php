<?php

namespace App\Http\Controllers;

use App\Models\Participant;
use App\Models\ParticipantChallengeAttempt;
use App\Models\ViewReport;
use Illuminate\Support\Facades\Mail;
use Barryvdh\DomPDF\Facad\PDF;
use App\Mail\ReportMail;

class ReportController extends Controller
{
    // Existing code...

    public function showReport($participantId)
    {
        // Generate report data
        $data = $this->generateReport($participantId);

        // Return view with the data
        return view('reports.participantReport', $data);
    }

    // Existing code...

    private function generateReport($participantId)
    {
        // Retrieve participant's details and their attempts
        $participant = Participant::with('school')->find($participantId);
        $attempts = ParticipantChallengeAttempt::where('participant_id', $participantId)
            ->with(['challenge', 'challenge.questionAnswerRecords'])
            ->get();

        if (!$participant || $attempts->isEmpty()) {
            abort(404, 'Participant or attempts not found');
        }

        // Retrieve view report for participant's challenges
        $viewReports = ViewReport::where('participant_id', $participantId)->get();

        // Compile data into an array
        return [
            'participant' => $participant,
            'attempts' => $attempts,
            'viewReports' => $viewReports,
            'correctAnswers' => $this->getCorrectAnswers($attempts),
            'totalScore' => $this->calculateTotalScore($attempts),
            'averageScore' => $this->calculateAverageScore($attempts),
            'highestScore' => $this->getHighestScore($attempts),
            'lowestScore' => $this->getLowestScore($attempts),
            'totalAttempts' => $this->getTotalAttempts($attempts),
            'completedAttempts' => $this->getCompletedAttempts($attempts),
            'incompleteAttempts' => $this->getIncompleteAttempts($attempts),
            'passingAttempts' => $this->getPassingAttempts($attempts),
            'failingAttempts' => $this->getFailingAttempts($attempts),
            'passingPercentage' => $this->getPassingPercentage($attempts),
            'failingPercentage' => $this->getFailingPercentage($attempts),
            'averageTimeTaken' => $this->calculateAverageTimeTaken($attempts),
            'totalTimeTaken' => $this->calculateTotalTimeTaken($attempts),
            'averageMarksPerAttempt' => $this->calculateAverageMarksPerAttempt($attempts),
            'highestMarksPerAttempt' => $this->getHighestMarksPerAttempt($attempts),
            'lowestMarksPerAttempt' => $this->getLowestMarksPerAttempt($attempts),
            'averageMarksPerQuestion' => $this->calculateAverageMarksPerQuestion($attempts),
            'highestMarksPerQuestion' => $this->getHighestMarksPerQuestion($attempts),
            'lowestMarksPerQuestion' => $this->getLowestMarksPerQuestion($attempts),
        ];
    }

    private function getCorrectAnswers($attempts)
    {
        // Existing code...
    }

    private function calculateTotalScore($attempts)
    {
        // Existing code...
    }

    // New functions...

    private function calculateAverageScore($attempts)
    {
        $totalScore = $this->calculateTotalScore($attempts);
        $totalAttempts = $this->getTotalAttempts($attempts);

        if ($totalAttempts > 0) {
            return $totalScore / $totalAttempts;
        }

        return 0;
    }

    private function getHighestScore($attempts)
    {
        $highestScore = 0;

        foreach ($attempts as $attempt) {
            $questionAnswerRecord = $attempt->challenge->questionAnswerRecords->first();

            if ($questionAnswerRecord && $attempt->participant_answer == $questionAnswerRecord->answer) {
                $score = $questionAnswerRecord->score;

                if ($score > $highestScore) {
                    $highestScore = $score;
                }
            }
        }

        return $highestScore;
    }

    private function getLowestScore($attempts)
    {
        $lowestScore = PHP_INT_MAX;

        foreach ($attempts as $attempt) {
            $questionAnswerRecord = $attempt->challenge->questionAnswerRecords->first();

            if ($questionAnswerRecord && $attempt->participant_answer == $questionAnswerRecord->answer) {
                $score = $questionAnswerRecord->score;

                if ($score < $lowestScore) {
                    $lowestScore = $score;
                }
            }
        }

        return $lowestScore === PHP_INT_MAX ? 0 : $lowestScore;
    }

    private function getTotalAttempts($attempts)
    {
        return count($attempts);
    }

    private function getCompletedAttempts($attempts)
    {
        $completedAttempts = 0;

        foreach ($attempts as $attempt) {
            if ($attempt->is_completed) {
                $completedAttempts++;
            }
        }

        return $completedAttempts;
    }

    private function getIncompleteAttempts($attempts)
    {
        $incompleteAttempts = 0;

        foreach ($attempts as $attempt) {
            if (!$attempt->is_completed) {
                $incompleteAttempts++;
            }
        }

        return $incompleteAttempts;
    }

    private function getPassingAttempts($attempts)
    {
        $passingAttempts = 0;

        foreach ($attempts as $attempt) {
            $questionAnswerRecord = $attempt->challenge->questionAnswerRecords->first();

            if ($questionAnswerRecord && $attempt->participant_answer == $questionAnswerRecord->answer) {
                $score = $questionAnswerRecord->score;

                if ($score > 0) {
                    $passingAttempts++;
                }
            }
        }

        return $passingAttempts;
    }

    private function getFailingAttempts($attempts)
    {
        $failingAttempts = 0;

        foreach ($attempts as $attempt) {
            $questionAnswerRecord = $attempt->challenge->questionAnswerRecords->first();

            if ($questionAnswerRecord && $attempt->participant_answer == $questionAnswerRecord->answer) {
                $score = $questionAnswerRecord->score;

                if ($score === 0) {
                    $failingAttempts++;
                }
            }
        }

        return $failingAttempts;
    }

    private function getPassingPercentage($attempts)
    {
        $passingAttempts = $this->getPassingAttempts($attempts);
        $totalAttempts = $this->getTotalAttempts($attempts);

        if ($totalAttempts > 0) {
            return ($passingAttempts / $totalAttempts) * 100;
        }

        return 0;
    }

    private function getFailingPercentage($attempts)
    {
        $failingAttempts = $this->getFailingAttempts($attempts);
        $totalAttempts = $this->getTotalAttempts($attempts);

        if ($totalAttempts > 0) {
            return ($failingAttempts / $totalAttempts) * 100;
        }

        return 0;
    }

    private function calculateAverageTimeTaken($attempts)
    {
        $totalTimeTaken = $this->calculateTotalTimeTaken($attempts);
        $totalAttempts = $this->getTotalAttempts($attempts);

        if ($totalAttempts > 0) {
            return $totalTimeTaken / $totalAttempts;
        }

        return 0;
    }

    private function calculateTotalTimeTaken($attempts)
    {
        $totalTimeTaken = 0;

        foreach ($attempts as $attempt) {
            $totalTimeTaken += $attempt->time_taken;
        }

        return $totalTimeTaken;
    }

    private function calculateAverageMarksPerAttempt($attempts)
    {
        $totalScore = $this->calculateTotalScore($attempts);
        $totalAttempts = $this->getTotalAttempts($attempts);

        if ($totalAttempts > 0) {
            return $totalScore / $totalAttempts;
        }

        return 0;
    }

    private function getHighestMarksPerAttempt($attempts)
    {
        $highestMarks = 0;

        foreach ($attempts as $attempt) {
            $questionAnswerRecord = $attempt->challenge->questionAnswerRecords->first();

            if ($questionAnswerRecord && $attempt->participant_answer == $questionAnswerRecord->answer) {
                $score = $questionAnswerRecord->score;

                if ($score > $highestMarks) {
                    $highestMarks = $score;
                }
            }
        }

        return $highestMarks;
    }

    private function getLowestMarksPerAttempt($attempts)
    {
        $lowestMarks = PHP_INT_MAX;

        foreach ($attempts as $attempt) {
            $questionAnswerRecord = $attempt->challenge->questionAnswerRecords->first();

            if ($questionAnswerRecord && $attempt->participant_answer == $questionAnswerRecord->answer) {
                $score = $questionAnswerRecord->score;

                if ($score < $lowestMarks) {
                    $lowestMarks = $score;
                }
            }
        }

        return $lowestMarks === PHP_INT_MAX ? 0 : $lowestMarks;
    }

    private function calculateAverageMarksPerQuestion($attempts)
    {
        $totalScore = $this->calculateTotalScore($attempts);
        $totalQuestions = $this->getTotalQuestions($attempts);

        if ($totalQuestions > 0) {
            return $totalScore / $totalQuestions;
        }

        return 0;
    }

    private function getHighestMarksPerQuestion($attempts)
    {
        $highestMarks = 0;

        foreach ($attempts as $attempt) {
            $questionAnswerRecord = $attempt->challenge->questionAnswerRecords->first();

            if ($questionAnswerRecord && $attempt->participant_answer == $questionAnswerRecord->answer) {
                $score = $questionAnswerRecord->score;

                if ($score > $highestMarks) {
                    $highestMarks = $score;
                }
            }
        }

        return $highestMarks;
    }

    private function getLowestMarksPerQuestion($attempts)
    {
        $lowestMarks = PHP_INT_MAX;

        foreach ($attempts as $attempt) {
            $questionAnswerRecord = $attempt->challenge->questionAnswerRecords->first();

            if ($questionAnswerRecord && $attempt->participant_answer == $questionAnswerRecord->answer) {
                $score = $questionAnswerRecord->score;

                if ($score < $lowestMarks) {
                    $lowestMarks = $score;
                }
            }
        }

        return $lowestMarks === PHP_INT_MAX ? 0 : $lowestMarks;
    }

    // Existing code...

    public function downloadReport($participantId)
    {
        // Existing code...
    }

    public function sendReport($participantId)
    {
        // Existing code...
    }
}
