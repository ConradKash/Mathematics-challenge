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
        Schema::create('participant_attempts', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('participant_id');
            $table->foreign('participant_id')->references('id')->on('participants')->onDelete('cascade')->OnUpdate('cascade');
            $table->unsignedBigInteger('question_id');
            $table->foreign('question_id')->references('id')->on('questions')->onDelete('cascade')->OnUpdate('cascade');
            $table->unsignedBigInteger('attempt_id');
            $table->foreign('attempt_id')->references('id')->on('attempts')->onDelete('cascade')->OnUpdate('cascade');
            $table->unsignedBigInteger('score');
            $table->dateTime('timeTaken', 0);
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('participant_attempts', function (Blueprint $table) {
            $table->dropForeign('participant_attempts_participant_id_foreign');
            $table->dropIndex('participant_attempts_participant_id_index');
            $table->dropColumn('participant_id');
            $table->dropForeign('participant_attempts_attempt_id_foreign');
            $table->dropIndex('participant_attempts_attempt_id_index');
            $table->dropColumn('attempt_id');
        });
    }
};
