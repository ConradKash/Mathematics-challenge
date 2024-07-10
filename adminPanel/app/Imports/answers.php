<?php

namespace App\Imports;
use App\Models\Question;
use Maatwebsite\Excel\Concerns\ToModel;
use Maatwebsite\Excel\Concerns\WithHeadingRow;

use Illuminate\Support\Collection;

class answers implements ToModel,WithHeadingRow
{
   
    public function model(array $row)
    {
        //Find the corresponding question by questionId
        $question = Question::where('id', $row[1])->first();
        // If question exists, update its answer column
        $question->answer = $row['question']; 
            $question->save();
    }
}
