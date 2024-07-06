<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class SchoolRepresentative extends Model
{
    protected $fillable = ['name', 'email', 'phone', 'school_id', 'password'];
}
