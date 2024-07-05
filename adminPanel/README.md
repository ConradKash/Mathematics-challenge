## Note

We recommend installing this preset on a project that you are starting from scratch, otherwise your project's design might break.

## Prerequisites

If you don't already have an Apache local environment with PHP and MySQL, use one of the following links:

 - Windows: https://updivision.com/blog/post/beginner-s-guide-to-setting-up-your-local-development-environment-on-windows
 - Linux: https://howtoubuntu.org/how-to-install-lamp-on-ubuntu
 - Mac: https://wpshout.com/quick-guides/how-to-install-mamp-on-your-mac/

Also, you will need to install Composer: https://getcomposer.org/doc/00-intro.md   
And Laravel: https://laravel.com/docs/9.x/installation

## Installation

After initializing a fresh instance of Laravel (and making all the necessary configurations), install the preset using one of the provided methods:

### Via composer

1. `Cd` to your Laravel app  
2. Type in your terminal: `composer require laravel/ui` and `php artisan ui vue --auth`
3. Install this preset via `composer require laravel-frontend-presets/light-bootstrap`. No need to register the service provider. Laravel 9.x can auto detect the package.
4. Run `php artisan ui light-bootstrap` command to install the Light Bootstrap preset. This will install all the necessary assets and also the custom auth views, it will also add the auth route in `routes/web.php`
(NOTE: If you run this command several times, be sure to clean up the duplicate Auth entries in routes/web.php)
5. In your terminal run `composer dump-autoload`
6. Run `php artisan migrate --seed` to create basic users table

## Usage

Register a user or login using **admin@lightbp.com** and **secret** and start testing the preset (make sure to run the migrations and seeders for these credentials to be available).

Besides the dashboard and the auth pages this preset also has an edit profile page. All the necessary files (controllers, requests, views) are installed out of the box and all the needed routes are added to `routes/web.php`. Keep in mind that all of the features can be viewed once you login using the credentials provided above or by registering your own user. 

### Dashboard

You can access the dashboard either by using the "**Dashboard**" link in the left sidebar or by adding **/home** in the url. 

### Profile edit

You have the option to edit the current logged in user's profile (change name, email and password). To access this page just click the "**User profile**" link in the left sidebar or by adding **/profile** in the url.

The `App\Http\Controllers\ProfileController` handles the update of the user information. 

```
public function update(ProfileRequest $request)
{
    auth()->user()->update($request->all());

    return back()->withStatus(__('Profile successfully updated.'));
}
```

Also you shouldn't worry about entering wrong data in the inputs when editing the profile, validation rules were added to prevent this (see `App\Http\Requests\ProfileRequest`). If you try to change the password you will see that other validation rules were added in `App\Http\Requests\PasswordRequest`. Notice that in this file you have a custom validation rule that can be found in `App\Rules\CurrentPasswordCheckRule`.

```
public function rules()
{
    return [
        'old_password' => ['required', 'min:6', new CurrentPasswordCheckRule],
        'password' => ['required', 'min:6', 'confirmed', 'different:old_password'],
        'password_confirmation' => ['required', 'min:6'],
    ];
}
```
## Table of Contents

