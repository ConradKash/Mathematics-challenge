<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Challenge extends Model
{
    use HasFactory;
    protected $fillable = [
        'id',
        'duration',
        'startDate',
        'endDate',
        'questionCount',
        // Add any other fields that are fillable
    ];
}
