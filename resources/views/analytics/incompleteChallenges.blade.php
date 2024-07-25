<!DOCTYPE html>
<html>
<head>
    <title>Participants with Incomplete Challenges</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1>Participants with Incomplete Challenges</h1>
        @if ($incompleteParticipants->isEmpty())
            <p>No participants with incomplete challenges found.</p>
        @else
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Incomplete Challenges</th>
                    </tr>
                </thead>
                <tbody>
                    @foreach ($incompleteParticipants as $participant)
                        <tr>
                            <td>{{ $participant->username }}</td>
                            <td>{{ $participant->firstname }}</td>
                            <td>{{ $participant->lastname }}</td>
                            <td>{{ $participant->emailAddress }}</td>
                            <td>
                                <ul>
                                    @foreach ($participant->incomplete_challenges as $challenge)
                                        <li>
                                            {{ $challenge->title }} (ID: {{ $challenge->challenge_id }})
                                        </li>
                                    @endforeach
                                </ul>
                            </td>
                        </tr>
                    @endforeach
                </tbody>
            </table>
        @endif
    </div>
</body>
</html>
