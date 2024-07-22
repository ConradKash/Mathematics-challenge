<?php


namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Relations\HasMany;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasManyThrough;
use Illuminate\Database\Eloquent\Relations\BelongsTo;


class School extends Model
{
    use HasFactory;

    protected $fillable = [
        'name', 'district', 'registration_number', 'representative_email', 'representative_name', 'validated'
    ];
    public function attempts()
    {
        return $this->hasMany(ParticipantChallengeAttempt::class, 'registration_number', 'registration_number');
    }


    public function challengeAttempts(): HasManyThrough
    {
    return $this->hasManyThrough(
        ParticipantChallengeAttempt::class,
        Participant::class,
        'school_id', // Foreign key on participants table
        'participant_id', // Foreign key on challenge_attempts table
        'id', // Local key on schools table
        'id'  // Local key on participants table
    );
    }

    public function participants()
    { 
    return $this->hasMany(Participant::class, 'registration_number', 'registration_number');
    }



    // Method to calculate average score
    public function averageScore()
    {
        return $this->attempts()->avg('score');
    }
    public function participantChallengeAttempts(): HasMany
    {
        return $this->hasMany(ParticipantChallengeAttempt::class, 'registration_number', 'registration_number');
    }
}