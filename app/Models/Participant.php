<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany; 

class Participant extends Model
{
    use HasFactory;

    protected $primaryKey = 'participant_id'; // Assuming you want to use 'participant_id' as the primary key

    protected $fillable = [
        'username',
        'firstname',
        'lastname',
        'emailAddress',
        'dob',
        'registration_number',
        'imagePath',
    ];

    protected $casts = [
        'dob' => 'date', // Casting 'dob' to date type
    ];


    public function school()
    {
        return $this->belongsTo(School::class, 'registration_number', 'registration_number');
    }

    public function challengeAttempts(): HasMany
    {
        return $this->hasMany(ParticipantChallengeAttempt::class, 'participant_id');
    }

    public function viewReports()
    {
        return $this->hasMany(ViewReport::class);
    }
}
