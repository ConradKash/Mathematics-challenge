<?php

namespace App\Imports;
use App\Models\Question;
use Maatwebsite\Excel\Concerns\ToModel;
use Maatwebsite\Excel\Concerns\WithHeadingRow;

class challenges implements ToModel,  WithHeadingRow
{
    
    public function model(array $row)
    {
            
            // Define how to create a model from the Excel row data
                 return new Question([
                'id'=> $row['id'],   
                'challenge_id'=> $row['challenge_id'],
                'question'=> $row['questions'],
                'answer'=> $row['answer'],
                'score'=> $row['score'],
            ]);
    }
}
