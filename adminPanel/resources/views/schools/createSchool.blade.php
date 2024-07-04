@extends('layouts.app', ['activePage' => 'dashboard', 'title' => 'Mathematics Challenge', 'navName' => 'Dashboard', 'activeButton' => 'laravel'])

@section('content')

<div class="content">
        <div class="container-fluid">
        <div class="formbold-main-wrapper">
        <div class="formbold-form-wrapper">
            <form action="{{ route('schools.save') }}" method="post" class = 'submitForm'>
                @csrf
                <div class="formbold-steps">
                    <ul>
                        <li class="formbold-step-menu1 active">
                            <span>1</span>
                            School Details
                        </li>
                        <li class="formbold-step-menu2">
                            <span>2</span>
                            School Representative
                        </li>
                    </ul>
                </div>

                <div class="formbold-form-step-1 active">
                <div>
                    <label for="schoolName" class="formbold-form-label"> School Name </label>
                    <input
                    type="text"
                    name="schoolName"
                    id="schoolName"
                    placeholder="Enter the school name"
                    class="formbold-form-input"
                    />
                </div>
                        
                <div>
                    <label for="address" class="formbold-form-label"> District </label>
                    <input
                    type="text"
                    name="district"
                    id="district"
                    placeholder="Enter the district"
                    class="formbold-form-input"
                    />
                </div>
                </div>

                <div class="formbold-form-step-2">
                  <div class="formbold-input-flex">
                    <div>
                        <label for="firstname" class="formbold-form-label"> Name </label>
                        <input
                        type="text"
                        name="name"
                        placeholder="Name"
                        id="name"
                        class="formbold-form-input"
                        />
                    </div>
                    <div>
                        <label for="lastname" class="formbold-form-label"> Password </label>
                        <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        id="password"
                        class="formbold-form-input"
                        />
                    </div>
                  </div>
          
                  <div class="formbold-input-flex">
                      <div>
                          <label for="dob" class="formbold-form-label"> Contact </label>
                          <input 
                          type="text" 
                          name="phone" 
                          id="phone" 
                          placeholder="Phone Number"
                          class="formbold-form-input"
                          />
                      </div>
                      <div>
                          <label for="email" class="formbold-form-label"> Email Address </label>
                          <input
                          type="email"
                          name="email"
                          placeholder="example@mail.com"
                          id="email"
                          class="formbold-form-input"
                          />
                      </div>
                  </div>
                </div>

                <div class="formbold-form-btn-wrapper">
                <button type="submit" class="formbold-back-btn">
                    Back
                </button>

                <button  class="formbold-btn">
                    Next Step
                </button>
                </div>
            </form>
        </div>
        </div>
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

  .formbold-steps {
    padding-bottom: 18px;
    margin-bottom: 35px;
    border-bottom: 1px solid #DDE3EC;
  }
  .formbold-steps ul {
    padding: 0;
    margin: 0;
    list-style: none;
    display: flex;
    gap: 40px;
  }
  .formbold-steps li {
    display: flex;
    align-items: center;
    gap: 14px;
    font-weight: 500;
    font-size: 16px;
    line-height: 24px;
    color: #536387;
  }
  .formbold-steps li span {
    display: flex;
    align-items: center;
    justify-content: center;
    background: #DDE3EC;
    border-radius: 50%;
    width: 36px;
    height: 36px;
    font-weight: 500;
    font-size: 16px;
    line-height: 24px;
    color: #536387;
  }
  .formbold-steps li.active {
    color: #07074D;;
  }
  .formbold-steps li.active span {
    background: #6A64F1;
    color: #FFFFFF;
  }

  .formbold-input-flex {
    display: flex;
    gap: 20px;
    margin-bottom: 22px;
  }
  .formbold-input-flex > div {
    width: 50%;
  }
  .formbold-form-input {
    width: 100%;
    padding: 13px 22px;
    border-radius: 5px;
    border: 1px solid #DDE3EC;
    background: #FFFFFF;
    font-weight: 500;
    font-size: 16px;
    color: #536387;
    outline: none;
    resize: none;
  }
  .formbold-form-input:focus {
    border-color: #6a64f1;
    box-shadow: 0px 3px 8px rgba(0, 0, 0, 0.05);
  }
  .formbold-form-label {
    color: #07074D;
    font-weight: 500;
    font-size: 14px;
    line-height: 24px;
    display: block;
    margin-bottom: 10px;
  }

  .formbold-form-confirm {
    border-bottom: 1px solid #DDE3EC;
    padding-bottom: 35px;
  }
  .formbold-form-confirm p {
    font-size: 16px;
    line-height: 24px;
    color: #536387;
    margin-bottom: 22px;
    width: 75%;
  }
  .formbold-form-confirm > div {
    display: flex;
    gap: 15px;
  }

  .formbold-confirm-btn {
    display: flex;
    align-items: center;
    gap: 10px;
    background: #FFFFFF;
    border: 0.5px solid #DDE3EC;
    border-radius: 5px;
    font-size: 16px;
    line-height: 24px;
    color: #536387;
    cursor: pointer;
    padding: 10px 20px;
    transition: all .3s ease-in-out;
  }
  .formbold-confirm-btn {
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.12);
  }
  .formbold-confirm-btn.active {
    background: #6A64F1;
    color: #FFFFFF;
  }

  .formbold-form-step-1,
  .formbold-form-step-2,
  .formbold-form-step-3 {
    display: none;
  }
  .formbold-form-step-1.active,
  .formbold-form-step-2.active,
  .formbold-form-step-3.active {
    display: block;
  }

  .formbold-form-btn-wrapper {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    gap: 25px;
    margin-top: 25px;
  }
  .formbold-back-btn {
    cursor: pointer;
    background: #FFFFFF;
    border: none;
    color: #07074D;
    font-weight: 500;
    font-size: 16px;
    line-height: 24px;
    display: none;
  }
  .formbold-back-btn.active {
    display: block;
  }
  .formbold-btn {
    display: flex;
    align-items: center;
    gap: 5px;
    font-size: 16px;
    border-radius: 5px;
    padding: 10px 25px;
    border: none;
    font-weight: 500;
    background-color: #6A64F1;
    color: white;
    cursor: pointer;
  }
  .formbold-btn:hover {
    box-shadow: 0px 3px 8px rgba(0, 0, 0, 0.05);
  }

