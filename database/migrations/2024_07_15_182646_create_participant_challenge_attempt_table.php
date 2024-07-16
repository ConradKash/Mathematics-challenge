<?
use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateChallengeAttemptTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('challenge_attempt', function (Blueprint $table) {
            $table->id('attempt_id');
            $table->foreignId('participant_id')->constrained('participant')->onDelete('cascade');
            $table->foreignId('challenge_id')->constrained('challenge')->onDelete('cascade');
            $table->integer('score');
            $table->integer('total_score');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('challenge_attempt');
    }
}
