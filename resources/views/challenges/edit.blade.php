@extends('layouts.app', ['activePage' => 'edit', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'edit', 'activeButton' => 'laravel'])


@section('content')
    <h1>Edit Challenge</h1>
    <form action="{{ route('challenges.update', $challenge->id) }}" method="POST">
        @csrf
        @method('PUT')
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" name="name" class="form-control" value="{{ $challenge->name }}" required>
        </div>
        <div class="form-group">
            <label for="start_date">Start Date</label>
            <input type="date" name="start_date" class="form-control" value="{{ $challenge->start_date }}" required>
        </div>
        <div class="form-group">
            <label for="end_date">End Date</label>
            <input type="date" name="end_date" class="form-control" value="{{ $challenge->end_date }}" required>
        </div>
        <div class="form-group">
            <label for="duration">Duration (minutes)</label>
            <input type="number" name="duration" class="form-control" value="{{ $challenge->duration }}" required>
        </div>
        <div class="form-group">
            <label for="num_questions">Number of Questions</label>
            <input type="number" name="num_questions" class="form-control" value="{{ $challenge->num_questions }}" required>
        </div>
        <button type="submit" class="btn btn-primary">Update</button>
    </form>
@endsection
