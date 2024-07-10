<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\SchoolController;
use App\Http\Controllers\CompetitionController;
use App\Http\Controllers\ChallengeController;
use App\Http\Controllers\PageController;


/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});



Auth::routes();

Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');
Auth::routes();

Route::get('/home', 'App\Http\Controllers\HomeController@index')->name('dashboard');

Route::group(['middleware' => 'auth'], function () {
	Route::resource('user', 'App\Http\Controllers\UserController', ['except' => ['show']]);
	Route::get('profile', ['as' => 'profile.edit', 'uses' => 'App\Http\Controllers\ProfileController@edit']);
	Route::patch('profile', ['as' => 'profile.update', 'uses' => 'App\Http\Controllers\ProfileController@update']);
	Route::patch('profile/password', ['as' => 'profile.password', 'uses' => 'App\Http\Controllers\ProfileController@password']);
});

Route::group(['middleware' => 'auth'], function () {
	Route::get('{page}', ['as' => 'page.index', 'uses' => 'App\Http\Controllers\PageController@index']);
	
	Route::get('/school', [PageController::class, 'school'])->name('page.school');
    Route::get('/school/create', [SchoolController::class, 'createSchool'])->name('schools.createSchool');
    Route::post('/school', [SchoolController::class, 'store'])->name('schools.store');
    Route::get('/school/{school}/edit', [SchoolController::class, 'edit'])->name('schools.edit');
    Route::put('/school/{school}', [SchoolController::class, 'update'])->name('schools.update');
    Route::delete('/school/{school}', [SchoolController::class, 'destroy'])->name('schools.destroy');
});



use App\Http\Controllers\QuestionController;

Route::resource('questions', QuestionController::class)->except(['show']);
Route::get('/questAnswer', [PageController::class, 'index'])->name('questAnswer');
Route::get('/questions/index', [QuestionController::class, 'index'])->name('questions.index');



//Competition controller implement
Route::get('/comp', [PageController::class, 'index'])->name('pages.comp');
Route::post('/competition', [CompetitionController::class, 'store'])->name('competition.store');

//Challenge controller implement

Route::get('/challenges/{competition}/edit', [ChallengeController::class, 'edit'])->name('challenges.edit');
Route::put('/challenges/{competition}', [ChallengeController::class, 'update'])->name('challenges.update');
Route::get('/challenges/create', [ChallengeController::class, 'create'])->name('challenges.create');
Route::post('/challenges', [ChallengeController::class, 'store'])->name('challenges.store');



Route::resource('challenges', ChallengeController::class);

//Route::get('/index', [PageController::class, 'index'])->name('page.index');
Route::put('challenges/{challenge}', [ChallengeController::class, 'update'])->name('challenges.update');
// In your web.php (routes file)
Route::get('/challenges', [PageController::class, 'challenges'])->name('page.challenges');