* [Demo](#demo)
* [Documentation](#documentation)
* [File Structure](#file-structure)
* [Browser Support](#browser-support)
* [Resources](#resources)

## Demo

| Register | Login | Dashboard |
| --- | --- | ---  |
| [![Register](screens/Register.png)](https://light-bootstrap-dashboard-laravel.creative-tim.com/register?ref=lbdl-readme)  | [![Login](screens/Login.png)](https://light-bootstrap-dashboard-laravel.creative-tim.com/login?ref=lbdl-readme)  | [![Dashboard](screens/Dashboard.png)](https://light-bootstrap-dashboard-laravel.creative-tim.com/?ref=lbdl-readme)

| Profile Page | Users Page | Tables Page  |
| --- | --- | ---  |
| [![Profile Page](screens/Profile.png)](https://light-bootstrap-dashboard-laravel.creative-tim.com/profile?ref=lbdlp-readme)  | [![Users Page](screens/Users.png)](https://light-bootstrap-dashboard-laravel.creative-tim.com/user?ref=lbdl-readme) | [![Tables Page](screens/Tables.png)](https://light-bootstrap-dashboard-laravel.creative-tim.com/table-list?ref=lbdl-readme)
[View More](https://light-bootstrap-dashboard-laravel.creative-tim.com/?ref=lbdl-readme)

## File Structure
```
+--- app
│   ├── Console
│   │   └── Kernel.php
│   ├── Exceptions
│   │   └── Handler.php
│   ├── Http
│   │   ├── Controllers
│   │   │   ├── Auth
│   │   │   │   ├── ForgotPasswordController.php
│   │   │   │   ├── LoginController.php
│   │   │   │   ├── RegisterController.php
│   │   │   │   ├── ResetPasswordController.php
│   │   │   │   └── VerificationController.php
│   │   │   ├── Controller.php
│   │   │   ├── HomeController.php
│   │   │   ├── PageController.php
│   │   │   ├── ProfileController.php
│   │   │   └── UserController.php
│   │   ├── Kernel.php
│   │   ├── Middleware
│   │   │   ├── Authenticate.php
│   │   │   ├── CheckForMaintenanceMode.php
│   │   │   ├── EncryptCookies.php
│   │   │   ├── RedirectIfAuthenticated.php
│   │   │   ├── TrimStrings.php
│   │   │   ├── TrustProxies.php
│   │   │   └── VerifyCsrfToken.php
│   │   └── Requests
│   │       ├── PasswordRequest.php
│   │       ├── ProfileRequest.php
│   │       └── UserRequest.php
│   ├── Providers
│   │   ├── AppServiceProvider.php
│   │   ├── AuthServiceProvider.php
│   │   ├── BroadcastServiceProvider.php
│   │   ├── EventServiceProvider.php
│   │   └── RouteServiceProvider.php
│   ├── Rules
│   │   └── CurrentPasswordCheckRule.php
│   └── User.php
├── artisan
├── bootstrap
│   ├── app.php
│   └── cache
│       ├── packages.php
│       └── services.php
├── composer.json
├── composer.lock
├── config
│   ├── app.php
│   ├── auth.php
│   ├── broadcasting.php
│   ├── cache.php
│   ├── database.php
│   ├── filesystems.php
│   ├── hashing.php
│   ├── logging.php
│   ├── mail.php
│   ├── queue.php
│   ├── services.php
│   ├── session.php
│   └── view.php
├── database
│   ├── factories
│   │   └── UserFactory.php
│   ├── migrations
│   │   ├── 2014_10_12_000000_create_users_table.php
│   │   └── 2014_10_12_100000_create_password_resets_table.php
│   └── seeds
│       ├── DatabaseSeeder.php
│       └── UsersTableSeeder.php
├── package.json
├── phpunit.xml
├── public
│   ├── favicon.ico
│   ├── index.php
│   ├── light-bootstrap
│   │   ├── css
│   │   │   ├── bootstrap.min.css
│   │   │   ├── demo.css
│   │   │   └── light-bootstrap-dashboard.css
│   │   ├── fonts
│   │   │   ├── nucleo-icons.eot
│   │   │   ├── nucleo-icons.svg
│   │   │   ├── nucleo-icons.ttf
│   │   │   ├── nucleo-icons.woff
│   │   │   └── nucleo-icons.woff2
│   │   ├── img
│   │   │   ├── apple-icon.png
│   │   │   ├── bg5.jpg
│   │   │   ├── default-avatar.png
│   │   │   ├── faces
│   │   │   │   ├── face-0.jpg
│   │   │   │   ├── face-1.jpg
│   │   │   │   ├── face-2.jpg
│   │   │   │   ├── face-3.jpg
│   │   │   │   ├── face-4.jpg
│   │   │   │   ├── face-5.jpg
│   │   │   │   ├── face-6.jpg
│   │   │   │   ├── face-7.jpg
│   │   │   │   └── tim_vector.jpe
│   │   │   ├── favicon.ico
│   │   │   ├── full-screen-image-2.jpg
│   │   │   ├── full-screen-image-3.jpg
│   │   │   ├── laravel.svg
│   │   │   ├── loading-bubbles.svg
│   │   │   ├── mask.png
│   │   │   ├── new_logo.png
│   │   │   ├── sidebar-1.jpg
│   │   │   ├── sidebar-2.jpg
│   │   │   ├── sidebar-3.jpg
│   │   │   ├── sidebar-4.jpg
│   │   │   ├── sidebar-5.jpg
│   │   │   └── tim_80x80.png
│   │   ├── js
│   │   │   ├── core
│   │   │   │   ├── bootstrap.min.js
│   │   │   │   ├── jquery.3.2.1.min.js
│   │   │   │   └── popper.min.js
│   │   │   ├── demo.js
│   │   │   ├── light-bootstrap-dashboard.js
│   │   │   └── plugins
│   │   │       ├── bootstrap-datepicker.js
│   │   │       ├── bootstrap-notify.js
│   │   │       ├── bootstrap-switch.js
│   │   │       ├── chartist.min.js
│   │   │       └── nouislider.min.js
│   │   └── sass
│   │       ├── lbd
│   │       │   ├── _alerts.scss
│   │       │   ├── _bootstrap-switch.scss
│   │       │   ├── _buttons.scss
│   │       │   ├── _cards.scss
│   │       │   ├── _chartist.scss
│   │       │   ├── _checkbox-radio-switch.scss
│   │       │   ├── _dropdown.scss
│   │       │   ├── _footers.scss
│   │       │   ├── _inputs.scss
│   │       │   ├── _misc.scss
│   │       │   ├── mixins
│   │       │   │   ├── _buttons.scss
│   │       │   │   ├── _cards.scss
│   │       │   │   ├── _chartist.scss
│   │       │   │   ├── _icons.scss
│   │       │   │   ├── _inputs.scss
│   │       │   │   ├── _labels.scss
│   │       │   │   ├── _morphing-buttons.scss
│   │       │   │   ├── _navbars.scss
│   │       │   │   ├── _social-buttons.scss
│   │       │   │   ├── _tabs.scss
│   │       │   │   ├── _transparency.scss
│   │       │   │   └── _vendor-prefixes.scss
│   │       │   ├── _mixins.scss
│   │       │   ├── _navbars.scss
│   │       │   ├── _partial-nucleo-icons.scss
│   │       │   ├── plugins
│   │       │   │   ├── _animate.scss
│   │       │   │   └── _datetime-picker.scss
│   │       │   ├── _responsive.scss
│   │       │   ├── _sidebar-and-main-panel.scss
│   │       │   ├── _tables.scss
│   │       │   ├── _typography.scss
│   │       │   └── _variables.scss
│   │       └── light-bootstrap-dashboard.scss
│   └── robots.txt
├── resources
│   ├── js
│   │   ├── app.js
│   │   ├── bootstrap.js
│   │   └── components
│   │       └── ExampleComponent.vue
│   ├── lang
│   │   └── en
│   │       ├── auth.php
│   │       ├── pagination.php
│   │       ├── passwords.php
│   │       └── validation.php
│   ├── sass
│   │   ├── app.scss
│   │   └── _variables.scss
│   └── views
│       ├── alerts
│       │   ├── errors.blade.php
│       │   ├── error_self_update.blade.php
│       │   ├── feedback.blade.php
│       │   ├── migrations_check.blade.php
│       │   └── success.blade.php
│       ├── auth
│       │   ├── login.blade.php
│       │   ├── passwords
│       │   │   ├── email.blade.php
│       │   │   └── reset.blade.php
│       │   ├── register.blade.php
│       │   └── verify.blade.php
│       ├── dashboard.blade.php
│       ├── layouts
│       │   ├── app.blade.php
│       │   ├── footer
│       │   │   └── nav.blade.php
│       │   └── navbars
│       │       ├── navbar.blade.php
│       │       ├── navs
│       │       │   ├── auth.blade.php
│       │       │   └── guest.blade.php
│       │       └── sidebar.blade.php
│       ├── pages
│       │   ├── icons.blade.php
│       │   ├── maps.blade.php
│       │   ├── notifications.blade.php
│       │   ├── table.blade.php
│       │   ├── typography.blade.php
│       │   └── upgrade.blade.php
│       ├── profile
│       │   └── edit.blade.php
│       ├── users
│       │   └── index.blade.php
│       └── welcome.blade.php
├── routes
│   ├── api.php
│   ├── channels.php
│   ├── console.php
│   └── web.php
├── server.php
├── storage
│   ├── app
│   │   └── public
│   ├── framework
│   │   ├── cache
│   │   │   └── data
│   │   │       ├── 1a
│   │   │       │   └── 43
│   │   │       │       └── 1a43d4d8a10fba3a5857264007fd4f8494990eb9
│   │   │       └── f0
│   │   │           └── a7
│   │   │               └── f0a70bbe9f0a2fbd95a4dee84fb8f092e00acc29
│   │   ├── sessions
│   │   │   └── bDASqge6ugwwnupCJQxc6UpeCzU5mWnlBf3OtHbV
│   │   ├── testing
│   │   └── views
│   └── logs
│       ├── laravel-2019-08-07.log
│       ├── laravel-2019-08-08.log
│       └── laravel-2019-08-09.log
├── tests
│   ├── CreatesApplication.php
│   ├── Feature
│   │   └── ExampleTest.php
│   ├── TestCase.php
│   └── Unit
│       └── ExampleTest.php
```

## Browser Support

At present, we officially aim to support the last two versions of the following browsers:

<img src="https://github.com/creativetimofficial/public-assets/blob/master/logos/chrome-logo.png?raw=true" width="64" height="64"> <img src="https://raw.githubusercontent.com/creativetimofficial/public-assets/master/logos/firefox-logo.png" width="64" height="64"> <img src="https://raw.githubusercontent.com/creativetimofficial/public-assets/master/logos/edge-logo.png" width="64" height="64"> <img src="https://raw.githubusercontent.com/creativetimofficial/public-assets/master/logos/safari-logo.png" width="64" height="64"> <img src="https://raw.githubusercontent.com/creativetimofficial/public-assets/master/logos/opera-logo.png" width="64" height="64">


## Resources
- Demo: <https://www.creative-tim.com/live/light-bootstrap-dashboard-laravel?ref=lbdl-readme>
- Download Page: <https://www.creative-tim.com/product/light-bootstrap-dashboard-laravel?ref=lbdl-readme>
- Documentation: <https://www.creative-tim.com/live/light-bootstrap-dashboard-laravel/?start-page=/docs/tutorial-components.html&ref=lbdl-readme>
- License Agreement: <https://www.creative-tim.com/license?ref=lbdl-readme>
- Support: <https://www.creative-tim.com/contact-us?ref=lbdl-readme>
- Issues: [Github Issues Page](https://github.com/laravel-frontend-presets/light-bootstrap-dashboard-laravel/issues)
- **Dashboards:**

| HTML | LARAVEL |
| --- | --- |
| [![Light Bootstrap Dashboard HTML](https://s3.amazonaws.com/creativetim_bucket/products/32/original/opt_lbd_thumbnail.jpg?1513017628)](https://demos.creative-tim.com/light-bootstrap-dashboard/examples/dashboard.html?ref=lbdl-readme) | [![Light Bootstrap Dashboard Laravel](https://s3.amazonaws.com/creativetim_bucket/products/213/original/opt_lbd_laravel_thumbnail.jpg?1567087339)](https://www.creative-tim.com/live/light-bootstrap-dashboard-laravel?ref=lbdl-readme)


