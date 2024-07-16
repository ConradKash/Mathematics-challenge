@extends('layouts.app', ['activePage' => 'edit', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'edit', 'activeButton' => 'laravel'])

@section('content')
    <h1>Edit Challenge</h1>
    <form action="{{ route('challenges.update', $challenge->id) }}" method="POST">
        @csrf
        @method('PUT')
        <div class="form-group">
            <label for="title">Title</label>
            <input type="text" name="title" class="form-control" value="{{ $challenge->title }}" required>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea name="description" class="form-control" required>{{ $challenge->description }}</textarea>
        </div>
        <div class="form-group">
            <label for="starting_date">Starting Date</label>
            <input type="date" name="starting_date" class="form-control" value="{{ $challenge->starting_date }}" required>
        </div>
        <div class="form-group">
            <label for="closing_date">Closing Date</label>
            <input type="date" name="closing_date" class="form-control" value="{{ $challenge->closing_date }}" required>
        </div>
        <div class="form-group">
            <label for="duration_minutes">Duration (minutes)</label>
            <input type="number" name="duration_minutes" class="form-control" value="{{ $challenge->duration_minutes }}" required>
        </div>
        <button type="submit" class="btn btn-primary">Update</button>
    </form>
@endsection
