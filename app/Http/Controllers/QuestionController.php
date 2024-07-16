<?php

namespace App\Http\Controllers;

use App\Models\Question;
use App\Models\Answer;
use Illuminate\Http\Request;
use Maatwebsite\Excel\Facades\Excel; // Assuming you're using Maatwebsite Excel package

class QuestionController extends Controller
{
    // Method to load the view for questions and answers
    public function questAnswer()
    {
        $title = "Questions and Answers";
        $questions = Question::with('answers')->get();
        return view('pages.questAnswer', compact('title', 'questions'));
    }

   

    // Method to fetch questions and thveir answers
    public function index()
    {
        $questions = Question::with('answers')->get();
        return view('questions.index', compact('questions'));
    }

    // Method to handle uploading of questions and answers via Excel
    public function store(Request $request)
    {
        $request->validate([
            'questions' => 'required|mimes:xlsx',
            'answers' => 'required|mimes:xlsx',
        ]);

        // Process the Excel files
        $questions = Excel::toCollection(null, $request->file('questions'));
        $answers = Excel::toCollection(null, $request->file('answers'));

        // Iterate through each question row and create Question and Answer models
        foreach ($questions[0] as $questionRow) {
            $question = Question::create(['content' => $questionRow[0]]);
            $answerRow = $answers[0]->firstWhere('0', $questionRow[0]);
 
            Answer::create([ 
                'question_id' => $question->id,
                'content' => $answerRow[1],
                'marks' => intval($answerRow[2]), // Ensure marks is an integer
            ]);
        }

        return redirect()->route('questions.index')->with('success', 'Questions and answers uploaded successfully.');
    }
}
