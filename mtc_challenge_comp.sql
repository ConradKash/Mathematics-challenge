-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 25, 2024 at 03:39 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mtc_challenge_comp`
--

-- --------------------------------------------------------

--
-- Table structure for table `answers`
--

CREATE TABLE `answers` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `question` text NOT NULL,
  `answer` text NOT NULL,
  `score` int(11) NOT NULL,
  `correct` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `attempts`
--

CREATE TABLE `attempts` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `participant_id` bigint(20) UNSIGNED NOT NULL,
  `challenge_id` bigint(20) UNSIGNED NOT NULL,
  `question_id` bigint(20) UNSIGNED NOT NULL,
  `answer` varchar(255) NOT NULL,
  `is_correct` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `attempts`
--

INSERT INTO `attempts` (`id`, `participant_id`, `challenge_id`, `question_id`, `answer`, `is_correct`, `created_at`, `updated_at`) VALUES
(1, 1, 4, 34, '1000', 1, '2024-07-25 10:39:58', '2024-07-25 10:39:58'),
(2, 1, 4, 8, '2', 0, '2024-07-25 10:39:58', '2024-07-25 10:39:58'),
(3, 1, 4, 57, '45', 0, '2024-07-25 10:39:58', '2024-07-25 10:39:58'),
(4, 1, 4, 69, '45', 0, '2024-07-25 10:39:58', '2024-07-25 10:39:58'),
(5, 1, 4, 54, '67', 0, '2024-07-25 10:39:58', '2024-07-25 10:39:58'),
(6, 1, 4, 76, '100', 0, '2024-07-25 10:39:58', '2024-07-25 10:39:58'),
(7, 1, 4, 81, '4', 0, '2024-07-25 10:39:58', '2024-07-25 10:39:58'),
(8, 1, 4, 17, '34', 0, '2024-07-25 10:39:58', '2024-07-25 10:39:58'),
(9, 1, 4, 96, '57', 0, '2024-07-25 10:39:58', '2024-07-25 10:39:58'),
(10, 1, 4, 40, '23', 0, '2024-07-25 10:39:58', '2024-07-25 10:39:58');

-- --------------------------------------------------------

--
-- Table structure for table `challenges`
--

CREATE TABLE `challenges` (
  `challenge_id` bigint(20) UNSIGNED NOT NULL,
  `challenge_name` varchar(255) NOT NULL,
  `difficulty` text NOT NULL,
  `starting_date` date NOT NULL,
  `closing_date` date NOT NULL,
  `time_allocation` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `challenges`
--

INSERT INTO `challenges` (`challenge_id`, `challenge_name`, `difficulty`, `starting_date`, `closing_date`, `time_allocation`, `created_at`, `updated_at`) VALUES
(1, 'Challenge 1', 'This is the first challenge', '2024-07-25', '2024-07-26', 10, '2024-07-25 03:55:02', '2024-07-25 03:55:02'),
(2, 'Challenge 2', 'This is challenge 2', '2024-07-25', '2024-07-27', 10, '2024-07-25 03:56:56', '2024-07-25 03:56:56'),
(3, 'Challenge 3', 'This is challenge 3', '2024-07-25', '2024-07-28', 5, '2024-07-25 03:57:49', '2024-07-25 03:57:49'),
(4, 'Challenge 4', 'This is Challenge 4', '2024-07-25', '2024-07-28', 15, '2024-07-25 03:58:47', '2024-07-25 03:58:47');

-- --------------------------------------------------------

--
-- Table structure for table `failed_jobs`
--

CREATE TABLE `failed_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `connection` text NOT NULL,
  `queue` text NOT NULL,
  `payload` longtext NOT NULL,
  `exception` longtext NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2014_10_12_000000_create_users_table', 1),
(2, '2014_10_12_100000_create_password_resets_table', 1),
(3, '2019_08_19_000000_create_failed_jobs_table', 1),
(4, '2019_12_14_000001_create_personal_access_tokens_table', 1),
(5, '2024_07_03_112009_create_schools_table', 1),
(6, '2024_07_15_164642_create_participant_table', 1),
(7, '2024_07_15_164751_create_rejectedparticipant_table', 1),
(8, '2024_07_15_180627_create_challenge_table', 1),
(9, '2024_07_15_180816_create_question_answer_record_table', 1),
(10, '2024_07_17_071123_create_question_answer_records_table', 1),
(11, '2024_07_17_132340_create_challenges_table', 1),
(12, '2024_07_22_084101_create_view_report_table', 1),
(13, '2024_07_22_140029_create_answers_table', 1),
(14, '2024_07_22_140051_create_questions_table', 1);

