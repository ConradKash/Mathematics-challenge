<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\School;
use App\Models\Participant;
use App\Models\ParticipantChallengeAttempt;

class SchoolController extends Controller
{

    public function school()
{
    $activePage = 'school'; 
    $schools = School::all();
    return view('pages.school', compact('schools', 'activePage'));
}

   

    public function createSchool()
    {
        return view('schools.createSchool');
    }

    public function store(Request $request)
{
    $request->validate([
        'name' => 'required|string',
        'district' => 'required|string',
        'registration_number' => 'required|string|unique:schools',
        'representative_email' => 'nullable|email',
        'representative_name' => 'nullable|string',
        'validated' => 'nullable|boolean',  // Add validation rule for validated
    ]);

    $data = $request->all();
    $data['validated'] = $request->has('validated') ? 1 : 0;

    School::create($data);

    return redirect()->route('page.school')
        ->with('success', 'School created successfully.');
}

    public function edit(School $school)
    {
        return view('schools.edit', compact('school'));
    }

    public function update(Request $request, School $school)
    {
        $request->validate([
            'name' => 'required|string',
            'district' => 'required|string',
            'registration_number' => 'required|string|unique:schools,registration_number,'.$school->id,
            'representative_email' => 'nullable|email',
            'representative_name' => 'nullable|string',
        ]);

        $school->update($request->all());

        return redirect()->route('page.school')
            ->with('success', 'School updated successfully.');
    }

    public function destroy(School $school)
    {
        $school->delete();

        return redirect()->route('page.school')
            ->with('success', 'School deleted successfully.');
    }
    public function rankings()
    {
        // Fetch schools with their related participants and attempts, and calculate the average score
        $schools = School::with(['participants.attempts'])
            ->get()
            ->map(function ($school) {
                $totalScore = 0;
                $totalAttempts = 0;
                $numParticipants = $school->participants->count();

                foreach ($school->participants as $participant) {
                    foreach ($participant->attempts as $attempt) {
                        $totalScore += $attempt->score;
                        $totalAttempts++;
                    }
                }

                $school->average_score = $totalAttempts ? $totalScore / $totalAttempts : 0;
                $school->num_participants = $numParticipants;
                return $school;
            })
            ->sortByDesc('average_score')
            ->values(); // Reset the keys to ensure correct indexing in the view

        return view('schools.rankings', compact('schools'));
    }
}

