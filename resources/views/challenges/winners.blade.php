@extends('layouts.app', ['activePage' => 'edit', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'edit', 'activeButton' => 'laravel'])

@section('content')
    <h1>Top Winners</h1>
    @foreach($winners as $winner)
        <p>{{ $winner->participant->firstname }} {{ $winner->participant->lastname }}: {{ $winner->score }}</p>
    @endforeach
@endsection
