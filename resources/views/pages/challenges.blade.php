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
                        <th>Title</th>
                        <th>Description</th>
                        <th>Starting Date</th>
                        <th>Closing Date</th>
                        <th>Duration (minutes)</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    @foreach($challenges as $challenge)
                    <tr>
                        <td>{{ $challenge->title }}</td>
                        <td>{{ $challenge->description }}</td>
                        <td>{{ $challenge->starting_date }}</td>
                        <td>{{ $challenge->closing_date }}</td>
                        <td>{{ $challenge->duration_minutes }}</td>
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
