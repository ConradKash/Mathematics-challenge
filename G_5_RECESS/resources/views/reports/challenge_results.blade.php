<!DOCTYPE html>
<html>
<head>
    <title>Challenge Results</title>
</head>
<body>
    <h1>Challenge Results</h1>
    <table>
        <thead>
            <tr>
                <th>Participant</th>
                <th>Question</th>
                <th>Answer</th>
                <th>Correct</th>
            </tr>
        </thead>
        <tbody>
            @foreach($attempts as $attempt)
                @foreach($attempt->answers as $answer)
                    <tr>
                        <td>{{ $attempt->participant->name }}</td>
                        <td>{{ $answer->question->text }}</td>
                        <td>{{ $answer->answer }}</td>
                        <td>{{ $answer->is_correct ? 'Yes' : 'No' }}</td>
                    </tr>
                @endforeach
            @endforeach
        </tbody>
    </table>
</body>
</html>
