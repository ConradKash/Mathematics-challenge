<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\SchoolRepresentative;


class SchoolRespresentativesController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');
    }

    public function index()
    {
        $arr['schoolRepresentative'] = SchoolRepresentative::all();
        return view('schools.schoolRepresentatives.index')->with($arr);
    }

    public function add()
    {
        return view('schools.createSchool');
    }
    public function save()
    {
        dump($_POST);
        // $school = new School();
        // $school->name = $request->name;
        // $school->address = $request->address;
        // $school->save();
        // return redirect()->route('list_schools');
    }
    public function exchange() {
        
    }
}
