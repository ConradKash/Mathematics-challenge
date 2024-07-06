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
        $schools = School::all();
        $schoolRepresentatives = SchoolRepresentative::all();
        $schoolRepresentatives = SchoolRepresentative::select('school_representatives.*', 'schools.name as school_name')
            ->join('schools', 'schools.id', '=', 'school_representatives.school_id')
            ->get();

        return view('schools.index', compact('schools', 'schoolRepresentatives'));
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

    public function edit($id)
    {
        $school = School::find($id);
        $schoolRepresentative = new SchoolRepresentative;
        $schoolRepresentative->where('school_id', $id)->first();
        return view('schools.editSchool', compact('schoolRepresentative'));
    }

    public function update(Request $request, $id)
    {
        $school = School::find($id);
        $school->name = $request->name;
        $school->district = $request->district;
        $school->save();
        $schoolRepresentative = new SchoolRepresentative;
        $schoolRepresentative->where('school_id', $id)->first();
        $schoolRepresentative->name = $request->name;
        $schoolRepresentative->email = $request->email;
        $schoolRepresentative->phone = $request->phone;
        $schoolRepresentative->school_id = $school->id;
        $schoolRepresentative->password = $request->password;
        $schoolRepresentative->save();
        return redirect()->route('list_schools');
    }

    public function delete($id)
    {
        $school = School::find($id);
        if ($school != null) {
            $school->delete();
            return redirect()->route('list_schools');
        }
        return redirect()->route('list_schools');
    }
}
