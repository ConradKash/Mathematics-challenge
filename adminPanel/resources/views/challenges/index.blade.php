@extends('layouts.app', ['activePage' => 'dashboard', 'title' => 'Mathematics Challenge', 'navName' => 'Dashboard', 'activeButton' => 'laravel'])

@section('content')
<div class="content">
    <div class="row">
        <div class="col-md-12">
            <h1> Challenges </h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="card strpied-tabled-with-hover">
                <div class="card-header ">
                    <h4 class="card-title">Challenges </h4>
                    <p class="card-category">This table displays a list of challenges, including their start date,end date,duration and number of questions . It also provides options to edit or delete each challenge entry.</p>
                </div>
                <div class="card-body table-full-width table-responsive">
                    <table class="table table-hover table-striped">
                        <thead>
                            <tr>
                                <th> Start Date </th>
                                <th> End Date </th>
                                <th> Duration </th>
                                <th> Number Of Questions </th>
                            </tr>
                        </thead>
                        <tbody>
                            @foreach($challenges as $challenge)
                            <tr>
                                <td> {{ $challenge->startDate }} </td>
                                <td> {{ $challenge->endDate }} </td>
                                <td> {{ $challenge->duration }} </td>
                                <td> {{ $challenge->questionCount }} </td>
                                <td>
                                    <a href="{{ route('challenges.edit', $challenge->id) }}" class="btn btn-success">Edit</a>
                                </td>
                                <td>
                                    <form action="{{ route('challenges.delete', $challenge->id) }}" method="POST">
                                        @csrf
                                        @method('DELETE')
                                        <button type="submit" class="btn btn-danger">Delete</button>
                                    </form>
                                </td>
                            </tr>
                            @endforeach
                        </tbody>
                    </table>
                    <div class="col">
                        <a href="{{ route('challenges.create') }}" class="btn btn-primary">Add Challenge</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    @endsection