-- --------------------------------------------------------

--
-- Table structure for table `participants`
--

CREATE TABLE `participants` (
  `participant_id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `emailAddress` varchar(255) NOT NULL,
  `dob` date NOT NULL,
  `registration_number` varchar(255) NOT NULL,
  `imagePath` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `participants`
--

INSERT INTO `participants` (`participant_id`, `username`, `firstname`, `lastname`, `emailAddress`, `dob`, `registration_number`, `imagePath`, `created_at`, `updated_at`) VALUES
(1, 'Conrad', 'Omalla', 'Vicky', 'vicky@gmail.com', '1998-05-23', '8899', 'image.png', NULL, NULL),
(2, 'Cathy', 'Miles', 'Jane', 'cathymiles@gmail.com', '1998-05-23', '8899', 'xyzx.png', NULL, NULL),
(3, 'James', 'Osero', 'Umaru', 'emailjone@gmail.com', '2000-06-23', '9099', 'hyji.png', NULL, NULL),
(4, 'james', 'Peter', 'Obbo', 'Jamesjone@gmail.com', '1998-08-23', '8899', 'hyji.png', NULL, NULL),
(5, 'Opio', 'Clark', 'James', 'James@gmail.com', '1998-08-23', '9099', 'trdj.png', NULL, NULL),
(6, 'Janes', 'Ian', 'Milbert', 'jonah@gmail.com', '1998-08-23', '9099', 'frgg.png', NULL, NULL),
(7, 'Bridget', 'Teddy', 'Fahad', 'Fahad@gmail.com', '1996-08-23', '9099', 'hhhh.png', NULL, NULL),
(8, 'Wasswa', 'Jane', 'Kato', 'kato@gmail.com', '1995-08-23', '9099', 'ttth.png', NULL, NULL),
(9, 'Teddy', 'Obbo', 'Yen', 'Yen@gmail.com', '1996-08-23', '9099', 'jhhh.png', NULL, NULL),
(10, 'Ababiri', 'Teddy', 'Fahad', 'Jao@gmail.com', '1994-08-23', '9099', 'uuij.png', NULL, NULL),
(11, 'Jommie', 'Hen', 'Fahad', 'Hen@gmail.com', '1996-08-23', '9099', 'gfgf.png', NULL, NULL),
(12, 'Nana', 'Teddy', 'Fahad', 'Nana@gmail.com', '1996-08-23', '9099', 'jhhj.png', NULL, NULL),
(13, 'Yen', 'Peter', 'Fahad', 'Gahad@gmail.com', '1996-08-23', '9099', 'yrtr.png', NULL, NULL),
(14, 'Kiyimba', 'Fahad', 'Ababiri', 'ababira@gmail.com', '1997-07-12', '9077', 'fahad.png', NULL, NULL),
(15, 'Garang', 'John', 'Bernard', 'bernard@gmail.com', '1998-07-12', '9077', 'John.png', NULL, NULL),
(16, 'Paul', 'Appa', 'Joseph', 'Joseph@gmail.com', '1997-08-09', '9077', 'Joseph.png', NULL, NULL),
(17, 'Apio', 'Jane', 'Jena', 'jena@gmail.com', '1997-08-08', '9077', 'Jane.png', NULL, NULL),
(18, 'mukalazi', 'Kato', 'Mildred', 'Kato@gmail.com', '1997-09-08', '9077', 'Kato.png', NULL, NULL),
(19, 'Ritah', 'Paul', 'Qing', 'Qing@gmail.com', '1997-09-07', '9077', 'Qing.png', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `participant_challenge_attempts`
--

CREATE TABLE `participant_challenge_attempts` (
  `attempt_id` bigint(20) UNSIGNED NOT NULL,
  `participant_id` bigint(20) UNSIGNED NOT NULL,
  `challenge_id` bigint(20) UNSIGNED NOT NULL,
  `score` int(11) NOT NULL,
  `total_score` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `password_resets`
--

CREATE TABLE `password_resets` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) NOT NULL,
  `tokenable_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `token` varchar(64) NOT NULL,
  `abilities` text DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `question_answer_records`
--

CREATE TABLE `question_answer_records` (
  `question_id` bigint(20) UNSIGNED NOT NULL,
  `question` text NOT NULL,
  `correct_answer` varchar(255) NOT NULL,
  `score` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `question_answer_records`
--

INSERT INTO `question_answer_records` (`question_id`, `question`, `correct_answer`, `score`, `created_at`, `updated_at`) VALUES
(1, 'What is the sum of 63 and 29?', '92', 1, '2024-07-25 02:33:18', '2024-07-25 02:33:18'),
(2, 'How many grams are in 1 kilogram?', '1000', 1, '2024-07-25 02:33:18', '2024-07-25 02:33:18'),
(3, 'How many millimeters are there in 2 centimeters?', '20', 1, '2024-07-25 02:33:18', '2024-07-25 02:33:18'),
(4, 'Fill in the missing number: 4, 8, 12, ..., 20.', '16', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(5, 'What comes next in this sequence: 3, 6, 9, 12, ...?', '15', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(6, 'If a pencil costs 200 shillings and you buy 5 pencils, how much money do you need?', '1000', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(7, 'How much money is 2 coins of 50 shillings each?', '100', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(8, 'If a chocolate bar costs 500 shillings and you buy 2 bars, how much do you spend in total?', '1000', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(9, 'If it is 9:45 AM now, what time will it be in 1 hour and 30 minutes?', '                         11:15 ', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(10, 'How many seconds are there in 1 minute?', '60', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(11, 'Fill in the missing number: 7, 14, 21, ..., 35.', '28', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(12, 'How many meters are there in 3 kilometers?', '3000', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(13, 'Convert 5 liters to milliliters.', '5000', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(14, ' Name a shape with 6 sides.', '                          Hexagon', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(15, 'Which shape has 3 sides and all angles less than 90 degrees?', '               Acute triangle', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(16, 'If a book costs 1500 shillings and a pencil costs 300 shillings, how much will you spend if you buy both?', '1800', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(17, 'Tom has 12 marbles. He gives 4 to his friend. How many marbles does he have left?', '                        8 marbles', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(18, 'There are 30 candies in a jar. If 15 candies are taken out, how many candies are left?', '15', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(19, 'How many minutes are in an hour?', '60', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(20, 'Which number is odd: 12, 15, 18, 21?', '15', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(21, 'How many sides does a hentagon have?', '7', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(22, 'If a dozen eggs cost $3.60, how much does one egg cost?', '                                 $0.30', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(23, 'Which shape has no corners?', '                                Circle', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(24, 'If you have 8 crayons and give away 3, how many do you have left?', '5', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(25, 'What is the next number in the sequence: 4, 8, 12, ...?', '16', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(26, 'How many sides does a parallelogram have?', '4', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(27, 'If you buy 5 packs of stickers for $1.50 each, how much do you spend in total?', '                                 $7.50', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(28, 'How many sides does a cylinder have?', '4', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(29, 'Which number is the smallest: 9, 13, 7, 16?', '7', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(30, 'How many vertices does a cube have?', '8', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(31, 'Which number is the largest: 28, 35, 24, 32?', '35', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(32, 'How many sides does a nonagon have?', '9', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(33, 'If a book has 120 pages and you read 30 pages, how many pages are left?', '90', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(34, 'How many liters are in a kiloliter?', '1000', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(35, 'Which number is even: 23, 29, 32, 37?', '32', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(36, 'How many months are in a year?', '12', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(37, 'If you have 9 marbles and give away 5, how many do you have left?', '4', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(38, 'What is the next number in the sequence: 5, 10, 15, ...?', '20', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(39, 'How many sides does a rhombus have?', '4', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(40, 'If a dozen pencils cost $1.20, how much does one pencil cost?', '                                 $0.10', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(41, 'What is the product of 23 and 8?', '184', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(42, 'How many sides does a triangle have?', '3', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(43, 'If Jane buys 4 apples at 25 cents each, how much does she pay in total?', '                                 $1.00', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(44, 'What is the next number in the sequence: 2, 4, 6, 8, ...?', '10', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(45, 'How many minutes are there in 2 hours?', '120', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(46, 'If you have $5 and you spend $2.50, how much money do you have left?', '                                 $2.50', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(47, 'How many centimeters are in 1 meter?', '100', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(48, 'What is the lowest common multiple of 3 and 4?', '12', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(49, 'If the ratio of girls to boys in a class is 3:2 and there are 15 girls, how many boys are there?', '10', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(50, 'What is the next number in the sequence: 3, 6, 9, 12, ...?', '15', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(51, 'Calculate the perimeter of a rectangle with length 8 cm and width 5 cm.', '                                26 cm', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(52, 'If a movie starts at 3:30 PM and lasts for 2 hours, when does it end?', '                            5:30 PM', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(53, 'John has 15 marbles and gives 3 to his friend. How many marbles does John have left?', '12', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(54, 'How many edges does a cube have?', '12', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(55, 'How many grams are there in 1 kilogram?', '1000', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(56, 'If a toy costs $8 and Sarah buys 2 toys, how much money does she spend?', '                                     $16', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(57, 'What is the missing number in the sequence: 1, 4, 9, 16, ...?', '25', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(58, 'What is the highest common factor of 12 and 18?', '6', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(59, 'How many liters are there in 3.5 deciliters?', '0.35', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(60, 'What is the sum of the interior angles of a triangle?', '                   180 degrees', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(61, 'If a train journey takes 4 hours and 45 minutes, how long is it in minutes?', '                   285 minutes', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(62, 'If 1 US dollar is equal to 100 Kenyan shillings, how many Kenyan shillings are there in 5 dollars?', '500', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(63, 'Lucy has 24 pencils. She gives away 8 pencils. How many pencils does she have left?', '16', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(64, 'How many meters are there in 350 centimeters?', '                      3.5 meters', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(65, 'What is the next number in the sequence: 1, 3, 6, 10, ...?', '15', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(66, 'How many sides does a hexagon have?', '6', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(67, 'If a car travels at 60 km/h, how long will it take to travel 180 kilometers?', '                             3 hours', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(68, 'If it is 9:45 AM now, what time will it be in 2 hours and 15 minutes?', '                         12:00 PM', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(69, 'What is the next number in the sequence: 2, 4, 8, 16, ...?', '32', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(70, 'Calculate the area of a square with side length 6 cm.', '                 36 square cm', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(71, 'If a book costs $12 and Sara has $20, how much change will she get if she buys the book?', '                                       $8', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(72, 'What is the sum of the angles in a quadrilateral?', '                   360 degrees', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(73, 'How many kilometers are there in 2000 meters?', '                  2 kilometers', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(74, 'If the ratio of boys to girls in a class is 3:2 and there are 15 girls, how many boys are there?', '18', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(75, 'Calculate the volume of a cube with edge length 3 cm.', '                    27 cubic cm', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(76, 'If 25% of the students in a class are absent, and there are 40 students present, how many students are absent?', '10', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(77, 'If a car travels at 60 km/h, how long will it take to travel 180 kilometers?', '                             3 hours', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(78, 'How many diagonals does a hexagon have?', '9', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(79, 'Tom has 5 apples. He gives 2 apples to his sister. How many apples does he have left?', '3', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(80, 'How many meters are there in 350 centimeters?', '                      3.5 meters', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(81, 'If it is 3:15 PM now, what time was it 2 hours ago?', '                            1:15 PM', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(82, 'How many lines of symmetry does the letter \"H\" have?', '2', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(83, 'What is the next number in the sequence: 5, 10, 15, 20, ...?', '25', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(84, 'How many millimeters are there in 2.5 meters?', '2500', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(85, 'How many faces does a rectangular prism have?', '6', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(86, 'There are 24 students in a class. If 1/3 of the students are absent, how many students are present?', '16', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(87, 'If a plane journey takes 3 hours and 30 minutes, how long is it in minutes?', '                   210 minutes', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(88, 'If a pen costs $1.50 and John buys 4 pens, how much money does he spend?', '                            $6.00', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(89, 'What is the sum of the interior angles of a quadrilateral?', '                   360 degrees', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(90, 'What is the next number in the sequence: 2, 5, 10, 17, ...?', '26', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(91, 'How many centimeters are there in 2.5 meters?', '250', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(92, 'What is the perimeter of a square with side length 12 cm?', '                                48 cm', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(93, 'How many centimeters are there in 3 meters?', '                              300 cm', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(94, 'Peter has 40 marbles. He gives away 15 marbles. How many marbles does he have left?', '25', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(95, 'What is the next number in the sequence: 7, 14, 21, 28, ...?', '35', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(96, 'How many lines of symmetry does an equilateral triangle have?', '3', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(97, 'How many meters are there in 2500 millimeters?', '                      2.5m', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(98, 'Calculate the perimeter of a rectangle with length 14 cm and width 8 cm.', '                                44 cm', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(99, 'What is the sum of the angles in a triangle?', '180', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19'),
(100, 'How many lines of symmetry does a rectangle have?', '2', 1, '2024-07-25 02:33:19', '2024-07-25 02:33:19');

-- --------------------------------------------------------

--
-- Table structure for table `rejectedparticipant`
--

CREATE TABLE `rejectedparticipant` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `emailAddress` varchar(255) NOT NULL,
  `dob` date NOT NULL,
  `registration_number` varchar(255) NOT NULL,
  `imagePath` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `rejectedparticipant`
--

INSERT INTO `rejectedparticipant` (`id`, `username`, `firstname`, `lastname`, `emailAddress`, `dob`, `registration_number`, `imagePath`, `created_at`, `updated_at`) VALUES
(1, 'jachan', 'Peter', 'Obbo', 'Jamesjone@gmail.com', '1998-08-23', '8899', 'tdbv.png', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `schools`
--

CREATE TABLE `schools` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `district` varchar(255) NOT NULL,
  `registration_number` varchar(255) NOT NULL,
  `representative_email` varchar(255) NOT NULL,
  `representative_name` varchar(255) NOT NULL,
  `validated` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `schools`
--

INSERT INTO `schools` (`id`, `name`, `district`, `registration_number`, `representative_email`, `representative_name`, `validated`, `created_at`, `updated_at`) VALUES
(2, 'Taibah International', 'Entebbe', '8899', 'omallaroy25@gmail.com', 'Omalla Roy', 1, '2024-07-25 03:39:15', '2024-07-25 03:39:15'),
(3, 'ISU', 'Entebbe', '9099', 'offsetroy256@gmail.com', 'Asuman', 1, '2024-07-25 03:40:17', '2024-07-25 03:40:17'),
(4, 'Light Academy S.S', 'Entebbe', '9077', 'mulondoasuman3@gmail.com', 'Mulondo Asuman', 1, '2024-07-25 03:41:17', '2024-07-25 03:41:17');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `email_verified_at`, `password`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'Mulondo Asuman', 'mulondoasuman3@gmail.com', NULL, '$2y$10$S0AICr1x2Nm/kgi7JRLhMe1prq5SQF1HYv68SvKapPvyKhg85l9TG', NULL, '2024-07-25 02:30:46', '2024-07-25 02:30:46');

-- --------------------------------------------------------

--
-- Table structure for table `view_report`
--

CREATE TABLE `view_report` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `challenge_id` bigint(20) UNSIGNED NOT NULL,
  `participant_id` bigint(20) UNSIGNED NOT NULL,
  `question_text` text NOT NULL,
  `answer` text NOT NULL,
  `is_correct` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answers`
--
ALTER TABLE `answers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `attempts`
--
ALTER TABLE `attempts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `challenges`
--
ALTER TABLE `challenges`
  ADD PRIMARY KEY (`challenge_id`);

--
-- Indexes for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `participants`
--
ALTER TABLE `participants`
  ADD PRIMARY KEY (`participant_id`);

--
-- Indexes for table `participant_challenge_attempts`
--
ALTER TABLE `participant_challenge_attempts`
  ADD PRIMARY KEY (`attempt_id`);

--
-- Indexes for table `password_resets`
--
ALTER TABLE `password_resets`
  ADD KEY `password_resets_email_index` (`email`);

--
-- Indexes for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `question_answer_records`
--
ALTER TABLE `question_answer_records`
  ADD PRIMARY KEY (`question_id`);

--
-- Indexes for table `rejectedparticipant`
--
ALTER TABLE `rejectedparticipant`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `schools`
--
ALTER TABLE `schools`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- Indexes for table `view_report`
--
ALTER TABLE `view_report`
  ADD PRIMARY KEY (`id`),
  ADD KEY `view_report_challenge_id_foreign` (`challenge_id`),
  ADD KEY `view_report_participant_id_foreign` (`participant_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `answers`
--
ALTER TABLE `answers`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `attempts`
--
ALTER TABLE `attempts`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `challenges`
--
ALTER TABLE `challenges`
  MODIFY `challenge_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `participants`
--
ALTER TABLE `participants`
  MODIFY `participant_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `participant_challenge_attempts`
--
ALTER TABLE `participant_challenge_attempts`
  MODIFY `attempt_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `question_answer_records`
--
ALTER TABLE `question_answer_records`
  MODIFY `question_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT for table `rejectedparticipant`
--
ALTER TABLE `rejectedparticipant`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `schools`
--
ALTER TABLE `schools`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `view_report`
--
ALTER TABLE `view_report`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `view_report`
--
ALTER TABLE `view_report`
  ADD CONSTRAINT `view_report_challenge_id_foreign` FOREIGN KEY (`challenge_id`) REFERENCES `challenges` (`challenge_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `view_report_participant_id_foreign` FOREIGN KEY (`participant_id`) REFERENCES `participants` (`participant_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
