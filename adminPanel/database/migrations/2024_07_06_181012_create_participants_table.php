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
        Schema::create('participants', function (Blueprint $table) {
            $table->id();
            $table->string('userName');
            $table->string('firstName');
            $table->string('lastName');
            $table->unsignedBigInteger('school_id');
            $table->foreign('school_id')->references('id')->on('schools')->onDelete('cascade')->OnUpdate('cascade');
            $table->string('profilePicture');
            $table->string('emailAddress')->unique();
            $table->date('dateOfBirth');
            $table->string('password');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('participants', function (Blueprint $table) {
            $table->dropForeign('participants_school_id_foreign');
            $table->dropIndex('participants_school_id_index');
            $table->dropColumn('school_id');
        });
    }
};
