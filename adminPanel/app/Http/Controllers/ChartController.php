<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Participant;
use App\Models\Challenge;
use App\Models\ParticipantAttempt;

class ChartController extends Controller
{
    public function index()
    {
        $participants = Participant::all();
        $participantAttempts = ParticipantAttempt::all();
        $challenges = Challenge::all();

        $participantAttemptScores = [];
        $participantAttemptTimeTaken = [];
        $challengeScores = [];
        $challengeTimeTaken = [];

        foreach ($participants as $participant) {
            $participantAttemptScores[$participant->id] = $participant->participantAttempts->sum('score');
            $participantAttemptTimeTaken[$participant->id] = $participant->participantAttempts->sum('timeTaken');
        }

        foreach ($challenges as $challenge) {
            $challengeScores[$challenge->id] = $challenge->participantAttempts->sum('score');
            $challengeTimeTaken[$challenge->id] = $challenge->participantAttempts->sum('timeTaken');
        }

        return view('chart', [
            'participantAttemptScores' => $participantAttemptScores,
            'participantAttemptTimeTaken' => $participantAttemptTimeTaken,
            'challengeScores' => $challengeScores,
            'challengeTimeTaken' => $challengeTimeTaken,
        ]);
    }

    public function pieChart()
    {

    }
}
