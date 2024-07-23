<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\Question;
use Faker\Factory as FakerFactory;

class QuestionSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $faker = FakerFactory::create();

        for ($i = 0; $i < 80; $i++) {
            Question::create([
                'challenge_id' => rand(1, 6),
                'question' => $faker->sentence(),
                'answer' => $faker->sentence(),
                'score' => 3,
                'created_at' => now(),
                'updated_at' => now(),
            ]);
        }
    }
}
