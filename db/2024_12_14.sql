-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: school_sync_v1
-- ------------------------------------------------------
-- Server version	8.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `additional_fees`
--

DROP TABLE IF EXISTS `additional_fees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `additional_fees` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(20) NOT NULL,
  `price` double NOT NULL,
  `grades_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_additional_fees_grades1_idx` (`grades_id`),
  CONSTRAINT `fk_additional_fees_grades1` FOREIGN KEY (`grades_id`) REFERENCES `grades` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `additional_fees`
--

LOCK TABLES `additional_fees` WRITE;
/*!40000 ALTER TABLE `additional_fees` DISABLE KEYS */;
/*!40000 ALTER TABLE `additional_fees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `grades_has_classes_id` int NOT NULL,
  `student_id` varchar(10) NOT NULL,
  `created_at` datetime NOT NULL,
  `users_id` varchar(10) NOT NULL,
  `is_active` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_appointments_grades_has_classes1_idx` (`grades_has_classes_id`),
  KEY `fk_appointments_student1_idx` (`student_id`),
  KEY `fk_appointments_users1_idx` (`users_id`),
  CONSTRAINT `fk_appointments_grades_has_classes1` FOREIGN KEY (`grades_has_classes_id`) REFERENCES `grades_has_classes` (`id`),
  CONSTRAINT `fk_appointments_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `fk_appointments_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `makrd_at` datetime NOT NULL,
  `student_id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_attendance_student1_idx` (`student_id`),
  CONSTRAINT `fk_attendance_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (2,'2024-10-30 22:52:02','stu-5');
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `debug`
--

DROP TABLE IF EXISTS `debug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `debug` (
  `id` int NOT NULL AUTO_INCREMENT,
  `message` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `debug`
--

LOCK TABLES `debug` WRITE;
/*!40000 ALTER TABLE `debug` DISABLE KEYS */;
INSERT INTO `debug` VALUES (1,'Connection is working');
/*!40000 ALTER TABLE `debug` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `decipline_records`
--

DROP TABLE IF EXISTS `decipline_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `decipline_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `title` varchar(45) NOT NULL,
  `grades_has_classes_id` int NOT NULL,
  `issued_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `users_id` varchar(10) NOT NULL,
  `student_id` varchar(10) NOT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_decipline_records_grades_has_classes1_idx` (`grades_has_classes_id`),
  KEY `fk_decipline_records_users1_idx` (`users_id`),
  KEY `fk_decipline_records_student1_idx` (`student_id`),
  KEY `fk_decipline_records_status1_idx` (`status_id`),
  CONSTRAINT `fk_decipline_records_grades_has_classes1` FOREIGN KEY (`grades_has_classes_id`) REFERENCES `grades_has_classes` (`id`),
  CONSTRAINT `fk_decipline_records_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  CONSTRAINT `fk_decipline_records_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `fk_decipline_records_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `decipline_records`
--

