<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class HomeController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Contracts\Support\Renderable
     */
    public function index()
    {
        $topQuestions = DB::select(DB::raw("
            SELECT attempts.question_id AS id, COUNT(attempts.question_id)  AS correct_answers, question_answer_records.question AS question 
            FROM attempts, question_answer_records
            WHERE is_correct = 1 
            GROUP BY attempts.question_id 
            ORDER BY correct_answers 
            DESC LIMIT 5;
        "));
        $dataPie = "";
        foreach ($topQuestions as $question) {
            $dataPie .= "['" . $question->question . "', " . $question->correct_answers . "],";
        }
        $chartData = $dataPie;

        $studentCountBySchool = DB::select(DB::raw("
            SELECT s.name AS school_name, COUNT(p.participant_id) AS student_count, SUM(a.is_correct) AS total_marks 
            FROM schools s 
            LEFT JOIN participants p 
            ON p.registration_number = s.registration_number 
            LEFT JOIN attempts a 
            ON p.participant_id = a.participant_id 
            GROUP BY s.registration_number;
            "));
        $dataBar = "";
        foreach ($studentCountBySchool as $school) {
            if ($school->total_marks == NULL) {
                $school->total_marks = 0;
            }
            $dataBar .= "['" . $school->school_name . "', " . $school->student_count . ", " . $school->total_marks . "],";
        }
        $chartData2 = $dataBar;

        $studentCountByGender = DB::select(DB::raw("
            SELECT s.name AS school_name, COUNT(p.participant_id) AS student_count, SUM(a.is_correct) AS total_marks 
            FROM schools s 
            LEFT JOIN participants p 
            ON p.registration_number = s.registration_number 
            LEFT JOIN attempts a 
            ON p.participant_id = a.participant_id 
            GROUP BY s.registration_number;
            "));
        $dataBar1 = "";
        foreach ($studentCountBySchool as $school) {
            if ($school->total_marks == NULL) {
                $school->total_marks = 0;
            }
            $dataBar1 .= "['" . $school->school_name . "', " . $school->student_count . ", " . $school->total_marks . "],";
        }
        $chartData3 = $dataBar;
        return view('dashboard', compact('chartData', 'chartData2'));
    }

}
