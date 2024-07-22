@extends('layouts.app', ['activePage' => 'dashboard', 'title' => 'Mathematics Challenge', 'navName' => 'Dashboard', 'activeButton' => 'laravel'])

@section('content')

<div class="formbold-main-wrapper">
  <div class="formbold-form-wrapper">
    <form action="{{ route('challenges.update', $challenges->id) }}" method="POST" enctype="multipart/form-data">
    @method('PUT')
    @csrf
        <div class="formbold-input-flex">
          <p>Set challenge parameters</P>
          <div>
              <input
              type="date"
              name="startDate"
              id="startDate"
              placeholder="Enter the start date"
              class="formbold-form-input"
              value="{{$challenges->startDate}}"
              />
              <label for="startDate" class="formbold-form-label"> Start Date </label>
          </div>
          <div>
              <input
              type="date"
              name="endDate"
              id="endDate"
              placeholder="Enter the end date"
              class="formbold-form-input"
              value="{{$challenges->endDate}}"
              />
              <label for="endDate" class="formbold-form-label"> End Date </label>
          </div>
        </div>

        <div class="formbold-input-flex">
          <div>
              <input
              type="number"
              name="duration"
              id="duration"
              placeholder="Enter the challenge duration"
              class="formbold-form-input"
              value="{{$challenges->duration}}"
              />
              <label for="duration" class="formbold-form-label"> Challenge Duration </label>
          </div>
          <div>
              <input
              type="number"
              name="questionCount"
              id="questionCount"
              placeholder="Enter the number Of Questions"
              class="formbold-form-input"
              value="{{$challenges->questionCount}}"
              />
              <label for="number" class="formbold-form-label"> Number Of Questions </label>
          </div>
        </div>
        <div class="formbold-input-file">
        @csrf
            <label for="file">Import questions and answers</label>
            <input type="file" name="file" id="file" class="form-control">
        </div>
        <button type="submit" class="btn btn-primary">Import</button>
    </form>
  </div>
</div>
<style>
  @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');

  body {
    font-family: "Inter", sans-serif;
  }

  .formbold-form-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 48px;
    margin: 0 auto;
    max-width: 550px;
    width: 100%;
    background: white;
  }

  .formbold-input-flex {
    display: flex;
    gap: 20px;
    margin-bottom: 22px;
  }
  .formbold-input-flex > div {
    width: 50%;
    display: flex;
    flex-direction: column-reverse;
  }
  .formbold-form-input {
    width: 100%;
    padding-bottom: 10px;
    border: none;
    border-bottom: 1px solid #DDE3EC;
    background: #FFFFFF;
    font-weight: 500;
    font-size: 16px;
    color: #07074D;
    outline: none;
    resize: none;
  }
  .formbold-form-input::placeholder {
    color: #536387;
  }
  .formbold-form-input:focus {
    border-color: #6A64F1;
  }
  .formbold-form-label {
    color: #07074D;
    font-weight: 500;
    font-size: 14px;
    line-height: 24px;
    display: block;
    margin-bottom: 18px;
  }
  .formbold-form-input:focus + .formbold-form-label {
    color: #6A64F1;
  }

  .formbold-input-file input[type="file"] {
    position: absolute;
    top: 6px;
    left: 0;
    z-index: -1;
  }
  .formbold-input-file .formbold-input-label {
    display: flex;
    align-items: center;
    gap: 10px;
    position: relative;
  }
  
</style>

@endsection