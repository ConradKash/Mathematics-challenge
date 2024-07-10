@extends('layouts.app', ['activePage' => 'school', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'School', 'activeButton' => 'laravel'])

@section('content')
    <h1>Schools</h1>
    <a href="{{ route('schools.createSchool') }}" class="btn btn-success mb-3">Add School</a>
    <table class="table">
        <thead>
            <tr>
                <th>Name</th>
                <th>District</th>
                <th>Registration Number</th>
                <th>Email</th>
                <th>Representative Name</th>
                <th>Validated</th>
            </tr>
        </thead>
        <tbody>
            @foreach($schools as $school)
                <tr>
                    <td>{{ $school->name }}</td>
                    <td>{{ $school->district }}</td>
                    <td>{{ $school->registration_number }}</td>
                    <td>{{ $school->email }}</td>
                    <td>{{ $school->representative_name }}</td>
                    <td>{{ $school->validated ? 'Yes' : 'No' }}</td>
                    <td>
                        <a href="{{ route('schools.edit', $school->id) }}" class="btn btn-primary btn-sm">Edit</a>
                        <!-- Implement delete functionality using form submission -->
                        <form action="{{ route('schools.destroy', $school->id) }}" method="POST" style="display: inline-block;">
                            @csrf
                            @method('DELETE')
                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</button>
                        </form>
                    </td>
                </tr>
            @endforeach
        </tbody>
    </table>
@endsection
