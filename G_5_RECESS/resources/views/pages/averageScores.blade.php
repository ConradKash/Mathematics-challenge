@extends('layouts.app', ['activePage' => 'Analytics', 'title' => 'Average Scores Across Challenges', 'navName' => 'Analytics', 'activeButton' => 'laravel'])

@section('content')
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h4 class="card-title">Average Scores Across Challenges</h4>
                    </div>
                    <div class="card-body">
                        <div class="chart">
                            <canvas id="averageScoresChart" style="height: 400px; width: 100%;"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Include Chart.js library -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Extract the averageScores data from PHP and convert it into JavaScript array
            var averageScoresData = {!! json_encode($averageScores) !!};

            // Prepare arrays for labels (challenge IDs) and data (average scores)
            var challengeIds = averageScoresData.map(data => data.challenge_id);
            var averageScores = averageScoresData.map(data => data.average_score);

            // Use Chart.js to create a bar chart
            var ctx = document.getElementById('averageScoresChart').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: challengeIds,
                    datasets: [{
                        label: 'Average Score',
                        data: averageScores,
                        backgroundColor: 'rgba(54, 162, 235, 0.5)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true, // Start y-axis from zero
                            title: {
                                display: true,
                                text: 'Average Score'
                            }
                        },
                        x: {
                            title: {
                                display: true,
                                text: 'Challenge IDs'
                            }
                        }
                    }
                }
            });
        });
    </script>
@endsection
