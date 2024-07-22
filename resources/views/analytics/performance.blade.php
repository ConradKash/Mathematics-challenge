@extends('layouts.app', ['activePage' => 'Analytics', 'title' => 'Performance Over Time', 'navName' => 'Analytics', 'activeButton' => 'laravel'])

@section('content')
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h4 class="card-title">Performance of Schools Over Time</h4>
                    </div>
                    <div class="card-body">
                        

                        <div class="chart mt-4">
                            <canvas id="performanceChart" style="height: 400px; width: 100%;"></canvas>
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
            // Extract the chartData from PHP and convert it into JavaScript array
            var chartData = @json($chartData);

            // Use Chart.js to create a bar chart
            var ctx = document.getElementById('performanceChart').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar', // Change chart type to 'bar'
                data: {
                    labels: chartData.labels,
                    datasets: chartData.datasets
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        x: {
                            stacked: true,
                            title: {
                                display: true,
                                text: 'Year'
                            }
                        },
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'Average Score'
                            }
                        }
                    }
                }
            });
        });
    </script>
@endsection
