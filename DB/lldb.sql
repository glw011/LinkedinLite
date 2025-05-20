-- phpMyAdmin SQL Dump
-- version 5.2.1deb3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 19, 2025 at 07:25 PM
-- Server version: 10.11.11-MariaDB-0ubuntu0.24.04.2
-- PHP Version: 8.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lldb`
--

-- --------------------------------------------------------

--
-- Table structure for table `Follows`
--

CREATE TABLE `Follows` (
  `user_id` int(11) NOT NULL,
  `following_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `FoS`
--

CREATE TABLE `FoS` (
  `fos_id` int(11) NOT NULL,
  `fos_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `FoS`
--

INSERT INTO `FoS` (`fos_id`, `fos_name`) VALUES
(14, 'Agriculture'),
(15, 'Architecture'),
(16, 'Arts'),
(17, 'Biological Sciences'),
(18, 'Business'),
(19, 'Communications'),
(20, 'Computer Science'),
(21, 'Criminal Justice'),
(22, 'Data Science'),
(23, 'Education'),
(24, 'Engineering'),
(25, 'Environmental Science'),
(26, 'Ethnic and Cultural Studies'),
(27, 'Health Sciences'),
(28, 'History'),
(29, 'Humanities'),
(30, 'International Relations'),
(31, 'Languages and Linguistics'),
(32, 'Law'),
(33, 'Library and Information Science'),
(34, 'Mathematics'),
(35, 'Media Studies'),
(36, 'Philosophy'),
(37, 'Physical Sciences'),
(38, 'Political Science'),
(39, 'Psychology'),
(40, 'Public Administration'),
(41, 'Social Sciences'),
(42, 'Sociology'),
(43, 'Theology and Religious Studies');

-- --------------------------------------------------------

--
-- Table structure for table `Interests`
--

CREATE TABLE `Interests` (
  `interest_id` int(11) NOT NULL,
  `interest_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Interests`
--

INSERT INTO `Interests` (`interest_id`, `interest_name`) VALUES
(26, 'Accounting'),
(132, 'Advertising'),
(51, 'Agriculture'),
(93, 'Algebra'),
(61, 'Animation'),
(77, 'Anthropology'),
(9, 'App Development'),
(95, 'Applied Mathematics'),
(89, 'Arabic'),
(55, 'Architecture'),
(56, 'Art History'),
(7, 'Artificial Intelligence'),
(107, 'Astronomy'),
(106, 'Astrophysics'),
(98, 'Biochemistry'),
(108, 'Biomedical Engineering'),
(16, 'Blockchain'),
(21, 'Business Strategy'),
(92, 'Calculus'),
(112, 'Chemical Engineering'),
(96, 'Chemistry'),
(44, 'Child Development'),
(111, 'Civil Engineering'),
(63, 'Classical Music'),
(47, 'Climate Change'),
(14, 'Cloud Computing'),
(40, 'Cognitive Science'),
(15, 'Computer Vision'),
(36, 'Constitutional Law'),
(67, 'Creative Writing'),
(37, 'Criminal Law'),
(11, 'Cryptography'),
(138, 'Culinary Arts'),
(75, 'Cultural Studies'),
(10, 'Cybersecurity'),
(66, 'Dance'),
(12, 'Data Analysis'),
(13, 'Data Visualization'),
(19, 'DevOps'),
(34, 'Diplomacy'),
(50, 'Ecology'),
(30, 'Economics'),
(43, 'Education Policy'),
(110, 'Electrical Engineering'),
(25, 'Entrepreneurship'),
(46, 'Environmental Conservation'),
(119, 'Epidemiology'),
(71, 'Ethics'),
(133, 'Fashion Design'),
(60, 'Film Production'),
(22, 'Finance'),
(81, 'Foreign Languages'),
(38, 'Forensics'),
(84, 'French'),
(17, 'Game Development'),
(79, 'Gender Studies'),
(100, 'Genetics'),
(49, 'Geology'),
(86, 'German'),
(33, 'Government'),
(59, 'Graphic Design'),
(121, 'Health Informatics'),
(74, 'History'),
(52, 'Horticulture'),
(136, 'Hospitality'),
(29, 'Human Resources'),
(102, 'Immunology'),
(113, 'Industrial Engineering'),
(127, 'Information Science'),
(134, 'Interior Design'),
(23, 'Investing'),
(88, 'Japanese'),
(64, 'Jazz Music'),
(129, 'Journalism'),
(122, 'Kinesiology'),
(35, 'Law'),
(27, 'Leadership'),
(126, 'Library Science'),
(83, 'Linguistics'),
(68, 'Literature'),
(6, 'Machine Learning'),
(87, 'Mandarin'),
(104, 'Marine Biology'),
(24, 'Marketing'),
(114, 'Materials Science'),
(109, 'Mechanical Engineering'),
(128, 'Media Studies'),
(42, 'Mental Health'),
(101, 'Microbiology'),
(99, 'Molecular Biology'),
(62, 'Music Theory'),
(115, 'Nanotechnology'),
(18, 'Network Security'),
(41, 'Neuroscience'),
(116, 'Nursing'),
(120, 'Nutrition'),
(123, 'Occupational Therapy'),
(97, 'Organic Chemistry'),
(57, 'Painting'),
(117, 'Pharmacy'),
(70, 'Philosophy'),
(58, 'Photography'),
(105, 'Physics'),
(69, 'Poetry'),
(31, 'Political Campaigns'),
(28, 'Project Management'),
(39, 'Psychology'),
(118, 'Public Health'),
(32, 'Public Policy'),
(130, 'Public Speaking'),
(94, 'Pure Mathematics'),
(20, 'Quantum Computing'),
(80, 'Race and Ethnicity'),
(139, 'Real Estate'),
(72, 'Religious Studies'),
(48, 'Renewable Energy'),
(90, 'Russian'),
(78, 'Social Justice'),
(131, 'Social Media'),
(76, 'Sociology'),
(85, 'Spanish'),
(45, 'Special Education'),
(124, 'Speech Pathology'),
(135, 'Sports Management'),
(91, 'Statistics'),
(140, 'Supply Chain Management'),
(53, 'Sustainability'),
(65, 'Theater'),
(73, 'Theology'),
(137, 'Tourism'),
(82, 'Translation'),
(54, 'Urban Planning'),
(125, 'Veterinary Science'),
(8, 'Web Development'),
(103, 'Zoology');

-- --------------------------------------------------------

--
-- Table structure for table `Interest_FoS`
--

CREATE TABLE `Interest_FoS` (
  `interest_id` int(11) NOT NULL,
  `fos_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Interest_FoS`
--

INSERT INTO `Interest_FoS` (`interest_id`, `fos_id`) VALUES
(52, 14),
(55, 15),
(134, 15),
(7, 16),
(56, 16),
(57, 16),
(58, 16),
(59, 16),
(61, 16),
(62, 16),
(63, 16),
(64, 16),
(65, 16),
(66, 16),
(67, 16),
(133, 16),
(138, 16),
(99, 17),
(100, 17),
(101, 17),
(103, 17),
(104, 17),
(125, 17),
(21, 18),
(22, 18),
(23, 18),
(24, 18),
(25, 18),
(26, 18),
(28, 18),
(30, 18),
(135, 18),
(136, 18),
(139, 18),
(140, 18),
(129, 19),
(130, 19),
(6, 20),
(8, 20),
(9, 20),
(10, 20),
(15, 20),
(17, 20),
(18, 20),
(19, 20),
(20, 20),
(38, 21),
(12, 22),
(13, 22),
(14, 22),
(16, 22),
(43, 23),
(44, 23),
(45, 23),
(124, 23),
(108, 24),
(109, 24),
(110, 24),
(111, 24),
(112, 24),
(113, 24),
(46, 25),
(47, 25),
(48, 25),
(49, 25),
(50, 25),
(51, 25),
(53, 25),
(54, 25),
(75, 26),
(79, 26),
(80, 26),
(137, 26),
(42, 27),
(102, 27),
(116, 27),
(117, 27),
(118, 27),
(119, 27),
(120, 27),
(121, 27),
(122, 27),
(123, 27),
(74, 28),
(68, 29),
(69, 29),
(34, 30),
(81, 31),
(82, 31),
(83, 31),
(84, 31),
(85, 31),
(86, 31),
(87, 31),
(88, 31),
(89, 31),
(90, 31),
(35, 32),
(36, 32),
(37, 32),
(126, 33),
(127, 33),
(11, 34),
(91, 34),
(92, 34),
(93, 34),
(94, 34),
(95, 34),
(60, 35),
(128, 35),
(131, 35),
(70, 36),
(71, 36),
(96, 37),
(97, 37),
(98, 37),
(105, 37),
(106, 37),
(107, 37),
(114, 37),
(115, 37),
(31, 38),
(32, 38),
(33, 38),
(39, 39),
(40, 39),
(41, 39),
(132, 39),
(77, 41),
(78, 41),
(27, 42),
(29, 42),
(76, 42),
(72, 43),
(73, 43);

-- --------------------------------------------------------

--
-- Table structure for table `Majors`
--

CREATE TABLE `Majors` (
  `major_id` int(11) NOT NULL,
  `major_name` varchar(255) NOT NULL,
  `fos_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Majors`
--

INSERT INTO `Majors` (`major_id`, `major_name`, `fos_id`) VALUES
(201, 'Accounting', 18),
(202, 'Aerospace Engineering', 24),
(203, 'African American Studies', 26),
(204, 'Agricultural Business', 14),
(205, 'Agricultural Economics', 14),
(206, 'Agricultural Engineering', 14),
(207, 'Agricultural Science', 14),
(208, 'Applied Mathematics', 34),
(209, 'Applied Physics', 37),
(210, 'Architecture', 15),
(211, 'Art History', 16),
(212, 'Artificial Intelligence', 16),
(213, 'Asian Studies', 26),
(214, 'Astronomy', 37),
(215, 'Astrophysics', 37),
(216, 'Biochemistry', 37),
(217, 'Bioengineering', 24),
(218, 'Biomedical Engineering', 24),
(219, 'Biophysics', 37),
(220, 'Botany', 17),
(221, 'Broadcast Journalism', 19),
(222, 'Business Administration', 18),
(223, 'Business Analytics', 18),
(224, 'Chemical Engineering', 24),
(225, 'Chemistry', 37),
(226, 'Chinese Language and Literature', 31),
(227, 'Civil Engineering', 24),
(228, 'Cognitive Science', 39),
(229, 'Communication Disorders', 19),
(230, 'Communication Studies', 19),
(231, 'Computational Biology', 17),
(232, 'Computer Engineering', 20),
(233, 'Computer Information Systems', 20),
(234, 'Computer Science', 20),
(235, 'Construction Management', 18),
(236, 'Creative Writing', 16),
(237, 'Criminal Justice', 21),
(238, 'Criminology', 21),
(239, 'Culinary Arts', 16),
(240, 'Cybersecurity', 20),
(241, 'Dance', 16),
(242, 'Data Analytics', 22),
(243, 'Data Science', 22),
(244, 'Digital Media', 35),
(245, 'Digital Marketing', 18),
(246, 'Early Childhood Education', 23),
(247, 'Earth Science', 16),
(248, 'Ecology', 25),
(249, 'Education', 23),
(250, 'Electrical Engineering', 24),
(251, 'Elementary Education', 23),
(252, 'Emergency Management', 18),
(253, 'Engineering Management', 18),
(254, 'Entrepreneurship', 18),
(255, 'Environmental Engineering', 24),
(256, 'Environmental Science', 25),
(257, 'Environmental Studies', 25),
(258, 'Ethnic Studies', 26),
(259, 'Film and Media Studies', 35),
(260, 'Finance', 18),
(261, 'Fine Arts', 16),
(262, 'Forensic Science', 21),
(263, 'French', 31),
(264, 'Gender Studies', 26),
(265, 'General Studies', 29),
(266, 'Genetics', 17),
(267, 'Geology', 25),
(268, 'Geophysics', 37),
(269, 'German', 31),
(270, 'Global Studies', 30),
(271, 'Health Administration', 27),
(272, 'Health Education', 23),
(273, 'Health Informatics', 27),
(274, 'Health Sciences', 27),
(275, 'Hebrew', 31),
(276, 'History', 28),
(277, 'Horticulture', 14),
(278, 'Hospitality Management', 18),
(279, 'Human Resource Management', 18),
(280, 'Human Services', 41),
(281, 'Industrial Engineering', 24),
(282, 'Information Technology', 20),
(283, 'International Business', 18),
(284, 'International Relations', 30),
(285, 'Islamic Studies', 43),
(286, 'Italian', 31),
(287, 'Japanese', 31),
(288, 'Jazz Studies', 16),
(289, 'Jewish Studies', 43),
(290, 'Journalism', 19),
(291, 'Landscape Architecture', 15),
(292, 'Latin American Studies', 26),
(293, 'Law Enforcement', 21),
(294, 'Legal Studies', 32),
(295, 'Liberal Arts', 16),
(296, 'Linguistics', 31),
(297, 'Management', 18),
(298, 'Management Information Systems', 18),
(299, 'Marine Biology', 17),
(300, 'Marketing', 18),
(301, 'Mathematics', 34),
(302, 'Mechanical Engineering', 24),
(303, 'Media Studies', 35),
(304, 'Medical Laboratory Science', 27),
(305, 'Medical Technology', 27),
(306, 'Meteorology', 37),
(307, 'Microbiology', 17),
(308, 'Molecular Biology', 17),
(309, 'Music', 16),
(310, 'Music Education', 16),
(311, 'Music Performance', 16),
(312, 'Neuroscience', 39),
(313, 'Nuclear Engineering', 24),
(314, 'Nursing', 27),
(315, 'Nutrition', 27),
(316, 'Occupational Therapy', 27),
(317, 'Oceanography', 25),
(318, 'Operations Management', 18),
(319, 'Paralegal Studies', 32),
(320, 'Petroleum Engineering', 24),
(321, 'Pharmacy', 27),
(322, 'Philosophy', 36),
(323, 'Physical Education', 23),
(324, 'Physical Therapy', 27),
(325, 'Physics', 37),
(326, 'Plant Science', 14),
(327, 'Political Science', 38),
(328, 'Portuguese', 31),
(329, 'Pre-Law', 32),
(330, 'Pre-Pharmacy', 27),
(331, 'Project Management', 18),
(332, 'Psychology', 39),
(333, 'Public Administration', 40),
(334, 'Public Health', 27),
(335, 'Public Policy', 38),
(336, 'Public Relations', 19),
(337, 'Real Estate', 18),
(338, 'Religious Studies', 43),
(339, 'Respiratory Therapy', 27),
(340, 'Risk Management', 18),
(341, 'Russian', 31),
(342, 'Social Science', 41),
(343, 'Social Work', 41),
(344, 'Sociology', 42),
(345, 'Software Engineering', 20),
(346, 'Spanish', 31),
(347, 'Special Education', 23),
(348, 'Statistics', 34);

-- --------------------------------------------------------

--
-- Table structure for table `Orgs`
--

CREATE TABLE `Orgs` (
  `org_id` int(11) NOT NULL,
  `org_name` varchar(255) NOT NULL DEFAULT 'NEW ORG'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Org_Membership`
--

CREATE TABLE `Org_Membership` (
  `org_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `role` varchar(255) DEFAULT 'Member',
  `join_date` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Pending_Invites`
--

CREATE TABLE `Pending_Invites` (
  `student_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `invite_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Pending_Requests`
--

CREATE TABLE `Pending_Requests` (
  `org_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `request_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Pictures`
--

CREATE TABLE `Pictures` (
  `img_id` int(11) NOT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `upload_date` datetime DEFAULT current_timestamp(),
  `img_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Pictures`
--

INSERT INTO `Pictures` (`img_id`, `owner_id`, `upload_date`, `img_url`) VALUES
(6, 0, '2025-05-19 13:50:11', '/Disk/0_1747680611063.png'),
(7, 0, '2025-05-19 13:50:11', '/Disk/0_1747680611465.png');

-- --------------------------------------------------------

--
-- Table structure for table `Posts`
--

CREATE TABLE `Posts` (
  `post_id` int(11) NOT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `post_date` datetime DEFAULT current_timestamp(),
  `img_id` int(11) DEFAULT NULL,
  `content` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Post_Comments`
--

CREATE TABLE `Post_Comments` (
  `comment_id` int(11) NOT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `post_id` int(11) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `comment_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Post_Tags`
--

CREATE TABLE `Post_Tags` (
  `post_id` int(11) NOT NULL,
  `interest_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Schools`
--

CREATE TABLE `Schools` (
  `school_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `country` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `state` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Schools`
--

INSERT INTO `Schools` (`school_id`, `name`, `country`, `city`, `state`) VALUES
(1, 'Louisiana Tech University', 'United States', 'Ruston', 'Louisiana'),
(2, 'University of Alabama', 'United States', 'Tuscaloosa', 'Alabama'),
(3, 'University of Alaska Anchorage', 'United States', 'Anchorage', 'Alaska'),
(4, 'Arizona State University', 'United States', 'Tempe', 'Arizona'),
(5, 'University of Arkansas', 'United States', 'Fayetteville', 'Arkansas'),
(6, 'University of California, Los Angeles', 'United States', 'Los Angeles', 'California'),
(7, 'University of Colorado Boulder', 'United States', 'Boulder', 'Colorado'),
(8, 'Yale University', 'United States', 'New Haven', 'Connecticut'),
(9, 'University of Delaware', 'United States', 'Newark', 'Delaware'),
(10, 'University of Florida', 'United States', 'Gainesville', 'Florida'),
(11, 'University of Georgia', 'United States', 'Athens', 'Georgia'),
(12, 'University of Hawaii at Manoa', 'United States', 'Honolulu', 'Hawaii'),
(13, 'University of Idaho', 'United States', 'Moscow', 'Idaho'),
(14, 'University of Chicago', 'United States', 'Chicago', 'Illinois'),
(15, 'Purdue University', 'United States', 'West Lafayette', 'Indiana'),
(16, 'University of Iowa', 'United States', 'Iowa City', 'Iowa'),
(17, 'University of Kansas', 'United States', 'Lawrence', 'Kansas'),
(18, 'University of Kentucky', 'United States', 'Lexington', 'Kentucky'),
(19, 'Louisiana State University', 'United States', 'Baton Rouge', 'Louisiana'),
(20, 'Bowdoin College', 'United States', 'Brunswick', 'Maine'),
(21, 'University of Maryland, College Park', 'United States', 'College Park', 'Maryland'),
(22, 'Harvard University', 'United States', 'Cambridge', 'Massachusetts'),
(23, 'University of Michigan', 'United States', 'Ann Arbor', 'Michigan'),
(24, 'University of Minnesota', 'United States', 'Minneapolis', 'Minnesota'),
(25, 'University of Mississippi', 'United States', 'Oxford', 'Mississippi'),
(26, 'Washington University in St. Louis', 'United States', 'St. Louis', 'Missouri'),
(27, 'Montana State University', 'United States', 'Bozeman', 'Montana'),
(28, 'University of Nebraska-Lincoln', 'United States', 'Lincoln', 'Nebraska'),
(29, 'University of Nevada, Reno', 'United States', 'Reno', 'Nevada'),
(30, 'Dartmouth College', 'United States', 'Hanover', 'New Hampshire'),
(31, 'Princeton University', 'United States', 'Princeton', 'New Jersey'),
(32, 'University of New Mexico', 'United States', 'Albuquerque', 'New Mexico'),
(33, 'New York University', 'United States', 'New York', 'New York'),
(34, 'Duke University', 'United States', 'Durham', 'North Carolina'),
(35, 'University of North Dakota', 'United States', 'Grand Forks', 'North Dakota'),
(36, 'Ohio State University', 'United States', 'Columbus', 'Ohio'),
(37, 'University of Oklahoma', 'United States', 'Norman', 'Oklahoma'),
(38, 'Oregon State University', 'United States', 'Corvallis', 'Oregon'),
(39, 'University of Pennsylvania', 'United States', 'Philadelphia', 'Pennsylvania'),
(40, 'Brown University', 'United States', 'Providence', 'Rhode Island'),
(41, 'University of South Carolina', 'United States', 'Columbia', 'South Carolina'),
(42, 'South Dakota State University', 'United States', 'Brookings', 'South Dakota'),
(43, 'Vanderbilt University', 'United States', 'Nashville', 'Tennessee'),
(44, 'University of Texas at Austin', 'United States', 'Austin', 'Texas'),
(45, 'University of Utah', 'United States', 'Salt Lake City', 'Utah'),
(46, 'University of Vermont', 'United States', 'Burlington', 'Vermont'),
(47, 'University of Virginia', 'United States', 'Charlottesville', 'Virginia'),
(48, 'University of Washington', 'United States', 'Seattle', 'Washington'),
(49, 'West Virginia University', 'United States', 'Morgantown', 'West Virginia'),
(50, 'University of Wisconsin-Madison', 'United States', 'Madison', 'Wisconsin'),
(51, 'University of Wyoming', 'United States', 'Laramie', 'Wyoming');

-- --------------------------------------------------------

--
-- Table structure for table `Skills`
--

CREATE TABLE `Skills` (
  `skill_id` int(11) NOT NULL,
  `skill_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Skills`
--

INSERT INTO `Skills` (`skill_id`, `skill_name`) VALUES
(38, '3D Modeling'),
(43, 'Academic Writing'),
(25, 'Agile Development'),
(89, 'Art History Analysis'),
(10, 'Bash Scripting'),
(64, 'Behavioral Observation'),
(49, 'Bioinformatics'),
(3, 'C++'),
(47, 'Chemical Analysis'),
(53, 'Circuit Analysis'),
(67, 'Clinical Procedures'),
(19, 'Cloud Computing'),
(30, 'Communication'),
(18, 'Computer Vision'),
(75, 'Conflict Resolution'),
(42, 'Creative Writing'),
(31, 'Critical Thinking'),
(5, 'CSS'),
(80, 'Cultural Competency'),
(63, 'Curriculum Development'),
(97, 'Customer Relationship Management'),
(60, 'Customer Service'),
(20, 'Cybersecurity'),
(84, 'Dance Choreography'),
(11, 'Data Analysis'),
(12, 'Data Cleaning'),
(13, 'Data Visualization'),
(74, 'Debate'),
(16, 'Deep Learning'),
(41, 'Digital Illustration'),
(35, 'Editing'),
(68, 'EHR Management'),
(56, 'Environmental Assessment'),
(70, 'Epidemiological Analysis'),
(91, 'Ethical Reasoning'),
(58, 'Event Planning'),
(65, 'First Aid'),
(99, 'Food Preparation'),
(76, 'Foreign Language Proficiency'),
(59, 'Fundraising'),
(57, 'GIS Mapping'),
(36, 'Graphic Design'),
(66, 'Healthcare Documentation'),
(98, 'Hospitality Operations'),
(4, 'HTML'),
(82, 'Instrumental Skills'),
(79, 'Intercultural Communication'),
(2, 'Java'),
(6, 'JavaScript'),
(45, 'Laboratory Techniques'),
(28, 'Leadership'),
(73, 'Legal Research'),
(62, 'Lesson Planning'),
(78, 'Linguistic Analysis'),
(23, 'Linux'),
(15, 'Machine Learning'),
(95, 'Marketing Analytics'),
(50, 'Mathematical Proofs'),
(9, 'MATLAB'),
(52, 'Mechanical Design'),
(46, 'Microscopy'),
(81, 'Music Performance'),
(17, 'Natural Language Processing'),
(21, 'Network Configuration'),
(51, 'Numerical Methods'),
(22, 'Operating Systems'),
(88, 'Painting'),
(48, 'PCR'),
(69, 'Pharmacology'),
(92, 'Philosophical Argumentation'),
(40, 'Photography'),
(72, 'Policy Writing'),
(71, 'Political Analysis'),
(32, 'Problem Solving'),
(26, 'Project Management'),
(33, 'Public Speaking'),
(1, 'Python'),
(8, 'R Programming'),
(90, 'Religious Studies Interpretation'),
(44, 'Research'),
(96, 'Sales Strategy'),
(87, 'Sculpting'),
(83, 'Singing'),
(94, 'Social Media Management'),
(7, 'SQL'),
(85, 'Stage Management'),
(55, 'Statics'),
(14, 'Statistical Modeling'),
(100, 'Supply Chain Management'),
(93, 'Survey Design'),
(29, 'Team Collaboration'),
(86, 'Theatrical Production'),
(54, 'Thermodynamics'),
(27, 'Time Management'),
(77, 'Translation'),
(61, 'Tutoring'),
(39, 'UI/UX Design'),
(24, 'Version Control (Git)'),
(37, 'Video Editing'),
(34, 'Writing');

-- --------------------------------------------------------

--
-- Table structure for table `Skill_FoS`
--

CREATE TABLE `Skill_FoS` (
  `skill_id` int(11) NOT NULL,
  `fos_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Skill_FoS`
--

INSERT INTO `Skill_FoS` (`skill_id`, `fos_id`) VALUES
(1, 20),
(2, 20),
(3, 20),
(4, 20),
(5, 20),
(6, 20),
(7, 20),
(8, 34),
(9, 34),
(10, 20),
(11, 22),
(12, 22),
(13, 22),
(14, 34),
(15, 22),
(16, 22),
(17, 31),
(18, 20),
(19, 22),
(20, 20),
(21, 20),
(22, 20),
(23, 20),
(24, 20),
(25, 18),
(26, 18),
(27, 18),
(28, 19),
(29, 19),
(30, 19),
(31, 39),
(32, 24),
(33, 19),
(34, 29),
(35, 35),
(36, 16),
(37, 35),
(38, 24),
(39, 20),
(40, 16),
(41, 16),
(42, 16),
(43, 29),
(44, 17),
(45, 17),
(46, 17),
(47, 37),
(48, 17),
(49, 17),
(50, 34),
(51, 34),
(52, 24),
(53, 24),
(54, 24),
(55, 24),
(56, 25),
(57, 22),
(58, 18),
(59, 18),
(60, 18),
(61, 23),
(62, 23),
(63, 23),
(64, 23),
(65, 27),
(66, 27),
(67, 27),
(68, 18),
(68, 27),
(69, 27),
(70, 27),
(71, 38),
(72, 38),
(73, 32),
(74, 32),
(75, 19),
(76, 31),
(77, 31),
(78, 31),
(79, 19),
(80, 26),
(81, 16),
(82, 16),
(83, 16),
(84, 16),
(85, 18),
(86, 16),
(87, 16),
(88, 16),
(89, 16),
(90, 43),
(91, 36),
(92, 36),
(93, 41),
(94, 18),
(95, 18),
(96, 18),
(97, 18),
(98, 18),
(99, 14),
(100, 18);

-- --------------------------------------------------------

--
-- Table structure for table `Students`
--

CREATE TABLE `Students` (
  `student_id` int(11) NOT NULL,
  `major_id` int(11) DEFAULT NULL,
  `fname` varchar(100) NOT NULL DEFAULT 'NEW STUDENT',
  `lname` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Student_Skills`
--

CREATE TABLE `Student_Skills` (
  `student_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `user_id` int(11) NOT NULL,
  `bio` text DEFAULT NULL,
  `user_type` enum('student','org') NOT NULL,
  `school_id` int(11) DEFAULT NULL,
  `pfp_id` int(11) DEFAULT 6
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `User_Banners`
--

CREATE TABLE `User_Banners` (
  `user_id` int(11) NOT NULL,
  `img_id` int(11) NOT NULL DEFAULT 7
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `User_Interests`
--

CREATE TABLE `User_Interests` (
  `user_id` int(11) NOT NULL,
  `interest_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `User_Verify`
--

CREATE TABLE `User_Verify` (
  `user_id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `pass_hash` varchar(255) NOT NULL,
  `join_date` datetime DEFAULT current_timestamp(),
  `type` enum('student','org') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Triggers `User_Verify`
--
DELIMITER $$
CREATE TRIGGER new_user_added 
AFTER INSERT ON User_Verify 
FOR EACH ROW 
BEGIN
	INSERT INTO Users (user_id, user_type) VALUES (NEW.user_id, NEW.type);
    INSERT INTO User_Banners (user_id) VALUES (NEW.user_id);
    
	IF NEW.type = 'student' THEN
    	INSERT INTO Students (student_id) VALUES (NEW.user_id);
    ELSEIF NEW.type = 'org' THEN
    	INSERT INTO Orgs (org_id) VALUES (NEW.user_id);
    END IF;
  
END $$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Follows`
--
ALTER TABLE `Follows`
  ADD PRIMARY KEY (`user_id`,`following_id`),
  ADD KEY `follow_followng_fk` (`following_id`);

--
-- Indexes for table `FoS`
--
ALTER TABLE `FoS`
  ADD PRIMARY KEY (`fos_id`),
  ADD UNIQUE KEY `fos_name` (`fos_name`);

--
-- Indexes for table `Interests`
--
ALTER TABLE `Interests`
  ADD PRIMARY KEY (`interest_id`),
  ADD UNIQUE KEY `interest_name` (`interest_name`);

--
-- Indexes for table `Interest_FoS`
--
ALTER TABLE `Interest_FoS`
  ADD PRIMARY KEY (`fos_id`,`interest_id`);

--
-- Indexes for table `Majors`
--
ALTER TABLE `Majors`
  ADD PRIMARY KEY (`major_id`) USING BTREE,
  ADD KEY `mjr_fos_fk` (`fos_id`);

--
-- Indexes for table `Orgs`
--
ALTER TABLE `Orgs`
  ADD PRIMARY KEY (`org_id`);

--
-- Indexes for table `Org_Membership`
--
ALTER TABLE `Org_Membership`
  ADD PRIMARY KEY (`org_id`,`student_id`),
  ADD KEY `orgmbr_stdnt_fk` (`student_id`);

--
-- Indexes for table `Pending_Invites`
--
ALTER TABLE `Pending_Invites`
  ADD PRIMARY KEY (`student_id`,`org_id`),
  ADD KEY `inv_fk2` (`org_id`);

--
-- Indexes for table `Pending_Requests`
--
ALTER TABLE `Pending_Requests`
  ADD PRIMARY KEY (`org_id`,`student_id`),
  ADD KEY `req_fk` (`student_id`);

--
-- Indexes for table `Pictures`
--
ALTER TABLE `Pictures`
  ADD PRIMARY KEY (`img_id`),
  ADD KEY `pic_owner_fk` (`owner_id`);

--
-- Indexes for table `Posts`
--
ALTER TABLE `Posts`
  ADD PRIMARY KEY (`post_id`),
  ADD KEY `post_owner_fk` (`owner_id`),
  ADD KEY `post_img_fk` (`img_id`);

--
-- Indexes for table `Post_Comments`
--
ALTER TABLE `Post_Comments`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `comment_owner_fk` (`owner_id`),
  ADD KEY `comment_parent_fk` (`post_id`);

--
-- Indexes for table `Post_Tags`
--
ALTER TABLE `Post_Tags`
  ADD PRIMARY KEY (`post_id`,`interest_id`),
  ADD KEY `tag_intrst_fk` (`interest_id`);

--
-- Indexes for table `Schools`
--
ALTER TABLE `Schools`
  ADD PRIMARY KEY (`school_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `Skills`
--
ALTER TABLE `Skills`
  ADD PRIMARY KEY (`skill_id`),
  ADD UNIQUE KEY `skill_name` (`skill_name`);

--
-- Indexes for table `Skill_FoS`
--
ALTER TABLE `Skill_FoS`
  ADD PRIMARY KEY (`skill_id`,`fos_id`),
  ADD KEY `sklfos_fos_fk` (`fos_id`);

--
-- Indexes for table `Students`
--
ALTER TABLE `Students`
  ADD PRIMARY KEY (`student_id`),
  ADD KEY `stdnt_mjr_fk` (`major_id`);

--
-- Indexes for table `Student_Skills`
--
ALTER TABLE `Student_Skills`
  ADD PRIMARY KEY (`skill_id`,`student_id`),
  ADD KEY `stdntskl_stdnt_fk` (`student_id`);

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `ent_pfp_fk` (`pfp_id`),
  ADD KEY `ent_school_fk` (`school_id`);

--
-- Indexes for table `User_Banners`
--
ALTER TABLE `User_Banners`
  ADD PRIMARY KEY (`user_id`,`img_id`),
  ADD KEY `banner_img_fk` (`img_id`);

--
-- Indexes for table `User_Interests`
--
ALTER TABLE `User_Interests`
  ADD PRIMARY KEY (`user_id`,`interest_id`),
  ADD KEY `entintrst_intrst_fk` (`interest_id`);

--
-- Indexes for table `User_Verify`
--
ALTER TABLE `User_Verify`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `FoS`
--
ALTER TABLE `FoS`
  MODIFY `fos_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `Interests`
--
ALTER TABLE `Interests`
  MODIFY `interest_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=141;

--
-- AUTO_INCREMENT for table `Majors`
--
ALTER TABLE `Majors`
  MODIFY `major_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=349;

--
-- AUTO_INCREMENT for table `Pictures`
--
ALTER TABLE `Pictures`
  MODIFY `img_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `Posts`
--
ALTER TABLE `Posts`
  MODIFY `post_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Post_Comments`
--
ALTER TABLE `Post_Comments`
  MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Schools`
--
ALTER TABLE `Schools`
  MODIFY `school_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `Skills`
--
ALTER TABLE `Skills`
  MODIFY `skill_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT for table `User_Verify`
--
ALTER TABLE `User_Verify`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Pending_Invites`
--
ALTER TABLE `Pending_Invites`
  ADD CONSTRAINT `inv_fk` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`),
  ADD CONSTRAINT `inv_fk2` FOREIGN KEY (`org_id`) REFERENCES `Orgs` (`org_id`);

--
-- Constraints for table `Pending_Requests`
--
ALTER TABLE `Pending_Requests`
  ADD CONSTRAINT `req_fk` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`),
  ADD CONSTRAINT `req_fk2` FOREIGN KEY (`org_id`) REFERENCES `Orgs` (`org_id`);

--
-- Constraints for table `Post_Comments`
--
ALTER TABLE `Post_Comments`
  ADD CONSTRAINT `comment_owner_fk` FOREIGN KEY (`owner_id`) REFERENCES `Users` (`user_id`),
  ADD CONSTRAINT `comment_parent_fk` FOREIGN KEY (`post_id`) REFERENCES `Posts` (`post_id`);

--
-- Constraints for table `User_Banners`
--
ALTER TABLE `User_Banners`
  ADD CONSTRAINT `banner_img_fk` FOREIGN KEY (`img_id`) REFERENCES `Pictures` (`img_id`),
  ADD CONSTRAINT `banner_usr_fk` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
