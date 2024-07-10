
<!-- resources/views/excel-index.blade.php -->
@extends('layouts.app', ['activePage' => 'dashboard', 'title' => 'Mathematics Challenge', 'navName' => 'Dashboard', 'activeButton' => 'laravel'])
@section('content')
<div class="container">
    @if(session('success'))
        <div class="alert alert-success">
            {{ session('success') }}
        </div>
    @endif
 
    <form action="{{ route('challenges') }}" method="POST" enctype="multipart/form-data">
        @csrf
        <div class="form-group">
            <label for="file">Import questions and answers</label>
            <input type="file" name="file" id="file" class="form-control">
        </div>
        <button type="submit" class="btn btn-primary">Import</button>
    </form>
</div>
@endsection