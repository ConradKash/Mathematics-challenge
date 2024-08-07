<div class="sidebar" data-image="{{ asset('light-bootstrap/img/sidebar-5.jpg') }}">
    <!--
Tip 1: You can change the color of the sidebar using: data-color="purple | blue | green | orange | red"

Tip 2: you can also add an image using data-image tag
-->
    <div class="sidebar-wrapper">
       <!-- <div class="logo">
            <a href="http://www.creative-tim.com" class="simple-text">
                {{ __("Creative Tim") }}
            </a>
        </div> -->
        <ul class="nav">
            <li class="nav-item @if($activePage == 'dashboard') active @endif">
                <a class="nav-link" href="{{route('dashboard')}}">
                    <i class="nc-icon nc-chart-pie-35"></i>
                    <p>{{ __("Dashboard") }}</p>
                </a>
            </li>
           
            <li class="nav-item">
                <a class="nav-link" data-toggle="collapse" href="#laravelExamples" @if($activeButton =='laravel') aria-expanded="true" @endif>
                    <i>
                        <img src="{{ asset('light-bootstrap/img/laravel.svg') }}" style="width:25px">
                    </i>
                    <p>
                        {{ __('Laravel example') }}
                        <b class="caret"></b>
                    </p>
                </a>
                <div class="collapse @if($activeButton =='laravel') show @endif" id="laravelExamples">
                    <ul class="nav">
                        <li class="nav-item @if($activePage == 'user') active @endif">
                            <a class="nav-link" href="{{route('profile.edit')}}">
                                <i class="nc-icon nc-single-02"></i>
                                <p>{{ __("User Profile") }}</p>
                            </a>
                        </li>
                        <li class="nav-item @if($activePage == 'user-management') active @endif">
                            <a class="nav-link" href="{{route('user.index')}}">
                                <i class="nc-icon nc-circle-09"></i>
                                <p>{{ __("User Management") }}</p>
                            </a>
                        </li>
                    </ul>
                </div>
            </li>
            <li class="nav-item @if($activePage == 'school') active @endif">
                <a class="nav-link" href="{{route('page.index', 'school')}}">
                    <i class="nc-icon nc-notes"></i>
                    <p>{{ __("School") }}</p>
                </a>
            </li>
            <li class="nav-item @if($activePage == 'questAnswer') active @endif">
                <a class="nav-link" href="{{route('page.index', 'questAnswer')}}">
                    <i class="nc-icon nc-atom"></i>
                    <p>{{ __("questAnswer") }}</p>
                </a>
            </li>
            <li class="nav-item @if($activePage == 'challenges') active @endif">
                <a class="nav-link" href="{{route('page.index', 'challenges')}}">
                    <i class="nc-icon nc-atom"></i>
                    <p>{{ __("challenges") }}</p>
                </a>
            </li>
            
           <li  class="nav-item @if($activePage == 'analyticsAll') active @endif">
                <a class="nav-link" href="{{route('page.index', 'analyticsAll')}}">
                 <i class="nc-icon nc-chart-bar-32"></i>
                <p>All Analytics</p>
            </a>
            </li>
            <li class="nav-item @if($activePage == 'averageScores') active @endif">
                <a class="nav-link" href="{{route('page.index', 'averageScores')}}">
                    <i class="nc-icon nc-bell-55"></i>
                    <p>{{ __("Analytics") }}</p>
                </a>
            </li>
            <li class="nav-item @if($activePage == 'SchoolRankings') active @endif">
                <a class="nav-link" href="{{route('page.index', 'schoolRankings')}}">
                    <i class="nc-icon nc-bell-55"></i>
                    <p>{{ __("Analytics") }}</p>
                </a>
            </li>
        </ul>
    </div>
</div>
