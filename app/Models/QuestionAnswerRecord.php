<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class QuestionAnswerRecord extends Model
{
    use HasFactory;
    protected $primaryKey = 'question_id';

    protected $fillable = ['question', 'answer', 'score'];

    public function challenges()
    {
        return $this->belongsToMany(Challenge::class, 'question_answer_record', 'question_id', 'challenge_id')
            ->withTimestamps();
    }

    public function attempts()
    {
        return $this->hasMany(ParticipantChallengeAttempt::class, 'question_id');
    }
    public function challenge()
    {
        return $this->belongsTo(Challenge::class, 'challenge_id');
    }
}
