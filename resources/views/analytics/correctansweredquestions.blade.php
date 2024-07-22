@extends('layouts.app', ['activePage' => 'Most', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Most', 'activeButton' => 'laravel'])

@section('content')
    <h1>Most Correctly Answered Questions</h1>
    <table>
        <thead>
            <tr>
                <th>Question</th>
                <th>Correct Answers</th>
                <th>Total Attempts</th>
                <th>Correct Percentage</th>
            </tr>
        </thead>
        <tbody>
            @foreach ($questions as $question)
                <tr>
                    <td>{{ $question->question }}</td>
                    <td>{{ $question->correct_answers }}</td>
                    <td>{{ $question->total_attempts }}</td>
                    <td>{{ number_format($question->correct_percentage, 2) }}%</td>
                </tr>
            @endforeach
        </tbody>
    </table>
@endsection
