<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Question extends Model
{
    use HasFactory;
    protected $fillable = [
        'id',
        'challenge_id',
        'question',
        'answer',
        'score',
        // Add any other fields that are fillable
    ];
}
