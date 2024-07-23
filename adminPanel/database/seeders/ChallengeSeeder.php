<?php

namespace Database\Seeders;

use App\Models\Challenge;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Faker\Factory as Faker;

class ChallengeSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
            $faker = Faker::create();

            for ($i = 0; $i < 6; $i++) {
                $startDate = $faker->dateTimeBetween('-1 month', '+1 month');
                $endDate = $faker->dateTimeBetween($startDate, '+2 months');

                Challenge::create([
                    'duration' => $faker->randomNumber(2),
                    'startDate' => $startDate,
                    'endDate' => $endDate,
                    'questionCount' => $faker->randomNumber(2),
                    'created_at' => now(),
                    'updated_at' => now(),
                ]);
            }
        }
    }

