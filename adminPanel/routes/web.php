<?php

use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Auth;
/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Auth::routes();

Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');

Route::get('/home', 'App\Http\Controllers\HomeController@index')->name('dashboard');
Route::get('/schools', 'App\Http\Controllers\SchoolsController@index')->name('list_schools');

Route::get('/schools/create', 'App\Http\Controllers\SchoolsController@add')->name('schools.create');
Route::post('/schools/save', 'App\Http\Controllers\SchoolsController@save')->name('schools.save');
Route::get('/schools/edit/{id}', 'App\Http\Controllers\SchoolsController@edit')->name('schools.edit');
Route::put('/schools/update/{id}', 'App\Http\Controllers\SchoolsController@update')->name('schools.update');
Route::get('/schools/schoolRepresentative', 'App\Http\Controllers\SchoolRespresentativesController@index')->name('list_schoolRepresentatives');
Route::delete('/schools/{id}', 'App\Http\Controllers\SchoolsController@delete')->name('schools.delete');


Route::group(['middleware' => 'auth'], function () {
	Route::resource('user', 'App\Http\Controllers\UserController', ['except' => ['show']]);
	Route::get('profile', ['as' => 'profile.edit', 'uses' => 'App\Http\Controllers\ProfileController@edit']);
	Route::patch('profile', ['as' => 'profile.update',  'uses' => 'App\Http\Controllers\ProfileController@update']);
	Route::patch('profile/password', ['as' => 'profile.password', 'uses' => 'App\Http\Controllers\ProfileController@password']);
});

Route::group(['middleware' => 'auth'], function () {
	Route::get('{page}', ['as' => 'page.index', 'uses' => 'App\Http\Controllers\PageController@index']);
});

