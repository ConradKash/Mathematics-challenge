<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateChallengeQuestionAnswerRecordTable extends Migration
{
    public function up()
    {
        Schema::create('challenge_question_answer_record', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('challenge_id');
            $table->unsignedBigInteger('question_id');
            $table->timestamps();

            // Add foreign key constraints
            $table->foreign('challenge_id')->references('id')->on('challenge')->onDelete('cascade');
            $table->foreign('question_id')->references('id')->on('question_answer_record')->onDelete('cascade');
        });
    }

    public function down()
    {
        Schema::dropIfExists('challenge_question_answer_record');
    }
}
