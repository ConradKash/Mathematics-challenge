@extends('layouts.app', ['activePage' => 'questions', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Uploads', 'activeButton' => 'laravel'])


@section('content')
<div class="container">
    <div class="card">
        <div class="card-header">
            <h4>Questions and Answers</h4>
        </div>
        <div class="card-body">
            @if (count($questions) > 0)
            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Question</th>
                            <th>Answer</th>
                            <th>Score</th>
                        </tr>
                    </thead>
                    <tbody>
                        @foreach ($questions as $question)
                        <tr>
                            <td>{{ $question->question }}</td>
                            <td>{{ $question->answer }}</td>
                            <td>{{ $question->score }}</td>
                        </tr>
                        @endforeach
                    </tbody>
                </table>
            </div>
            @else
            <p>No questions and answers found.</p>
            @endif
        </div>
    </div>
</div>
@endsection
