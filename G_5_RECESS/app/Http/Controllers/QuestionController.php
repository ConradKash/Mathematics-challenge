<?php

namespace App\Http\Controllers;

use App\Models\QuestionAnswerRecord;
use Illuminate\Http\Request;
use Maatwebsite\Excel\Facades\Excel; // Assuming you're using Maatwebsite Excel package

class QuestionController extends Controller
{
    // Method to load the view for questions and answers
    public function questAnswer()
    {
        $questions = QuestionAnswerRecord::paginate(10); 
        $title = "Questions and Answers";
        $questions = QuestionAnswerRecord::all();
        return view('pages.questAnswer', compact('title', 'questions'));
    }

    // Method to fetch questions and their answers
    public function index()
    {
        $questions = QuestionAnswerRecord::paginate(10); 
        $questions = QuestionAnswerRecord::all();
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
            $answerRow = $answers[0]->firstWhere(0, $questionRow[0]);
            $question = QuestionAnswerRecord::create([
                'question' => $questionRow[0],
                'answer' => $answerRow[1],
                'score' => intval($answerRow[2]),
            ]);

            $answerRow = $answers[0]->firstWhere(0, $questionRow[0]);

            if ($answerRow) {
                $question->update([
                    'answer' => $answerRow[1],
                    'score' => intval($answerRow[2]), // Ensure score is an integer
                ]);
            }
        }

        return redirect()->route('questions.index')->with('success', 'Questions and answers uploaded successfully.');
    }
}
