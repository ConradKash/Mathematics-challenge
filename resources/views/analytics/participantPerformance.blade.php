@extends('layouts.app', ['activePage' => 'analytics', 'title' => 'Participant Performance', 'navName' => 'Analytics', 'activeButton' => 'laravel'])

@section('content')
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <h4 class="card-title">Participant Performance by Quarter</h4>
                </div>
                <div class="card-body">
                    <canvas id="participantPerformanceChart" style="height: 400px; width: 100%;"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

@push('scripts')
<script src="{{ mix('js/chart.js') }}"></script>
@endpush
@endsection
