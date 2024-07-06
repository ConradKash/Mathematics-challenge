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
        Schema::create('questions', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('challenge_id');
            $table->foreign('challenge_id')->references('id')->on('challenges')->onDelete('cascade')->OnUpdate('cascade');
            $table->text('question');   
            $table->text('answer'); 
            $table->unsignedBigInteger('score');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('questions', function (Blueprint $table) {
            $table->dropForeign('questions_challenge_id_foreign');
            $table->dropIndex('questions_challenge_id_index');
            $table->dropColumn('challenge_id');
        });
    }
};
