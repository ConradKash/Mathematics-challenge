@extends('layouts.app', ['activePage' => 'comp', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Comp', 'activeButton' => 'laravel'])

@section('content')
<div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="card strpied-tabled-with-hover">
                        <div class="card-header ">
    <div class="container">
        <h1>Set Competition Parameters</h1>
        <form action="{{ route('competition.store') }}" method="POST">
            @csrf

            <div class="form-group">
                <label for="start_date">Start Date</label>
                <input type="datetime-local" id="start_date" name="start_date" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="end_date">End Date</label>
                <input type="datetime-local" id="end_date" name="end_date" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="duration_minutes">Duration (minutes)</label>
                <input type="number" id="duration_minutes" name="duration_minutes" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="questions_per_attempt">Questions per Attempt</label>
                <input type="number" id="questions_per_attempt" name="questions_per_attempt" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary">Save Parameters</button>
        </form>
    </div>
    </div>
    </div>
    </div>
    </div>
    </div>
    </div>
@endsection
