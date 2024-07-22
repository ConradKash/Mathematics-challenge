@extends('layouts.app', ['activePage' => 'analytics', 'title' => 'School Performance', 'navName' => 'Analytics', 'activeButton' => 'laravel'])

@section('content')
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <h4 class="card-title">School Performance Over Time</h4>
                </div>
                <div class="card-body">
                    <canvas id="schoolPerformanceChart" style="height: 400px; width: 100%;"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

@push('scripts')
<script src="{{ mix('js/chart.js') }}"></script>
@endpush
@endsection
