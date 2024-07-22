<!DOCTYPE html>
<html>
<head>
    <title>Participant Report</title>
</head>
<body>
    <h1>Participant Report for {{ $participant->name }}</h1>
    <p>School: {{ $participant->school->name }}</p>

    <h2>Challenge Attempts</h2>
    <table>
        <thead>
            <tr>
                <th>Challenge Title</th>
                <th>Question</th>
                <th>Correct Answer</th>
                <th>Participant Answer</th>
                <th>Marks</th>
            </tr>
        </thead>
        <tbody>
            @foreach($correctAnswers as $answer)
                <tr>
                    <td>{{ $answer['question']->challenge->title }}</td>
                    <td>{{ $answer['question']->text }}</td>
                    <td>{{ $answer['correct_answer'] }}</td>
                    <td>{{ $answer['participant_answer'] }}</td>
                    <td>{{ $answer['marks'] }}</td>
                </tr>
            @endforeach
        </tbody>
    </table>

    <h3>Total Score: {{ $totalScore }}</h3>

    <h2>View Reports</h2>
    <table>
        <thead>
            <tr>
                <th>Challenge ID</th>
                <th>Challenge Title</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            @foreach($viewReports as $viewReport)
                <tr>
                    <td>{{ $viewReport->challenge_id }}</td>
                    <td>{{ $viewReport->challenge->title }}</td>
                    <td>{{ $viewReport->is_completed ? 'Completed' : 'Incomplete' }}</td>
                </tr>
            @endforeach
        </tbody>
    </table>
</body>
</html>
