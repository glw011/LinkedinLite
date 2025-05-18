-- phpMyAdmin SQL Dump
-- version 5.2.1deb3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 12, 2025 at 09:31 PM
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
(1, 'Agriculture'),
(2, 'Architecture & Design'),
(3, 'Arts & Humanities'),
(4, 'Business'),
(5, 'Communication'),
(6, 'Education'),
(7, 'Engineering'),
(8, 'Health & Human Services'),
(9, 'Interdisciplinary'),
(10, 'Mathematics'),
(11, 'Natural Sciences'),
(12, 'Social Sciences'),
(13, 'Technology');

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
(1, "Artificial Intelligence"),
(2, "Machine Learning"),
(3, "Data Science"),
(5, "Robotics"),
(6, "Web Development"),
(7, "Computer Science"),
(8, "Mathematics"),
(9, "Physics"),
(10, "Chemistry"),
(11, "Biology"),
(12, "Psychology"),
(13, "Sociology"),
(14, "History"),
(15, "Political Science"),
(16, "Reading"),
(17, "Cyber Security"),
(18, "Casual"),
(19, "Competitive"),
(20, "Gaming"),
(21, "Community Service"),
(22, "Sports"),
(23, "Music"),
(24, "Art"),
(25, "Photography"),
(26, "Traveling"),
(27, "Cooking"),
(28, "Writing"),
(29, "Fashion"),
(30, "Fitness"),
(31, "Health & Wellness"),
(32, "Social Media"),
(33, "Networking"),
(34, "Public Speaking"),
(35, "Leadership"),
(36, "Entrepreneurship"),
(37, "Marketing"),
(38, "Finance"),
(39, "Investing"),
(40, "Debate"),
(42, "Theater"),
(43, "Dance"),
(44, "Film"),
(45, "Cooking"),
(46, "Gardening"),
(47, "Hiking"),
(48, "Camping"),
(49, "Fishing"),
(50, "Biking"),
(51, "Running"),
(52, "Swimming"),
(53, "Yoga"),
(54, "Meditation"),
(55, "Mindfulness"),
(56, "Self-Improvement"),
(57, "Personal Development"),
(58, "Football"),
(59, "Soccer"),
(60, "Basketball"),
(61, "Baseball"),
(62, "Tennis"),
(63, "Golf"),
(64, "Volleyball"),
(65, "Hockey"),
(66, "Rugby"),
(67, "Cricket"),
(68, "Lacrosse"),
(69, "Track & Field"),
(70, "Wrestling"),
(71, "Martial Arts"),
(72, "Swimming"),
(73, "Diving"),
(74, "Surfing"),
(75, "Skiing"),
(76, "Snowboarding"),
(77, "Skating");
-- --------------------------------------------------------

--
-- Table structure for table `Interest_FoS`
--

