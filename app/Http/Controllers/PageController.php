<?php
// App\Http\Controllers\PageController.php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\School;
use App\Models\Challenge; // Import the Challenge model

class PageController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index($page)
    {
        if (view()->exists("pages.{$page}")) {
            switch ($page) {
                case 'school':
                    // Fetch the schools data from the database
                    $schools = School::all();
                    // Pass the data to the view
                    return view("pages.{$page}", compact('schools'));
                
                case 'questAnswer':
                    $data['title'] = 'Your Quest Answer Page Title';
                    return view("pages.{$page}", $data);

                case 'setup':
              
                    return view("pages.{$page}");
                case 'index':
                    $challenges = Challenge::all();

                 // Pass the challenges to the view
                 return view("pages.{$page}", compact('challenges'));
                
                
                // Add more cases for other pages if needed
            }

            return view("pages.{$page}");
        }
        return abort(404);
    }
}
