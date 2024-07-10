@extends('layouts.app', ['activePage' => 'CHALLENGES', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'challenges', 'activeButton' => 'laravel'])

@section('content')
<div class="content">
    <div class="container-fluid">
        <h1>Challenges</h1>
        <a href="{{ route('challenges.create') }}" class="btn btn-primary">Create Challenge</a>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Duration</th>
                        <th>Number of Questions</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    @foreach($challenges as $challenge)
                    <tr>
                        <td>{{ $challenge->name }}</td>
                        <td>{{ $challenge->description }}</td>
                        <td>{{ $challenge->start_date }}</td>
                        <td>{{ $challenge->end_date }}</td>
                        <td>{{ $challenge->duration }}</td>
                        <td>{{ $challenge->number_of_questions }}</td>
                        <td>
                            <a href="{{ route('challenges.show', $challenge->id) }}" class="btn btn-info">View</a>
                            <a href="{{ route('challenges.edit', $challenge->id) }}" class="btn btn-warning">Edit</a>
                            <form action="{{ route('challenges.destroy', $challenge->id) }}" method="POST" style="display:inline-block;">
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
    </div>
</div>
@endsection
