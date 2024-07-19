<?php

namespace App\Imports;

use App\Models\Question;
use Maatwebsite\Excel\Concerns\ToModel;
use Maatwebsite\Excel\Concerns\WithHeadingRow;

class challenges implements ToModel,  WithHeadingRow
{   private $challenge_id;
    public function  __construct($challenge_id){
        $this->challenge_id = $challenge_id;
    }
    public function model(array $row)
    {
        $challenge_id = $this->challenge_id;

        // Define how to create a model from the Excel row data
        return new Question([
            'challenge_id' => $challenge_id ?? NULL,
            'question' => $row['questions'],
            'answer' => $row['answer'],
            'score' => $row['score'],
        ]);
    }
}
