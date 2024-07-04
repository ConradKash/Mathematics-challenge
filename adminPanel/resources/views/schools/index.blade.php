@extends('layouts.app', ['activePage' => 'dashboard', 'title' => 'Mathematics Challenge', 'navName' => 'Dashboard', 'activeButton' => 'laravel'])

@section('content')
<div class="content">
    <div class="row">
        <div class="col-md-12">
            <h1> Schools </h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <a href="{{ route('schools.create') }}" class="btn btn-primary">Add School</a>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table">
                <thead>
                    <tr>
                        <th> ID </th>
                        <th> Name </th>
                        <th> Address </th>
                        <th> Actions </th>
                    </tr>
                </thead>
                <tbody>
                    @foreach($schools as $school)
                    <tr>
                        <td> {{ $school->id }} </td>
                        <td> {{ $school->name }} </td>
                        <td> {{ $school->district }} </td>
                        <td>
                            <a href="" class="btn btn-success">Edit</a>
                            <form action="" method="POST" style="display: inline;">
                                @csrf
                                @method('DELETE')
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                    @endforeach
                </tbody>
            </table>
        </div>
@endsection