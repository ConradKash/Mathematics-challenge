<!-- resources/views/school/rankings.blade.php -->

@extends('layouts.app', ['activePage' => 'Rankings', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Rankings', 'activeButton' => 'laravel'])

@section('content')
    <div class="container">
        <h1>School Rankings</h1>

        <table class="table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>School Name</th>
                    <th>District</th>
                    <th>Registration Number</th>
                    <th>Number of Participants</th>
                    <th>Average Score</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($schools as $index => $school)
                    <tr>
                        <td>{{ $index + 1 }}</td>
                        <td>{{ $school->name }}</td>
                        <td>{{ $school->district }}</td>
                        <td>{{ $school->registration_number }}</td>
                        <td>{{ $school->num_participants }}</td>
                        <td>{{ number_format($school->average_score, 2) }}</td>
                    </tr>
                @endforeach
            </tbody>
        </table>
    </div>
@endsection
