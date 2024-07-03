<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\School;

class SchoolsController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');
    }

    public function index()
    {
        $arr['schools'] = School::all();
        return view('schools.index')->with($arr);
    }

    public function add()
    {
        return view('schools.createSchool');
    }
    public function save()
    {
        // $school = new School();
        // $school->name = $request->name;
        // $school->address = $request->address;
        // $school->save();
        // return redirect()->route('list_schools');
    }
}
