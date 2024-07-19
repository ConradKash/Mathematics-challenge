<?php
namespace App\Http\Controllers;
use App\Models\Question;
use Illuminate\Http\Request;
use Maatwebsite\Excel\Facades\Excel;
use App\Imports\challenges;
use App\Http\Requests\StoreQuestionRequest;
use App\Http\Requests\UpdateQuestionRequest;

class QuestionController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        return view('question-layout');
          
    }
    public function import(Request $request)
    {
    // Validate the uploaded file
    $request->validate([
     'file' => 'required|mimes:xlsx,xls',// Validate file type  
   
    ]);
    // Retrieve the uploaded file
    $file = $request->file('file');
    // Process the Excel file to check if file exists in the request and prevents errors.
    Excel::import(new challenges,$file);
    //Redirect back with success message after validating
    return redirect()->back()->with('success', 'Successfully uploaded');
    //Handle any exceptions that occur during the import process
    return redirect()->back()->with('error', 'Error got' . $e->getMessage());
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(StoreQuestionRequest $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show(Question $question)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Question $question)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(UpdateQuestionRequest $request, Question $question)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Question $question)
    {
        //
    }
}