</style>
<script>
  const stepMenuOne = document.querySelector('.formbold-step-menu1')
  const stepMenuTwo = document.querySelector('.formbold-step-menu2')

  const stepOne = document.querySelector('.formbold-form-step-1')
  const stepTwo = document.querySelector('.formbold-form-step-2')

  const formSubmitBtn = document.querySelector('.formbold-btn')
  const formBackBtn = document.querySelector('.formbold-back-btn')  

  formSubmitBtn.addEventListener("click", function(event){
    event.preventDefault()
    if(stepMenuOne.className == 'formbold-step-menu1 active') {
        event.preventDefault()

        stepMenuOne.classList.remove('active')
        stepMenuTwo.classList.add('active')

        stepOne.classList.remove('active')
        stepTwo.classList.add('active')

        formBackBtn.classList.add('active')
        formBackBtn.addEventListener("click", function (event) {
          event.preventDefault()

          stepMenuOne.classList.add('active')
          stepMenuTwo.classList.remove('active')

          stepOne.classList.add('active')
          stepTwo.classList.remove('active')

          formBackBtn.classList.remove('active')
          formSubmitBtn.textContent = 'Next Step'
        })
        
        formSubmitBtn.textContent = 'Submit'
        formSubmitBtn.type = 'submit'

      } else if(stepMenuTwo.className == 'formbold-step-menu2 active') {
        document.querySelector('.submitForm').submit()
      }
  })
    

  
</script>


@endsection