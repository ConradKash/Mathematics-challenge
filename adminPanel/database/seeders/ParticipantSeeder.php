<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\Participant;
use Faker\Factory as Faker;

class ParticipantSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $faker = Faker::create();

        for ($i = 1; $i <= 30; $i++) {
            Participant::create([
                'userName' => $faker->userName,
                'firstName' => $faker->firstName,
                'lastName' => $faker->lastName,
                'school_id' => rand(1, 4),
                'profilePicture' => $faker->imageUrl(),
                'emailAddress' => $faker->email,
                'dateOfBirth' => $faker->date(),
                'password' => bcrypt($faker->password),
                'created_at' => now(),
                'updated_at' => now(),
            ]);
        }
    }
}
