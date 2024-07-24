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
        $result = DB::select(DB::raw("SELECT COUNT(attempts.id) AS attempts, challenges.id FROM attempts LEFT JOIN challenges ON challenges.id = attempts.challenge_id GROUP BY attempts.challenge_id;")->getValue(DB::getQueryGrammar()));
        $data = "";
        foreach ($result as $row) {
            $data .= "['" . $row->id . "', " . $row->attempts . "],";
        }
        $chartData = $data;
        return view('dashboard', compact('chartData'));
    }
}