CREATE TABLE `Interest_FoS` (
  `interest_id` int(11) NOT NULL,
  `fos_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(1, 'Accounting', NULL),
(2, 'Actuarial Science', NULL),
(3, 'Advertising', NULL),
(4, 'Aerospace Engineering', NULL),
(5, 'African American Studies', NULL),
(6, 'Agricultural Business', NULL),
(7, 'Agricultural Economics', NULL),
(8, 'Agricultural Engineering', NULL),
(9, 'Agricultural Science', NULL),
(10, 'American Studies', NULL),
(11, 'Animal Science', NULL),
(12, 'Anthropology', NULL),
(13, 'Applied Mathematics', NULL),
(14, 'Applied Physics', NULL),
(15, 'Archaeology', NULL),
(16, 'Architecture', NULL),
(17, 'Art History', NULL),
(18, 'Artificial Intelligence', NULL),
(19, 'Asian Studies', NULL),
(20, 'Astronomy', NULL),
(21, 'Astrophysics', NULL),
(22, 'Athletic Training', NULL),
(23, 'Biochemistry', NULL),
(24, 'Bioengineering', NULL),
(25, 'Bioinformatics', NULL),
(26, 'Biological Sciences', NULL),
(27, 'Biomedical Engineering', NULL),
(28, 'Biophysics', NULL),
(29, 'Biotechnology', NULL),
(30, 'Botany', NULL),
(31, 'Broadcast Journalism', NULL),
(32, 'Business Administration', NULL),
(33, 'Business Analytics', NULL),
(34, 'Chemical Engineering', NULL),
(35, 'Chemistry', NULL),
(36, 'Child Development', NULL),
(37, 'Chinese Language and Literature', NULL),
(38, 'Civil Engineering', NULL),
(39, 'Classical Studies', NULL),
(40, 'Cognitive Science', NULL),
(41, 'Communication Disorders', NULL),
(42, 'Communication Studies', NULL),
(43, 'Comparative Literature', NULL),
(44, 'Computational Biology', NULL),
(45, 'Computer Engineering', NULL),
(46, 'Computer Information Systems', NULL),
(47, 'Computer Science', NULL),
(48, 'Construction Management', NULL),
(49, 'Creative Writing', NULL),
(50, 'Criminal Justice', NULL),
(51, 'Criminology', NULL),
(52, 'Culinary Arts', NULL),
(53, 'Cybersecurity', NULL),
(54, 'Dance', NULL),
(55, 'Data Analytics', NULL),
(56, 'Data Science', NULL),
(57, 'Dental Hygiene', NULL),
(58, 'Design', NULL),
(59, 'Digital Media', NULL),
(60, 'Digital Marketing', NULL),
(61, 'Early Childhood Education', NULL),
(62, 'Earth Science', NULL),
(63, 'Ecology', NULL),
(64, 'Economics', NULL),
(65, 'Education', NULL),
(66, 'Electrical Engineering', NULL),
(67, 'Elementary Education', NULL),
(68, 'Emergency Management', NULL),
(69, 'Engineering Management', NULL),
(70, 'English', NULL),
(71, 'English Literature', NULL),
(72, 'Entrepreneurship', NULL),
(73, 'Environmental Engineering', NULL),
(74, 'Environmental Science', NULL),
(75, 'Environmental Studies', NULL),
(76, 'Ethnic Studies', NULL),
(77, 'European Studies', NULL),
(78, 'Fashion Design', NULL),
(79, 'Fashion Merchandising', NULL),
(80, 'Film and Media Studies', NULL),
(81, 'Finance', NULL),
(82, 'Fine Arts', NULL),
(83, 'Fisheries and Wildlife', NULL),
(84, 'Food Science', NULL),
(85, 'Forensic Science', NULL),
(86, 'Forestry', NULL),
(87, 'French', NULL),
(88, 'Game Design', NULL),
(89, 'Gender Studies', NULL),
(90, 'General Studies', NULL),
(91, 'Genetics', NULL),
(92, 'Geography', NULL),
(93, 'Geology', NULL),
(94, 'Geophysics', NULL),
(95, 'German', NULL),
(96, 'Global Studies', NULL),
(97, 'Graphic Design', NULL),
(98, 'Health Administration', NULL),
(99, 'Health Education', NULL),
(100, 'Health Informatics', NULL),
(101, 'Health Sciences', NULL),
(102, 'Hebrew', NULL),
(103, 'Hispanic Studies', NULL),
(104, 'History', NULL),
(105, 'Horticulture', NULL),
(106, 'Hospitality Management', NULL),
(107, 'Human Development', NULL),
(108, 'Human Resource Management', NULL),
(109, 'Human Services', NULL),
(110, 'Industrial Design', NULL),
(111, 'Industrial Engineering', NULL),
(112, 'Information Systems', NULL),
(113, 'Information Technology', NULL),
(114, 'International Business', NULL),
(115, 'International Relations', NULL),
(116, 'Islamic Studies', NULL),
(117, 'Italian', NULL),
(118, 'Japanese', NULL),
(119, 'Jazz Studies', NULL),
(120, 'Jewish Studies', NULL),
(121, 'Journalism', NULL),
(122, 'Kinesiology', NULL),
(123, 'Landscape Architecture', NULL),
(124, 'Latin American Studies', NULL),
(125, 'Law Enforcement', NULL),
(126, 'Legal Studies', NULL),
(127, 'Liberal Arts', NULL),
(128, 'Linguistics', NULL),
(129, 'Management', NULL),
(130, 'Management Information Systems', NULL),
(131, 'Marine Biology', NULL),
(132, 'Marketing', NULL),
(133, 'Materials Science', NULL),
(134, 'Mathematics', NULL),
(135, 'Mechanical Engineering', NULL),
(136, 'Media Studies', NULL),
(137, 'Medical Laboratory Science', NULL),
(138, 'Medical Technology', NULL),
(139, 'Meteorology', NULL),
(140, 'Microbiology', NULL),
(141, 'Middle Eastern Studies', NULL),
(142, 'Military Science', NULL),
(143, 'Molecular Biology', NULL),
(144, 'Music', NULL),
(145, 'Music Education', NULL),
(146, 'Music Performance', NULL),
(147, 'Native American Studies', NULL),
(148, 'Natural Resources', NULL),
(149, 'Neuroscience', NULL),
(150, 'Nuclear Engineering', NULL),
(151, 'Nursing', NULL),
(152, 'Nutrition', NULL),
(153, 'Occupational Therapy', NULL),
(154, 'Oceanography', NULL),
(155, 'Operations Management', NULL),
(156, 'Optometry', NULL),
(157, 'Organizational Leadership', NULL),
(158, 'Paleontology', NULL),
(159, 'Paralegal Studies', NULL),
(160, 'Peace Studies', NULL),
(161, 'Petroleum Engineering', NULL),
(162, 'Pharmacology', NULL),
(163, 'Pharmacy', NULL),
(164, 'Philosophy', NULL),
(165, 'Photography', NULL),
(166, 'Physical Education', NULL),
(167, 'Physical Therapy', NULL),
(168, 'Physics', NULL),
(169, 'Physiology', NULL),
(170, 'Plant Science', NULL),
(171, 'Political Science', NULL),
(172, 'Portuguese', NULL),
(173, 'Pre-Dentistry', NULL),
(174, 'Pre-Law', NULL),
(175, 'Pre-Medicine', NULL),
(176, 'Pre-Pharmacy', NULL),
(177, 'Pre-Veterinary Medicine', NULL),
(178, 'Product Design', NULL),
(179, 'Project Management', NULL),
(180, 'Psychology', NULL),
(181, 'Public Administration', NULL),
(182, 'Public Health', NULL),
(183, 'Public Policy', NULL),
(184, 'Public Relations', NULL),
(185, 'Radiologic Technology', NULL),
(186, 'Real Estate', NULL),
(187, 'Recreation and Leisure Studies', NULL),
(188, 'Religious Studies', NULL),
(189, 'Renewable Energy', NULL),
(190, 'Respiratory Therapy', NULL),
(191, 'Risk Management', NULL),
(192, 'Russian', NULL),
(193, 'Social Science', NULL),
(194, 'Social Work', NULL),
(195, 'Sociology', NULL),
(196, 'Software Engineering', NULL),
(197, 'Spanish', NULL),
(198, 'Special Education', NULL),
(199, 'Speech Pathology', NULL),
(200, 'Statistics', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Orgs`
--

CREATE TABLE `Orgs` (
  `org_id` int(11) NOT NULL,
  `org_name` varchar(255) NOT NULL DEFAULT '"NEW ORG"'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Orgs`
--

INSERT INTO `Orgs` (`org_id`, `org_name`) VALUES
(5, 'Organization'),
(11, '\"NEW ORG\"');

-- --------------------------------------------------------

--
-- Table structure for table `Org_Membership`
--

CREATE TABLE `Org_Membership` (
  `org_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `role` enum('leadership','member','interested') DEFAULT NULL,
  `join_date` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Pending_Invites`
--

CREATE TABLE `Pending_Invites` (
  `student_id` int(11) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `invite_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Pending_Requests`
--

CREATE TABLE `Pending_Requests` (
  `org_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
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

-- --------------------------------------------------------

--
-- Table structure for table `Skill_FoS`
--

CREATE TABLE `Skill_FoS` (
  `skill_id` int(11) NOT NULL,
  `fos_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Students`
--

CREATE TABLE `Students` (
  `student_id` int(11) NOT NULL,
  `major_id` int(11) DEFAULT NULL,
  `fname` varchar(100) NOT NULL DEFAULT '"NEW STUDENT"',
  `lname` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Students`
--

INSERT INTO `Students` (`student_id`, `major_id`, `fname`, `lname`) VALUES
(10, NULL, '\"NEW STUDENT\"', NULL),
(12, 47, 'garrett', NULL),
(13, NULL, '\"NEW STUDENT\"', NULL),
(14, NULL, '\"NEW STUDENT\"', NULL),
(15, NULL, '\"NEW STUDENT\"', NULL),
(16, NULL, '\"NEW STUDENT\"', NULL);

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
  `pfp_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`user_id`, `bio`, `user_type`, `school_id`, `pfp_id`) VALUES
(10, NULL, 'student', NULL, NULL),
(11, NULL, 'org', NULL, NULL),
(12, NULL, 'student', 1, NULL),
(13, NULL, 'student', NULL, NULL),
(14, NULL, 'student', NULL, NULL),
(15, NULL, 'student', NULL, NULL),
(16, NULL, 'student', NULL, NULL);

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
-- Dumping data for table `User_Verify`
--

INSERT INTO `User_Verify` (`user_id`, `email`, `pass_hash`, `join_date`, `type`) VALUES
(10, 'stdnt@test.com', 'hash', '2025-05-02 14:27:24', 'student'),
(11, 'org@test.com', 'hash', '2025-05-02 14:27:24', 'org'),
(12, 'glw011@latech.edu', 'hashstring', '2025-05-02 20:34:49', 'student'),
(13, 'glw011@latech.edu', 'hashstring', '2025-05-02 20:34:52', 'student'),
(14, 'glw011@latech.edu', 'hashstring', '2025-05-02 20:36:54', 'student'),
(15, 'glw011@latech.edu', 'hashstring', '2025-05-02 20:36:57', 'student'),
(16, 'glw011@latech.edu', 'hashstring', '2025-05-02 20:55:15', 'student');

--
-- Triggers `User_Verify`
--
DELIMITER $$
CREATE TRIGGER user_added 
AFTER INSERT ON User_Verify 
FOR EACH ROW 
BEGIN
	INSERT INTO Users (user_id, user_type) VALUES (NEW.user_id, NEW.type);
    
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
  ADD KEY `inv_fk` (`student_id`),
  ADD KEY `inv_fk2` (`org_id`);

--
-- Indexes for table `Pending_Requests`
--
ALTER TABLE `Pending_Requests`
  ADD KEY `req_fk` (`student_id`),
  ADD KEY `req_fk2` (`org_id`);

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
  MODIFY `fos_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `Interests`
--
ALTER TABLE `Interests`
  MODIFY `interest_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Majors`
--
ALTER TABLE `Majors`
  MODIFY `major_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=201;

--
-- AUTO_INCREMENT for table `Pictures`
--
ALTER TABLE `Pictures`
  MODIFY `img_id` int(11) NOT NULL AUTO_INCREMENT;

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
  MODIFY `skill_id` int(11) NOT NULL AUTO_INCREMENT;

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
