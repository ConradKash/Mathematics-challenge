@extends('layouts.app', ['activePage' => 'add school', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Add school', 'activeButton' => 'laravel'])


@section('content')
    <h1>Upload School</h1>

    <form action="{{ route('schools.store') }}" method="POST">
        @csrf

        <div class="form-group">
            <label for="name">School Name</label>
            <input type="text" id="name" name="name" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="district">District</label>
            <input type="text" id="district" name="district" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="registration_number">Registration Number</label>
            <input type="text" id="registration_number" name="registration_number" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="representative_email" name="representative_email" class="form-control">
        </div>

        <div class="form-group">
            <label for="representative_name">Representative Name</label>
            <input type="text" id="representative_name" name="representative_name" class="form-control">
        </div>

        <div class="form-group">
            <label for="validated">Validated:</label>
            <input type="checkbox" id="validated" name="validated" value="1" {{ old('validated') ? 'checked' : '' }}>
        </div>

        <button type="submit" class="btn btn-primary">Upload</button>
    </form>
@endsection

