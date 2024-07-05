@extends('layouts.app', ['activePage' => 'dashboard', 'title' => 'Mathematics Challenge', 'navName' => 'Dashboard', 'activeButton' => 'laravel'])

@section('content')
<div class="content">
    <div class="row">
        <div class="col-md-12">
            <h1> Schools </h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="card strpied-tabled-with-hover">
                <div class="card-header ">
                    <h4 class="card-title">Schools </h4>
                    <p class="card-category">This table displays a list of schools, including their names and addresses. It also provides options to edit or delete each school entry.</p>
                </div>
                <div class="card-body table-full-width table-responsive">
                    <table class="table table-hover table-striped">
                        <thead>
                            <tr>
                                <th> Name </th>
                                <th> Address </th>
                                <th> Actions </th>
                                <th> Delete </th>
                            </tr>
                        </thead>
                        <tbody>
                            @foreach($schools as $school)
                            <tr>
                                <td> {{ $school->name }} </td>
                                <td> {{ $school->district }} </td>
                                <td>
                                    <a href="{{ route('schools.edit', $school->id) }}" class="btn btn-success">Edit</a>
                                </td>
                                <td>
                                    <form action="{{ route('schools.delete', $school->id) }}" method="POST">
                                        @csrf
                                        @method('DELETE')
                                        <button type="submit" class="btn btn-danger">Delete</button>
                                    </form>
                                </td>
                            </tr>
                            @endforeach
                        </tbody>
                    </table>
                    <div class="col">
                        <a href="{{ route('schools.create') }}" class="btn btn-primary">Add School</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card strpied-tabled-with-hover">
                <div class="card-header ">
                    <h4 class="card-title">School Representatives</h4>
                    <p class="card-category">Here is a subtitle for this table</p>
                </div>
                <div class="card-body table-full-width table-responsive">
                    <table class="table table-hover table-striped">
                        <thead>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Contact</th>
                            <th>School</th>
                        </thead>
                        <tbody>
                            @foreach($schoolRepresentatives as $schoolschoolRepresentative)
                            <td> {{ $schoolschoolRepresentative->name }} </td>
                            <td> {{ $schoolschoolRepresentative->email }} </td>
                            <td> {{ $schoolschoolRepresentative->phone }} </td>
                            <td> {{ $schoolschoolRepresentative->school_name }} </td>
                            </tr>
                            @endforeach
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    @endsection