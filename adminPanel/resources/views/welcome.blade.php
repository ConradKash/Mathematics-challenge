@extends('layouts/app', ['activePage' => 'welcome', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION'])

@section('content')
<div class="full-page section-image" data-color="black" data-image="{{asset('light-bootstrap/img/full-screen-image-2.jpg')}}">
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-7 col-md-8">
                    <h1 class="text-white text-center">{{ __('Welcome to Light Bootstrap Dashboard FREE Laravel Live Preview.') }}</h1>
                </div>


                <body>
                    <div class="slide-container">
                        <div class="wrap">
                            <div class="box">
                                <div class="box-top">
                                    <img class="box-image" src="https://images.unsplash.com/photo-1622219809260-ce065fc5277f?crop=entropy&cs=srgb&fm=jpg&ixid=MnwxNDU4OXwwfDF8cmFuZG9tfHx8fHx8fHx8MTYyMzMwNjYxOQ&ixlib=rb-1.2.1&q=85" alt="Girl Eating Pizza">
                                    <div class="title-flex">
                                        <h3 class="box-title">Kelsie Meyer</h3>
                                        <p class="user-follow-info">17 Projects</p>
                                    </div>
                                    <p class="description">Whipped steamed roast cream beans macchiato skinny grinder café. Iced grinder go mocha steamed grounds cultivar panna aroma.</p>
                                </div>
                                <a href="#" class="button">Follow Kelsie</a>
                            </div>
                            <div class="box">
                                <div class="box-top">
                                    <img class="box-image" src="https://images.unsplash.com/photo-1488161628813-04466f872be2?crop=entropy&cs=srgb&fm=jpg&ixid=MnwxNDU4OXwwfDF8cmFuZG9tfHx8fHx8fHx8MTYyMzMxNTMwNQ&ixlib=rb-1.2.1&q=85" alt="Girl Eating Pizza">
                                    <div class="title-flex">
                                        <h3 class="box-title">Mark Carusso</h3>
                                        <p class="user-follow-info">33 Projects</p>
                                    </div>
                                    <p class="description">Whipped steamed roast cream beans macchiato skinny grinder café. Iced grinder go mocha steamed grounds cultivar panna aroma.</p>
                                </div>
                                <a href="#" class="button">Follow Mark</a>
                            </div>
                            <div class="box">
                                <div class="box-top">
                                    <img class="box-image" src="https://images.unsplash.com/photo-1456885284447-7dd4bb8720bf?crop=entropy&cs=srgb&fm=jpg&ixid=MnwxNDU4OXwwfDF8cmFuZG9tfHx8fHx8fHx8MTYyMzMxNTQzNA&ixlib=rb-1.2.1&q=85" alt="Girl Eating Pizza">
                                    <div class="title-flex">
                                        <h3 class="box-title">Taylor Green</h3>
                                        <p class="user-follow-info">26 Projects</p>
                                    </div>
                                    <p class="description">Whipped steamed roast cream beans macchiato skinny grinder café. Iced grinder go mocha steamed grounds cultivar panna aroma.</p>
                                </div>
                                <a href="#" class="button">Follow Taylor</a>
                            </div>
                            <div class="box">
                                <div class="box-top">
                                    <img class="box-image" src="https://images.unsplash.com/photo-1489980557514-251d61e3eeb6?crop=entropy&cs=srgb&fm=jpg&ixid=MnwxNDU4OXwwfDF8cmFuZG9tfHx8fHx8fHx8MTYyMzMxNjA1MA&ixlib=rb-1.2.1&q=85" alt="Girl Eating Pizza">
                                    <div class="title-flex">
                                        <h3 class="box-title">Isaiah Jian</h3>
                                        <p class="user-follow-info">12 Projects</p>
                                    </div>
                                    <p class="description">Whipped steamed roast cream beans macchiato skinny grinder café. Iced grinder go mocha steamed grounds cultivar panna aroma.</p>
                                </div>
                                <a href="#" class="button">Follow Isaiah</a>
                            </div>
                        </div>
                    </div>
                </body>

                @endsection

                @push('js')
                <script>
                    $(document).ready(function() {
                        demo.checkFullPageBackgroundImage();

                        setTimeout(function() {
                            // after 1000 ms we add the class animated to the login/register card
                            $('.card').removeClass('card-hidden');
                        }, 700)
                    });
                </script>
                @endpush