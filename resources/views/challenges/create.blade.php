@extends('layouts.app', ['activePage' => 'challenges', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Challenges', 'activeButton' => 'laravel'])

@section('content')
<div class="content">
    <div class="container-fluid">
        <h1>Create Challenge</h1>
        <form action="{{ route('challenges.store') }}" method="POST">
            @csrf
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" name="title" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea name="description" class="form-control" required></textarea>
            </div>
            <div class="form-group">
                <label for="starting_date">Starting Date</label>
                <input type="date" name="starting_date" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="closing_date">Closing Date</label>
                <input type="date" name="closing_date" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="duration_minutes">Duration (minutes)</label>
                <input type="number" name="duration_minutes" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">Create</button>
        </form>
    </div>
</div>
@endsection
