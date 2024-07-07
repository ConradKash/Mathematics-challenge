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
        Schema::create('attempts', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('challenge_id');
            $table->foreign('challenge_id')->references('id')->on('challenges')->onDelete('cascade')->OnUpdate('cascade');
            $table->unsignedBigInteger('totalscore');
            $table->dateTime('totalTimeTaken', 0);
            $table->unsignedBigInteger('attemptsMade');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('attempts', function (Blueprint $table) {
            $table->dropForeign('attempts_challenge_id_foreign');
            $table->dropIndex('attempts_challenge_id_index');
            $table->dropColumn('challenge_id');
        });
    }
};