LOCK TABLES `decipline_records` WRITE;
/*!40000 ALTER TABLE `decipline_records` DISABLE KEYS */;
INSERT INTO `decipline_records` VALUES (1,'The student was repeatedly disruptive during class, causing interruptions and not following the teacher\'s instructions. Despite multiple warnings, the behavior continued, affecting the learning environment for other students.','Disruptive Behavior',3,'2024-10-28 11:09:29','usr-1','stu-15',1),(2,'The student consistently failed to complete and submit homework assignments on time, despite multiple reminders. This behavior has impacted their progress and disrupted class activities.','Incomplete Homework',2,'2024-10-28 11:12:21','usr-2','stu-17',1),(3,'The student was repeatedly disruptive during class, causing interruptions and not following the teacher\'s instructions. Despite multiple warnings, the behavior continued, affecting the learning environment for other students.','Disruptive Behavior',1,'2024-10-28 11:19:46','usr-1','stu-19',1);
/*!40000 ALTER TABLE `decipline_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genders`
--

DROP TABLE IF EXISTS `genders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genders`
--

LOCK TABLES `genders` WRITE;
/*!40000 ALTER TABLE `genders` DISABLE KEYS */;
INSERT INTO `genders` VALUES (1,'Male'),(2,'Female'),(3,'Other');
/*!40000 ALTER TABLE `genders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grades` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades`
--

LOCK TABLES `grades` WRITE;
/*!40000 ALTER TABLE `grades` DISABLE KEYS */;
INSERT INTO `grades` VALUES (1,'1'),(2,'2'),(3,'3'),(4,'4'),(5,'5'),(6,'6'),(7,'7');
/*!40000 ALTER TABLE `grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grades_has_classes`
--

DROP TABLE IF EXISTS `grades_has_classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grades_has_classes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grades_id` int NOT NULL,
  `is_active` tinyint NOT NULL,
  `class` varchar(8) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grades_has_classes_grades_idx` (`grades_id`),
  CONSTRAINT `fk_grades_has_classes_grades` FOREIGN KEY (`grades_id`) REFERENCES `grades` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades_has_classes`
--

LOCK TABLES `grades_has_classes` WRITE;
/*!40000 ALTER TABLE `grades_has_classes` DISABLE KEYS */;
INSERT INTO `grades_has_classes` VALUES (1,2,1,'A'),(2,3,1,'A'),(3,1,1,'B'),(4,2,2,'B'),(5,3,1,'B'),(6,3,1,'C'),(8,4,1,'D'),(11,5,1,'A'),(12,6,1,'A'),(13,6,1,'B'),(14,6,1,'C'),(15,7,1,'A'),(16,7,1,'B'),(17,1,1,'A'),(18,1,1,'C'),(19,1,1,'D');
/*!40000 ALTER TABLE `grades_has_classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notices`
--

DROP TABLE IF EXISTS `notices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notices` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subject` varchar(45) NOT NULL,
  `content` varchar(300) NOT NULL,
  `created_at` datetime NOT NULL,
  `notices_type_id` int NOT NULL,
  `student_id` varchar(10) NOT NULL,
  `users_id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_notices_notices_type1_idx` (`notices_type_id`),
  KEY `fk_notices_student1_idx` (`student_id`),
  KEY `fk_notices_users1_idx` (`users_id`),
  CONSTRAINT `fk_notices_notices_type1` FOREIGN KEY (`notices_type_id`) REFERENCES `notices_type` (`id`),
  CONSTRAINT `fk_notices_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `fk_notices_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notices`
--

LOCK TABLES `notices` WRITE;
/*!40000 ALTER TABLE `notices` DISABLE KEYS */;
INSERT INTO `notices` VALUES (1,'Email Subject','Email Content','2024-12-13 14:16:11',1,'stu-26','usr-2');
/*!40000 ALTER TABLE `notices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notices_type`
--

DROP TABLE IF EXISTS `notices_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notices_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notices_type`
--

LOCK TABLES `notices_type` WRITE;
/*!40000 ALTER TABLE `notices_type` DISABLE KEYS */;
INSERT INTO `notices_type` VALUES (1,'Email');
/*!40000 ALTER TABLE `notices_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` varchar(10) NOT NULL,
  `additional_fees_id` int NOT NULL,
  `paid_amoint` double NOT NULL,
  `paid_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_payments_student1_idx` (`student_id`),
  KEY `fk_payments_additional_fees1_idx` (`additional_fees_id`),
  CONSTRAINT `fk_payments_additional_fees1` FOREIGN KEY (`additional_fees_id`) REFERENCES `additional_fees` (`id`),
  CONSTRAINT `fk_payments_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(9) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'Active'),(2,'Inactive'),(3,'Deleted');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` varchar(10) NOT NULL,
  `full_name` varchar(150) NOT NULL,
  `created_at` datetime NOT NULL,
  `guardian_1_full_name` varchar(150) NOT NULL,
  `guardian_2_full_name` varchar(150) NOT NULL,
  `mobile1` varchar(10) NOT NULL,
  `mobile_2` varchar(10) DEFAULT NULL,
  `genders_id` int NOT NULL,
  `email` varchar(60) NOT NULL,
  `grades_has_classes_id` int NOT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_grades_has_classes1_idx` (`grades_has_classes_id`),
  KEY `fk_student_genders1_idx` (`genders_id`),
  KEY `fk_student_status1_idx` (`status_id`),
  CONSTRAINT `fk_student_genders1` FOREIGN KEY (`genders_id`) REFERENCES `genders` (`id`),
  CONSTRAINT `fk_student_grades_has_classes1` FOREIGN KEY (`grades_has_classes_id`) REFERENCES `grades_has_classes` (`id`),
  CONSTRAINT `fk_student_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('stu-1','Kasun Perera','2024-10-28 09:53:16','Sunil Perera','Nimal Perera','0712345678','0709876543',1,'kasun.perera@example.com',1,1),('stu-10','Rashmi Karunaratne','2024-10-28 09:53:16','Janaka Karunaratne','Anoma Karunaratne','0710234567','0790123456',2,'rashmi.karunaratne@example.com',1,1),('stu-11','Suresh Liyanage','2024-10-28 09:53:16','Bandara Liyanage','Sumana Liyanage','0721345678','0710234567',1,'suresh.liyanage@example.com',2,2),('stu-12','Dilani Hettiarachchi','2024-10-28 09:53:16','Gamini Hettiarachchi','Soma Hettiarachchi','0732456789','0721345678',2,'dilani.hettiarachchi@example.com',3,1),('stu-13','Madushan Ranatunga','2024-10-28 09:53:16','Jagath Ranatunga','Indrani Ranatunga','0743567890','0732456789',1,'madushan.ranatunga@example.com',1,3),('stu-14','Lakmini Rajapaksha','2024-10-28 09:53:16','Ajith Rajapaksha','Swarnalatha Rajapaksha','0754678901','0743567890',2,'lakmini.rajapaksha@example.com',2,1),('stu-15','Nuwan Wijesinghe','2024-10-28 09:53:16','Wimal Wijesinghe','Padma Wijesinghe','0765789012','0754678901',1,'nuwan.wijesinghe@example.com',3,2),('stu-16','Thushari Gunawardena','2024-10-28 09:53:16','Ravi Gunawardena','Geetha Gunawardena','0776890123','0765789012',2,'thushari.gunawardena@example.com',1,1),('stu-17','Samantha De Alwis','2024-10-28 09:53:16','Kusal De Alwis','Ranjani De Alwis','0787901234','0776890123',1,'samantha.dealwis@example.com',2,3),('stu-18','Isuru Rajakaruna','2024-10-28 09:53:16','Sanath Rajakaruna','Nadeeka Rajakaruna','0798012345','0787901234',2,'isuru.rajakaruna@example.com',3,1),('stu-19','Kasuni Amarasinghe','2024-10-28 09:53:16','Indika Amarasinghe','Vimukthi Amarasinghe','0719123456','0798012345',1,'kasuni.amarasinghe@example.com',1,2),('stu-2','Nimali Silva','2024-10-28 09:53:16','Ranjith Silva','Kumari Silva','0723456789','0712345678',2,'nimali.silva@example.com',3,1),('stu-20','Shanika Abeysinghe','2024-10-28 09:53:16','Gayan Abeysinghe','Sujeewa Abeysinghe','0720234567','0719123456',2,'shanika.abeysinghe@example.com',2,1),('stu-21','Chamara Rathnayake','2024-10-28 09:53:16','Priyan Rathnayake','Wasantha Rathnayake','0731345678','0720234567',1,'chamara.rathnayake@example.com',3,1),('stu-22','Kavindi Weerasinghe','2024-10-28 09:53:16','Lalith Weerasinghe','Chandrani Weerasinghe','0742456789','0731345678',2,'kavindi.weerasinghe@example.com',1,3),('stu-23','Kusal Tennakoon','2024-10-28 09:53:16','Kamal Tennakoon','Nirmala Tennakoon','0753567890','0742456789',1,'kusal.tennakoon@example.com',2,2),('stu-24','Maleesha Rathnayake','2024-10-28 09:53:16','Keerthi Rathnayake','Anoma Rathnayake','0764678901','0753567890',2,'maleesha.rathnayake@example.com',3,1),('stu-25','Nishan Peris','2024-10-28 09:53:16','Amal Peris','Kumudu Peris','0775789012','0764678901',1,'nishan.peris@example.com',1,2),('stu-26','Dunali Harindhi','2024-10-29 15:35:12','Pradeep Basnayake','Imesha Basnayake','0785437980','',2,'chamodsanjula55@gmail.com',6,1),('stu-3','Ruwan Fernando','2024-10-28 09:53:16','Kamal Fernando','Chandra Fernando','0734567890','0723456789',1,'ruwan.fernando@example.com',17,2),('stu-4','Anjali De Silva','2024-10-28 09:53:16','Mahesh De Silva','Subha De Silva','0745678901','0734567890',2,'anjali.desilva@example.com',1,1),('stu-5','Chathura Jayasinghe','2024-10-28 09:53:16','Sunil Jayasinghe','Indrani Jayasinghe','0756789012','0745678901',1,'chathura.jayasinghe@example.com',2,3),('stu-6','Sanduni Wickramasinghe','2024-10-28 09:53:16','Ruwan Wickramasinghe','Mangalika Wickramasinghe','0767890123','0756789012',2,'sanduni.wickramasinghe@example.com',3,1),('stu-7','Pradeep Dissanayake','2024-10-28 09:53:16','Nimal Dissanayake','Chithra Dissanayake','0778901234','0767890123',1,'pradeep.dissanayake@example.com',1,2),('stu-8','Tharushi Bandara','2024-10-28 09:53:16','Priyantha Bandara','Hemali Bandara','0789012345','0778901234',2,'tharushi.bandara@example.com',2,1),('stu-9','Lakshan Senanayake','2024-10-28 09:53:16','Sarath Senanayake','Deepika Senanayake','0790123456','0789012345',1,'lakshan.senanayake@example.com',3,3);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'Super Admin'),(2,'Principal'),(3,'Vice Principal'),(4,'Sectional Head'),(5,'Teacher'),(6,'ITU'),(7,'Securty Guard');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(10) NOT NULL,
  `full_name` varchar(150) NOT NULL,
  `status_id` int NOT NULL,
  `created_at` datetime NOT NULL,
  `user_roles_id` int NOT NULL,
  `nic` varchar(15) NOT NULL,
  `address` varchar(60) NOT NULL,
  `mobile1` varchar(10) NOT NULL,
  `mobile2` varchar(10) DEFAULT NULL,
  `genders_id` int NOT NULL,
  `sys_username` varchar(45) NOT NULL,
  `sys_password` varchar(20) NOT NULL,
  `temp_token` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_users_status1_idx` (`status_id`),
  KEY `fk_users_user_roles1_idx` (`user_roles_id`),
  KEY `fk_users_genders1_idx` (`genders_id`),
  CONSTRAINT `fk_users_genders1` FOREIGN KEY (`genders_id`) REFERENCES `genders` (`id`),
  CONSTRAINT `fk_users_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  CONSTRAINT `fk_users_user_roles1` FOREIGN KEY (`user_roles_id`) REFERENCES `user_roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('usr-1','S. A. Wishva Kalhara',1,'2024-10-25 16:29:11',4,'200330010764','Dankotuwa','0766801652','0766801620',1,'wishva','123456789',NULL),('usr-2','Chamod Fernando',1,'2024-10-29 15:33:34',6,'200307101090','Katuneriya, Sri Lanka','0765548967','',1,'Chamod','chamo123',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitor_details`
--

DROP TABLE IF EXISTS `visitor_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitor_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `has_attended` tinyint NOT NULL,
  `student_id` varchar(10) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_visitor_details_student1_idx` (`student_id`),
  CONSTRAINT `fk_visitor_details_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitor_details`
--

LOCK TABLES `visitor_details` WRITE;
/*!40000 ALTER TABLE `visitor_details` DISABLE KEYS */;
INSERT INTO `visitor_details` VALUES (1,0,'stu-9','2024-10-31'),(2,0,'stu-12','2024-12-28'),(3,0,'stu-1','2024-12-21');
/*!40000 ALTER TABLE `visitor_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'school_sync_v1'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-14 13:17:37
