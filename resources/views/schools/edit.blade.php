@extends('layouts.app', ['activePage' => 'edit', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Edit', 'activeButton' => 'laravel'])

@section('content')
    <h1>Edit School</h1>

    <form action="{{ route('schools.update', $school->id) }}" method="POST">
        @csrf
        @method('PUT')

        <div class="form-group">
            <label for="name">School Name</label>
            <input type="text" id="name" name="name" class="form-control" value="{{ $school->name }}" required>
        </div>

        <div class="form-group">
            <label for="district">District</label>
            <input type="text" id="district" name="district" class="form-control" value="{{ $school->district }}" required>
        </div>

        <div class="form-group">
            <label for="registration_number">Registration Number</label>
            <input type="text" id="registration_number" name="registration_number" class="form-control" value="{{ $school->registration_number }}" required>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" class="form-control" value="{{ $school->email }}">
        </div>

        <div class="form-group">
            <label for="representative_name">Representative Name</label>
            <input type="text" id="representative_name" name="representative_name" class="form-control" value="{{ $school->representative_name }}">
        </div>

        <div class="form-group">
            <label for="validated">Validated:</label>
            <input type="checkbox" id="validated" name="validated" value="1" {{ $school->validated ? 'checked' : '' }}>
        </div>

        <button type="submit" class="btn btn-primary">Update School</button>
    </form>
@endsection
