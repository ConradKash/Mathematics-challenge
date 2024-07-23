<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\ParticipantAttempt;

class ParticipantAttemptSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $faker = \Faker\Factory::create();

        for ($i = 6; $i <= 89; $i++) {
            $participantId = $faker->numberBetween(7, 30);
            $attemptId = $faker->numberBetween(1, 305);
            for ($j = 1; $j <= 40; $j++) {
                $questionId = $faker->numberBetween(1, 80);
                $score = $faker->numberBetween(0, 100);
                $timeTaken = $faker->numberBetween(1, 60);
                $createdAt = $faker->dateTimeBetween('-1 year', 'now');
                $updatedAt = $faker->dateTimeBetween($createdAt, 'now');

                ParticipantAttempt::create([
                    'participant_id' => $participantId,
                    'attempt_id' => $attemptId,
                    'question_id' => $questionId,
                    'score' => $score,
                    'timeTaken' => $timeTaken,
                    'created_at' => $createdAt,
                    'updated_at' => $updatedAt,
                ]);
            }
        }

        for ($i = 1; $i <= 89; $i++) {
            $participantId = $faker->numberBetween(1, 100);
            $attemptId = $faker->unique()->randomNumber();
            $score = $faker->numberBetween(0, 100);
            $timeTaken = $faker->numberBetween(1, 60);
            $createdAt = $faker->dateTimeBetween('-1 year', 'now');
            $updatedAt = $faker->dateTimeBetween($createdAt, 'now');

            ParticipantAttempt::create([
                'id' => $i,
                'participant_id' => $participantId,
                'attempt_id' => $attemptId,
                'score' => $score,
                'timeTaken' => $timeTaken,
                'created_at' => $createdAt,
                'updated_at' => $updatedAt,
            ]);
        }
    }
}
