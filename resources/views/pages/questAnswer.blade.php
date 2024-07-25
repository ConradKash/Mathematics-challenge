@extends('layouts.app', ['activePage' => 'questAnswer', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'questAnswer', 'activeButton' => 'laravel'])

@section('content')
<div class="container">
    <h1>Upload Questions</h1>
    <form action="{{ route('questions.store') }}" method="POST" enctype="multipart/form-data">
        @csrf
        <div class="form-group">
            <label for="questions">Questions File</label>
            <input type="file" name="questions" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="answers">Answers File</label>
            <input type="file" name="answers" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Upload</button>
    </form>
</div>
@endsection
