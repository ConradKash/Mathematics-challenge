import Chart from 'chart.js/auto'; // Import Chart.js

// Sample data for school performance over time
const schoolPerformanceData = {
    labels: ['2021', '2022', '2023'], // Example years
    datasets: [
        {
            label: 'School A',
            data: [75, 80, 85], // Example scores
            borderColor: 'rgba(255, 99, 132, 1)',
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            fill: false,
            tension: 0.1
        },
        {
            label: 'School B',
            data: [65, 70, 75], // Example scores
            borderColor: 'rgba(54, 162, 235, 1)',
            backgroundColor: 'rgba(54, 162, 235, 0.2)',
            fill: false,
            tension: 0.1
        }
    ]
};

// Sample data for participant performance
const participantPerformanceData = {
    labels: ['Q1', 'Q2', 'Q3', 'Q4'], // Example quarters
    datasets: [
        {
            label: 'Participant 1',
            data: [80, 85, 90, 95], // Example scores
            borderColor: 'rgba(255, 159, 64, 1)',
            backgroundColor: 'rgba(255, 159, 64, 0.2)',
            fill: false,
            tension: 0.1
        },
        {
            label: 'Participant 2',
            data: [70, 75, 80, 85], // Example scores
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            fill: false,
            tension: 0.1
        }
    ]
};

// Initialize School Performance Chart
const schoolChartCtx = document.getElementById('schoolPerformanceChart').getContext('2d');
new Chart(schoolChartCtx, {
    type: 'line',
    data: schoolPerformanceData,
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
                    text: 'Year'
                }
            }
        }
    }
});

// Initialize Participant Performance Chart
const participantChartCtx = document.getElementById('participantPerformanceChart').getContext('2d');
new Chart(participantChartCtx, {
    type: 'bar',
    data: participantPerformanceData,
    options: {
        scales: {
            y: {
                beginAtZero: true,
                title: {
                    display: true,
                    text: 'Score'
                }
            },
            x: {
                title: {
                    display: true,
                    text: 'Quarter'
                }
            }
        }
    }
});
