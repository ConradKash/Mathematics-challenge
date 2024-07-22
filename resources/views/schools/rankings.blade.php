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
                    <th>Average Score</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($schools as $index => $school)
                @foreach ($school['attempts'] as $attempt)
                    <tr>
                        <td>{{ $index + 1 }}</td>
                                <td>{{ $school['name'] }}</td>
                                <td>{{ $attempt->challenge->title }}</td>
                                <td>{{ $attempt->challenge->challenge_id }}</td>
                                <td>{{ number_format($attempt->score, 2) }}</td>
                            </tr>
                        @endforeach
                    @endforeach
                
            </tbody>
        </table>
    </div>
@endsection
