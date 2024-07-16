<?php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Challenge;

class ChallengeController extends Controller
{
    public function index($name)
    {
        // Logic to handle 'page.index' route
        return view('pages.' . $name);
    }

    public function challenges()
    {
        $challenges = Challenge::all();
        // Logic to handle 'page.challenges' route
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

        return redirect()->route('page.challenges')->with('success', 'Challenge created successfully.');
    }

    public function show(Challenge $challenge)
    {
        return view('challenges.show', compact('challenge'));
    }

    public function edit($id)
    {
        // Fetch the Challenge instance from the database
        $challenge = Challenge::findOrFail($id);
    
        // Pass the Challenge instance to the view
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

        return redirect()->route('page.challenges')->with('success', 'Challenge updated successfully.');
    }

    public function destroy(Challenge $challenge)
    {
        $challenge->delete();

        return redirect()->route('page.challenges')->with('success', 'Challenge deleted successfully.');
    }
}
