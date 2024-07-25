@extends('layouts.app', [
    'activePage' => 'Analytics',
    'title' => 'Best Performing Schools Across Challenges',
    'navName' => 'Analytics',
    'activeButton' => 'laravel'
])

@section('content')
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h4 class="card-title">Best Performing Schools Across All Challenges</h4>
                    </div>
                    <div class="card-body">
                        @if ($bestPerformingSchools->isEmpty())
                            <p class="text-center">No schools data available.</p>
                        @else
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th>Rank</th>
                                            <th>School Name</th>
                                            <th>Number of Participants</th>
                                            <th>Total Score</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        @foreach ($bestPerformingSchools as $rank => $school)
                                            <tr>
                                                <td>{{ $rank + 1 }}</td>
                                                <td>{{ $school->name }}</td>
                                                <td>{{ $school->participant_count }}</td>
                                                <td>{{ number_format($school->total_score, 0) }}</td>
                                            </tr>
                                        @endforeach
                                    </tbody>
                                </table>
                            </div>
                        @endif
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
