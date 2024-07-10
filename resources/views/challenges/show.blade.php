@extends('layouts.app', ['activePage' => 'show', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'show', 'activeButton' => 'laravel'])



@section('content')
    <h1>{{ $challenge->name }}</h1>
    <p>Start Date: {{ $challenge->start_date }}</p>
    <p>End Date: {{ $challenge->end_date }}</p>
    <p>Duration: {{ $challenge->duration }} minutes</p>
    <p>Number of Questions: {{ $challenge->num_questions }}</p>

    <a href="{{ route('challenges.edit', $challenge->id) }}" class="btn btn-primary">Edit</a>

    <form action="{{ route('challenges.destroy', $challenge->id) }}" method="POST" style="display:inline;">
        @csrf
        @method('DELETE')
        <button type="submit" class="btn btn-danger">Delete</button>
    </form>
@endsection
