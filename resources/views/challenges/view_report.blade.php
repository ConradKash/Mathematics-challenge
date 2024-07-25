@extends('layouts.app', ['activePage' => 'edit', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'edit', 'activeButton' => 'laravel'])


@section('content')

    <h1>Challenge Report for {{ $challenge->title }}</h1>

    <table>
        <thead>
            <tr>
                <th>Participant</th>
                <th>Answers</th>
            </tr>
        </thead>
        <tbody>
            @foreach($participants as $participant_id => $attempts)
                @php
                    $participant = $attempts->first()->participant;
                @endphp
                <tr>
                    <td>{{ $participant->name }}</td>
                    <td>
                        @foreach($attempts as $attempt)
                          <!--  @if($attempt->question)
                                <p>{{ $attempt->question->text }}: {{ $attempt->answer }} ({{ $attempt->is_correct ? 'Correct' : 'Incorrect' }})</p> 
                            @else
                                <p>Question data not available for this attempt.</p>
                            @endif -->
                        @endforeach

                        @foreach($attempts as $attempt)
                            @if($attempt->attempt)
                                <p>{{ $attempt->attempt_id }}: {{ $attempt->score }} ({{ $attempt->total_score }})</p> 
                            @else
                                <p>Question data not available for this attempt.</p>
                            @endif 
                        @endforeach
                    </td>
                </tr>
            @endforeach
        </tbody>
    </table>

@endsection
