<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateQuestionAnswerRecordTable extends Migration
{
    public function up()
    {
        Schema::create('question_answer_records', function (Blueprint $table) {
            $table->id('question_id');
            $table->text('question');
            $table->string('answer');
            $table->integer('score');
            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('question_answer_records');
    }
}
