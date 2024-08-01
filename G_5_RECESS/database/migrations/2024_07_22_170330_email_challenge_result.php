<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('email_challenge_results', function (Blueprint $table) {
            $table->id();
            $table->foreignId('challenge')->constrained('challenges')->onDelete('cascade');
            $table->integer('status');
            $table->timestamps();
        });
    }

   

    public function down()
    {
        Schema::dropIfExists('email_challenge_results');
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    
};
