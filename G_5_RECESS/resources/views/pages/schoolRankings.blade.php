@extends('layouts.app', ['activePage' => 'schoolRankings', 'title' => 'School Rankings - Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'School Rankings', 'activeButton' => 'laravel'])

@section('content')
<div class="container">
    <h2>School Rankings by Total Score</h2>

    <div class="card">
        <div class="card-body">
            <canvas id="schoolRankingsChart" width="800" height="400"></canvas>
        </div>
    </div>

    <div class="card mt-4">
        <div class="card-body">
            <h3>Top Performing Schools with Challenges</h3>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>School Name</th>
                        <th>Challenge Title</th>
                        <th>Challenge ID</th>
                        <th>Average Score</th>
                    </tr>
                </thead>
                <tbody>
                    @foreach ($topPerformingSchools as $school)
                        @foreach ($school['attempts'] as $attempt)
                            <tr>
                                <td>{{ $school['name'] }}</td>
                                <td>{{ $attempt->challenge->title }}</td>
                                <td>{{ $attempt->challenge->challenge_id }}</td>
                                <td>{{ number_format($attempt->score, 2) }}</td>
                            </tr>
                        @endforeach
                    @endforeach
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Extract the topPerformingSchools data from PHP and convert it into JavaScript array
        const topPerformingSchools = @json($topPerformingSchools);

        // Debugging
        console.log(topPerformingSchools);

        // Extract labels and data for the chart
        const chartLabels = topPerformingSchools.map(school => school.name);
        const chartData = topPerformingSchools.map(school => school.average_score);

        // Creating Chart.js instance
        const ctx = document.getElementById('schoolRankingsChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: chartLabels,
                datasets: [{
                    label: 'Average Score',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    data: chartData
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Average Score'
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'School Names'
                        }
                    }
                }
            }
        });
    });
</script>



@endsection