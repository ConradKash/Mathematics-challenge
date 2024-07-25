@component('mail::message')
# Your Challenge Report

Hello {{ $data['participant']->name }},

Attached is your challenge report with the details of your attempts and scores.

@component('mail::button', ['url' => url('/')])
Visit Our Site
@endcomponent

Thanks,<br>
{{ config('app.name') }}
@endcomponent
