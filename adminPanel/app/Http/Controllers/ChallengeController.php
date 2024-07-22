<?php

namespace App\Http\Controllers;

use App\Models\Challenge;
use Illuminate\Http\Request;
use App\Http\Requests\UpdateChallengeRequest;
use Maatwebsite\Excel\Facades\Excel;
use App\Imports\challenges;


class ChallengeController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $challenges = Challenge::all();
        return view('challenges.index', compact('challenges'));
    }

    public function add()
    {
        return view('challenges.createChallenge');
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
    }

    /**
     * Store a newly created resource in storage.
     */
    public function save(Request $request)
    {
        /*$validatedData = $request->validate([
            
        
            'startDate' => 'required|date',
            'endDate' => 'required|date',
            'duration' => 'required|integer',
            
        ]);

        $challenge = new Challenge($validatedData); */
        $challenge = new Challenge();
        $challenge->duration = request()->get('challengeDuration');
        $challenge->startDate = request()->get('startDate');
        $challenge->endDate = request()->get('endDate');
        $challenge->questionCount = request()->get('questionCount');
        $challenge->save();
        // Validate the uploaded file
        $request->validate([
            'file' => 'required|mimes:xlsx,xls', // Validate file type  

        ]);
        // Retrieve the uploaded file
        $file = $request->file('file');
        // Process the Excel file to check if file exists in the request and prevents errors.
        Excel::import(new challenges($challenge->id), $file);
        // Solved
        //Redirect back with success message after validating
        return redirect()->route('challenges.index');
        //Redirect back with success message after validating
        return redirect()->back()->with('success', 'Successfully uploaded');
        //Handle any exceptions that occur during the import process
        return redirect()->back()->with('error', 'Error got' . $e->getMessage());
    }

    /**
     * Display the specified resource.
     */
    public function show(Challenge $challenge)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Challenge $challenge, $id)
    {
        $challenges = Challenge::find($id);
        return view('challenges.editChallenge');
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(UpdateChallengeRequest $request, Challenge $challenge, $id)
    {
        $challenge = Challenge::find($id);
        $challenge->startDate = request()->get('Start date');
        $challenge->endDate = request()->get('End date');
        $challenge->questionCount = request()->get('Number of questions');
        $challenge->save();
    }

    /**
     * Remove the specified resource from storage.
     */
    public function delete($id)
    {
        $challenges = Challenge::find($id);
        $challenges->delete();
        return redirect()->route('challenges.index');
    }
}
