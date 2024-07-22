<?php
// app/Models/Challenge.php
namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Challenge extends Model
{
    use HasFactory;

    protected $primaryKey = 'challenge_id'; // Assuming you want to use 'challenge_id' as the primary key

    protected $fillable = [
        'title',
        'description',
        'starting_date',
        'closing_date',
        'duration_minutes',
    ];

    protected $casts = [
        'starting_date' => 'date', // Casting 'starting_date' to date type
        'closing_date' => 'date', // Casting 'closing_date' to date type
    ];

    public function questionAnswerRecords()
    {
        return $this->hasMany(QuestionAnswerRecord::class, 'challenge_id');
    }

    public function attempts()
    {
        return $this->hasMany(ParticipantChallengeAttempt::class, 'challenge_id');
    }

    public function viewReports()
    {
        return $this->hasMany(ViewReport::class);
    }

}
