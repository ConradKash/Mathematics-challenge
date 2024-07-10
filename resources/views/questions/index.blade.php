@extends('layouts.app', ['activePage' => 'questions', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Uploads', 'activeButton' => 'laravel'])



@section('content')
<div class="container">
    <div class="card">
        <div class="card-header">
            <h4>Questions and Answers</h4>
        </div>
        <div class="card-body">
            @foreach ($questions as $question)
                <div class="mb-4">
                    <h5>Question:</h5>
                    <p>{{ $question->content }}</p>

                    @if($question->answers->isEmpty())
                        <p class="text-muted">No answers found for this question.</p>
                    @else
                        <h6>Answers:</h6>
                        <ul class="list-group">
                            @foreach ($question->answers as $answer)
                                <li class="list-group-item">
                                    <strong>Answer:</strong> {{ $answer->content }}<br>
                                    <strong>Marks:</strong> {{ $answer->marks }}
                                </li>
                            @endforeach
                        </ul>
                    @endif
                </div>
                <hr>
            @endforeach
        </div>
    </div>
</div>
@endsection


