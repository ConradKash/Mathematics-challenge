<?php
// app/Models/Challenge.php
namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Challenge extends Model
{
    use HasFactory;

    protected $fillable = [
        'title',
        'description',
        'starting_date',
        'closing_date',
        'duration_minutes',
    ];

    protected $table = 'challenge'; // Ensure this is correct
}
