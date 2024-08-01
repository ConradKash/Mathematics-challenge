<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class ViewReport extends Model
{
    use HasFactory;

    protected $table = 'view_report';

    protected $fillable = [
        'challenge_id',
        'participant_id',
        'question_text',
        'answer',
        'is_correct',
    ];

    /**
     * Get the challenge that owns the view report.
     */
    public function challenge()
    {
        return $this->belongsTo(Challenge::class);
    }

    /**
     * Get the participant that owns the view report.
     */
    public function participant()
    {
        return $this->belongsTo(Participant::class);
    }
}
