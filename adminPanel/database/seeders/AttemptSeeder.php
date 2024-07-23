<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\Attempt;
use Faker\Factory as Faker;

class AttemptSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $faker = Faker::create();

        for ($participantId = 2; $participantId <= 27; $participantId++) {
            for ($challengeId = 1; $challengeId <= 6; $challengeId++) {
                $attemptsMade = $faker->numberBetween(1, 3);

                for ($attempt = 1; $attempt <= $attemptsMade; $attempt++) {
                    $totalscore = $faker->numberBetween(0, 100);
                    $totalTimeTaken = $faker->numberBetween(0, 120);
                    $createdAt = $faker->dateTimeBetween('-5 month');
                    $updatedAt = $faker->dateTimeBetween($createdAt);

                    // Insert the attempt record into the database
                    Attempt::create([
                        'challenge_id' => $challengeId,
                        'totalscore' => $totalscore,
                        'totalTimeTaken' => $totalTimeTaken,
                        'attemptsMade' => $attempt,
                        'created_at' => $createdAt,
                        'updated_at' => $updatedAt,
                    ]);
                }
            }
        }
    }
}
