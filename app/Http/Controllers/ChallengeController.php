<?php

namespace App\Http\Controllers;

use App\Models\Challenge;
use App\Models\Participant;
use App\Models\ParticipantChallengeAttempt;
use Barryvdh\DomPDF\Facade\PDF;
use Illuminate\Support\Facades\Mail;
use App\Mail\ChallengeResults;
use Illuminate\Http\Request;

class ChallengeController extends Controller
{
    public function index($name)
    {
        return view('pages.' . $name);
    }

    public function challenges()
    {
        $challenges = Challenge::all();
        return view('pages.challenges');
    }

    public function create()
    {
        return view('challenges.create');
    }

    public function store(Request $request)
    {
        $validatedData = $request->validate([
            'title' => 'required|string|max:255',
            'description' => 'nullable|string',
            'starting_date' => 'required|date',
            'closing_date' => 'required|date',
            'duration_minutes' => 'required|integer',
        ]);

        $challenge = new Challenge($validatedData);
        $challenge->save();

        return redirect()->route('pages.challenges')->with('success', 'Challenge created successfully.');
    }

    public function edit($id)
    {
        $challenge = Challenge::findOrFail($id);
        return view('challenges.edit', compact('challenge'));
    }

    public function update(Request $request, Challenge $challenge)
    {
        $validatedData = $request->validate([
            'title' => 'required|string|max:255',
            'description' => 'nullable|string',
            'starting_date' => 'required|date',
            'closing_date' => 'required|date',
            'duration_minutes' => 'required|integer',
        ]);

        $challenge->update($validatedData);

        return redirect()->route('pages.challenges')->with('success', 'Challenge updated successfully.');
    }

    public function destroy(Challenge $challenge)
    {
        $challenge->delete();

        return redirect()->route('pages.challenges')->with('success', 'Challenge deleted successfully.');
    }

    public function sendChallengeReports($challenge_id)
    {
        $challenge = Challenge::with(['attempts.participant'])->findOrFail($challenge_id);

        foreach ($challenge->attempts->unique('participant_id') as $attempt) {
            $participant = $attempt->participant;
            
            // Generate PDF for each participant with their attempts
            $pdf = PDF::loadView('reports.challenge_results', ['attempts' => $challenge->attempts->where('participant_id', $participant->id)]);

            // Send email with PDF attached
            Mail::to($participant->emailAddress)->send(new ChallengeResults($pdf));
        }

        return back()->with('success', 'Challenge reports sent to all participants.');
    }

    public function viewChallengeReport($challenge_id)
    {
        $challenge = Challenge::with('attempts.participant')->findOrFail($challenge_id);

        $participants = $challenge->attempts->groupBy('participant_id');

        return view('challenges.view_report', compact('challenge', 'participants'));
    }

    public function getTopWinners($challenge_id)
    {
        $winners = ParticipantChallengeAttempt::where('challenge_id', $challenge_id)
                    ->orderBy('score', 'desc')
                    ->take(2)
                    ->get();

        return view('challenges.winners', compact('winners'));
    }
}
