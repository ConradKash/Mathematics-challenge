@extends('layouts.app', ['activePage' => 'dashboard', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Dashboard', 'activeButton' => 'laravel'])

@section('content')
    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="card ">
                        <div class="card-header ">
                            <h4 class="card-title">{{ __('ADMIN ') }}</h4>
                        <div class="card-body ">
                      click  <a href="{{ route('schools.createSchool') }}" class="btn btn-success mb-3">Upload Schools</a>
                        </div>
                        </div>
                        <div class="card ">
                        <div class="card-header ">
                            <h4 class="card-title">{{ __('Chakkenges ') }}</h4>
                       
                        <div class="card-body ">
                        <a href="{{ route('challenges.create') }}" class="btn btn-success mb-3">Create Challenge</a>
                        </div>
</div>
@endsection