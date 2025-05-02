-- phpMyAdmin SQL Dump
-- version 5.2.1deb3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 30, 2025 at 04:37 AM
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

-- --------------------------------------------------------

--
-- Table structure for table `Interests`
--

CREATE TABLE `Interests` (
  `interest_id` int(11) NOT NULL,
  `interest_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

-- --------------------------------------------------------

--
-- Table structure for table `Orgs`
--

CREATE TABLE `Orgs` (
  `org_id` int(11) NOT NULL,
  `org_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Orgs`
--

INSERT INTO `Orgs` (`org_id`, `org_name`) VALUES
(5, 'Organization');

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
  `fname` varchar(100) NOT NULL,
  `lname` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Students`
--

INSERT INTO `Students` (`student_id`, `major_id`, `fname`, `lname`) VALUES
(4, NULL, 'User', NULL),
(6, NULL, 'User', NULL),
(9, NULL, 'NEW USER', NULL);

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
(4, NULL, 'student', NULL, NULL),
(5, NULL, 'org', NULL, NULL),
(6, NULL, 'student', NULL, NULL),
(9, NULL, 'student', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `User_Interests`
--

CREATE TABLE `User_Interests` (
  `entity_id` int(11) NOT NULL,
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
(4, '1', 'p1', '2025-04-15 18:08:50', 'student'),
(5, '2', 'p2', '2025-04-15 18:08:50', 'org'),
(6, '3', 'p3', '2025-04-15 18:08:50', 'student'),
(9, 'test@test.test', 'tst', '2025-04-29 23:34:04', 'student');

--
-- Triggers `User_Verify`
--
DELIMITER $$
CREATE TRIGGER `new_user_added` AFTER INSERT ON `User_Verify` FOR EACH ROW BEGIN
 
	INSERT INTO Users (user_id, user_type) VALUES (NEW.user_id, NEW.type)$$
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
  ADD PRIMARY KEY (`entity_id`,`interest_id`),
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
  MODIFY `fos_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Interests`
--
ALTER TABLE `Interests`
  MODIFY `interest_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Majors`
--
ALTER TABLE `Majors`
  MODIFY `major_id` int(11) NOT NULL AUTO_INCREMENT;

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
-- AUTO_INCREMENT for table `Schools`
--
ALTER TABLE `Schools`
  MODIFY `school_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Skills`
--
ALTER TABLE `Skills`
  MODIFY `skill_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `User_Verify`
--
ALTER TABLE `User_Verify`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Follows`
--
ALTER TABLE `Follows`
  ADD CONSTRAINT `follow_ent_fk` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
  ADD CONSTRAINT `follow_followng_fk` FOREIGN KEY (`following_id`) REFERENCES `Users` (`user_id`);

--
-- Constraints for table `Majors`
--
ALTER TABLE `Majors`
  ADD CONSTRAINT `mjr_fos_fk` FOREIGN KEY (`fos_id`) REFERENCES `FoS` (`fos_id`);

--
-- Constraints for table `Orgs`
--
ALTER TABLE `Orgs`
  ADD CONSTRAINT `org_ent_fk` FOREIGN KEY (`org_id`) REFERENCES `Users` (`user_id`);

--
-- Constraints for table `Org_Membership`
--
ALTER TABLE `Org_Membership`
  ADD CONSTRAINT `orgmbr_org_fk` FOREIGN KEY (`org_id`) REFERENCES `Orgs` (`org_id`),
  ADD CONSTRAINT `orgmbr_stdnt_fk` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`);

--
-- Constraints for table `Pictures`
--
ALTER TABLE `Pictures`
  ADD CONSTRAINT `pic_owner_fk` FOREIGN KEY (`owner_id`) REFERENCES `Users` (`user_id`);

--
-- Constraints for table `Posts`
--
ALTER TABLE `Posts`
  ADD CONSTRAINT `post_img_fk` FOREIGN KEY (`img_id`) REFERENCES `Pictures` (`img_id`),
  ADD CONSTRAINT `post_owner_fk` FOREIGN KEY (`owner_id`) REFERENCES `Users` (`user_id`);

--
-- Constraints for table `Post_Tags`
--
ALTER TABLE `Post_Tags`
  ADD CONSTRAINT `tag_intrst_fk` FOREIGN KEY (`interest_id`) REFERENCES `Interests` (`interest_id`),
  ADD CONSTRAINT `tag_post_fk` FOREIGN KEY (`post_id`) REFERENCES `Posts` (`post_id`);

--
-- Constraints for table `Skill_FoS`
--
ALTER TABLE `Skill_FoS`
  ADD CONSTRAINT `sklfos_fos_fk` FOREIGN KEY (`fos_id`) REFERENCES `FoS` (`fos_id`),
  ADD CONSTRAINT `sklfos_skl_fk` FOREIGN KEY (`skill_id`) REFERENCES `Skills` (`skill_id`);

--
-- Constraints for table `Students`
--
ALTER TABLE `Students`
  ADD CONSTRAINT `stdnt_ent_fk` FOREIGN KEY (`student_id`) REFERENCES `Users` (`user_id`),
  ADD CONSTRAINT `stdnt_mjr_fk` FOREIGN KEY (`major_id`) REFERENCES `Majors` (`major_id`);

--
-- Constraints for table `Student_Skills`
--
ALTER TABLE `Student_Skills`
  ADD CONSTRAINT `stdntskl_skl_fk` FOREIGN KEY (`skill_id`) REFERENCES `Skills` (`skill_id`),
  ADD CONSTRAINT `stdntskl_stdnt_fk` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`);

--
-- Constraints for table `Users`
--
ALTER TABLE `Users`
  ADD CONSTRAINT `ent_pfp_fk` FOREIGN KEY (`pfp_id`) REFERENCES `Pictures` (`img_id`),
  ADD CONSTRAINT `ent_school_fk` FOREIGN KEY (`school_id`) REFERENCES `Schools` (`school_id`),
  ADD CONSTRAINT `id_fk` FOREIGN KEY (`user_id`) REFERENCES `User_Verify` (`user_id`);

--
-- Constraints for table `User_Interests`
--
ALTER TABLE `User_Interests`
  ADD CONSTRAINT `entintrst_ent_fk` FOREIGN KEY (`entity_id`) REFERENCES `Users` (`user_id`),
  ADD CONSTRAINT `entintrst_intrst_fk` FOREIGN KEY (`interest_id`) REFERENCES `Interests` (`interest_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
