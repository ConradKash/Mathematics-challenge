<?php

namespace App\Http\Controllers;

use App\Models\Participant;
use App\Models\ParticipantChallengeAttempt;
use App\Models\ViewReport;
use Illuminate\Support\Facades\Mail;
use Barryvdh\DomPDF\Facade as PDF;
use App\Mail\ReportMail;

class ReportController extends Controller
{
    public function showReport($participantId)
    {
        // Generate report data
        $data = $this->generateReport($participantId);

        // Return view with the data
        return view('reports.participantReport', $data);
    }

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
        ];
    }

    private function getCorrectAnswers($attempts)
    {
        $correctAnswers = [];

        foreach ($attempts as $attempt) {
            // Get the first question answer record safely
            $questionAnswerRecord = $attempt->challenge->questionAnswerRecords->first();

            if ($questionAnswerRecord) {
                $correctAnswers[] = [
                    'question' => $questionAnswerRecord->question,
                    'correct_answer' => $questionAnswerRecord->answer,
                    'participant_answer' => $attempt->participant_answer,
                    'marks' => $questionAnswerRecord->score,
                ];
            } else {
                $correctAnswers[] = [
                    'question' => 'N/A',
                    'correct_answer' => 'N/A',
                    'participant_answer' => $attempt->participant_answer,
                    'marks' => 0,
                ];
            }
        }

        return $correctAnswers;
    }

    private function calculateTotalScore($attempts)
    {
        $totalScore = 0;

        foreach ($attempts as $attempt) {
            // Get the first question answer record safely
            $questionAnswerRecord = $attempt->challenge->questionAnswerRecords->first();

            if ($questionAnswerRecord && $attempt->participant_answer == $questionAnswerRecord->answer) {
                $totalScore += $questionAnswerRecord->score;
            }
        }

        return $totalScore;
    }

    public function downloadReport($participantId)
    {
        $data = $this->generateReport($participantId);

        // Load view and pass data to it
        $pdf = PDF::loadView('reports.participantReport', $data);

        // Return PDF download response
        return $pdf->download('competition_report.pdf');
    }

    public function sendReport($participantId)
    {
        $data = $this->generateReport($participantId);

        // Generate PDF for the report
        $pdf = PDF::loadView('reports.participantReport', $data)->output();

        // Attach PDF data to the mail
        $data['pdf'] = $pdf;

        Mail::to($data['participant']->email)->send(new ReportMail($data));

        return response()->json(['message' => 'Report sent successfully.']);
    }
}
