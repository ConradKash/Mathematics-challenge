<?php

namespace App\Imports;
use App\Models\Question;
use Maatwebsite\Excel\Concerns\ToModel;
class challenges implements ToModel
{
    
    public function model(array $row)
    {
            
            // Define how to create a model from the Excel row data
                 return new Question([
                'id'=> 1,   
                'challenge_id'=> 1,
                'question'=> $row['question'],
                'answer'=> $row[''],
                'score'=> $row['score'],
            ]);
    }
}
