<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('rejecteds', function (Blueprint $table) {
            $table->id();
            $table->string('userName');
            $table->string('firstName');
            $table->string('lastName');
            $table->string('profilePicture');
            $table->string('emailAddress')->unique();
            $table->date('dateOfBirth');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('rejecteds');
    }
};
