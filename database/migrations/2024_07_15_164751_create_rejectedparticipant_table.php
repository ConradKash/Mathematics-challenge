<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateRejectedParticipantTable extends Migration
{
    public function up()
    {
        Schema::create('rejectedparticipant', function (Blueprint $table) {
            $table->id();
            $table->string('username');
            $table->string('firstname');
            $table->string('lastname');
            $table->string('emailAddress');
            $table->date('dob');
            $table->string('registration_number');
            $table->string('imagePath')->nullable();
            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('rejectedparticipant');
    }
}
