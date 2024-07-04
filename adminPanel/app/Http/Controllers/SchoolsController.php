<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\School;
use App\Models\SchoolRepresentative;

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
        $school = new School();
        $school->id = $school->id;
        $school->name = request()->get('schoolName');
        $school->district = request()->get('district');
        $school->save();
        $schoolRepresentative = new SchoolRepresentative();
        $schoolRepresentative->name = request()->get('name');
        $schoolRepresentative->email = request()->get('email');
        $schoolRepresentative->phone = request()->get('phone');
        $schoolRepresentative->school_id = $school->id;
        $schoolRepresentative->password = request()->get('password');
        $schoolRepresentative->save();
        return redirect()->route('list_schools');
    }

    public function update (Request $request, $id)
    {
        $school = School::find($id);
        $school->name = $request->name;
        $school->district = $request->district;
        $school->save();
        $schoolRepresentative = SchoolRepresentative::find($id);
        $schoolRepresentative->name = $request->name;
        $schoolRepresentative->email = $request->email;
        $schoolRepresentative->phone = $request->phone;
        $schoolRepresentative->school_id = $school->id;
        $schoolRepresentative->password = $request->password;
        $schoolRepresentative->save();
        return redirect()->route('list_schools');
    }

    public function edit($id)
    {
        $school = School::find($id);
        $schoolRepresentative = SchoolRepresentative::find($id);
        return view('schools.edit', compact('school', 'schoolRepresentative'));
    }

    public function delete($id)
    {
        $school = School::find($id);
        $school->delete();
        $schoolRepresentative = SchoolRepresentative::find($id);
        $schoolRepresentative->delete();
        return redirect()->route('list_schools');
    }
}
