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
    Route::get('/schools/rankings', [SchoolController::class, 'rankings'])->name('schools.rankings');

});



use App\Http\Controllers\QuestionController;

Route::resource('questions', QuestionController::class)->except(['show']);
Route::get('/questAnswer', [PageController::class, 'index'])->name('questAnswer');
Route::get('/questions/index', [QuestionController::class, 'index'])->name('questions.index');




//Challenge controller implement

Route::resource('challenges', ChallengeController::class);

Route::get('/challenges', [PageController::class, 'challenges'])->name('pages.challenges');
Route::get('/challenges/{challenge}/edit', [ChallengeController::class, 'edit'])->name('challenges.edit');
Route::put('/challenges/{challenge}', [ChallengeController::class, 'update'])->name('challenges.update');


//Route::get('/index', [PageController::class, 'index'])->name('page.index');



use App\Http\Controllers\AnalyticsController;
//Route::get('/averageScores', [PageController::class, 'averageScores'])->name('pages.averageScores');
// Route to render the average scores view


// Route for average scores across challenges
//Route::get('/analytics/average-scores', [AnalyticsController::class, 'averageScores'])->name('analytics.averageScores');

// Route for most correctly answered questions
Route::get('/analytics/correctansweredquestions', [AnalyticsController::class, 'mostCorrectlyAnsweredQuestions'])->name('analytics.correctansweredquestions');

// routes/web.php
Route::get('/analyticsAll', [PageController::class, 'analyticsAll'])->name('analyticsAll');



// Route for school rankings based on total score
//Route::get('/pages/school-rankings', [AnalyticsController::class, 'schoolRankings'])->name('pages.schoolRankings');

// Route for performance over time
Route::get('/analytics/performance', [AnalyticsController::class, 'performanceOverTime'])->name('analytics.performance');

// Route for question repetition for a given participant
Route::get('/analytics/question-repetition/{participantId}', [AnalyticsController::class, 'questionRepetition'])->name('analytics.questionRepetition');

// Route for worst performing schools for a given challenge
Route::get('/analytics/worst-performing-schools/{challengeId}', [AnalyticsController::class, 'worstPerformingSchools'])
    ->name('analytics.worstPerformingSchools');

    


// Route for best performing schools across all challenges
Route::get('/analytics/best-performing-schools', [AnalyticsController::class, 'bestPerformingSchools'])->name('analytics.bestPerformingSchools');

// Route for participants with incomplete challenges
Route::get('/analytics/incompleteChallenges', [AnalyticsController::class, 'incompleteChallenges'])->name('analytics.incompleteChallenges');
Route::post('/analytics/schoolPerformance', [AnalyticsController::class, 'schoolPerformance'])->name('analytics.schoolPerformance');
Route::get('/analytics/schoolPerformance', [AnalyticsController::class, 'schoolPerformance'])->name('analytics.schoolPerformance');
Route::get('/analytics/participantPerformance', [AnalyticsController::class, 'performanceOverTime'])->name('analytics.participantPerformance');
//Route::post('/school-performance', [AnalyticsController::class, 'schoolPerformance'])->name('schoolPerformance');

use App\Http\Controllers\ReportController;


Route::get('/report/{participantId}', [ReportController::class, 'showReport'])->name('report.show');
Route::get('/report/download/{participantId}', [ReportController::class, 'downloadReport'])->name('report.download');
Route::post('/report/send/{participantId}', [ReportController::class, 'sendReport'])->name('report.send');



Route::get('/challenges/{challenge_id}/report', [ChallengeController::class, 'sendChallengeReports'])->name('challenges.sendReports');
Route::get('/challenges/winners/{challenge}', [ChallengeController::class, 'getTopWinners'])->name('challenges.winners');
Route::get('/challenges/{challenge_id}/view_report', [ChallengeController::class, 'viewChallengeReport'])->name('challenges.view_report');

