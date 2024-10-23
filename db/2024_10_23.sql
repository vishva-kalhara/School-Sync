-- MySQL dump 10.13  Distrib 8.0.35, for Win64 (x86_64)
--
-- Host: localhost    Database: school_sync_v1
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
  PRIMARY KEY (`id`)
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classes`
--

DROP TABLE IF EXISTS `classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classes`
--

LOCK TABLES `classes` WRITE;
/*!40000 ALTER TABLE `classes` DISABLE KEYS */;
/*!40000 ALTER TABLE `classes` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `debug`
--

LOCK TABLES `debug` WRITE;
/*!40000 ALTER TABLE `debug` DISABLE KEYS */;
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
  `description` varchar(250) NOT NULL,
  `title` varchar(45) NOT NULL,
  `grades_has_classes_id` int NOT NULL,
  `issued_at` datetime NOT NULL,
  `users_id` varchar(10) NOT NULL,
  `student_id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_decipline_records_grades_has_classes1_idx` (`grades_has_classes_id`),
  KEY `fk_decipline_records_users1_idx` (`users_id`),
  KEY `fk_decipline_records_student1_idx` (`student_id`),
  CONSTRAINT `fk_decipline_records_grades_has_classes1` FOREIGN KEY (`grades_has_classes_id`) REFERENCES `grades_has_classes` (`id`),
  CONSTRAINT `fk_decipline_records_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `fk_decipline_records_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `decipline_records`
--

LOCK TABLES `decipline_records` WRITE;
/*!40000 ALTER TABLE `decipline_records` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genders`
--

LOCK TABLES `genders` WRITE;
/*!40000 ALTER TABLE `genders` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades`
--

LOCK TABLES `grades` WRITE;
/*!40000 ALTER TABLE `grades` DISABLE KEYS */;
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
  `classes_id` int NOT NULL,
  `is_active` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grades_has_classes_classes1_idx` (`classes_id`),
  KEY `fk_grades_has_classes_grades_idx` (`grades_id`),
  CONSTRAINT `fk_grades_has_classes_classes1` FOREIGN KEY (`classes_id`) REFERENCES `classes` (`id`),
  CONSTRAINT `fk_grades_has_classes_grades` FOREIGN KEY (`grades_id`) REFERENCES `grades` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades_has_classes`
--

LOCK TABLES `grades_has_classes` WRITE;
/*!40000 ALTER TABLE `grades_has_classes` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notices`
--

LOCK TABLES `notices` WRITE;
/*!40000 ALTER TABLE `notices` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notices_type`
--

LOCK TABLES `notices_type` WRITE;
/*!40000 ALTER TABLE `notices_type` DISABLE KEYS */;
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
  `value` varchar(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
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
  `grades_has_classes_id` int NOT NULL,
  `created_at` datetime NOT NULL,
  `guardian_1_full_name` varchar(150) NOT NULL,
  `guardian_2_full_name` varchar(150) NOT NULL,
  `mobile1` varchar(10) NOT NULL,
  `mobile_2` varchar(10) DEFAULT NULL,
  `genders_id` int NOT NULL,
  `email` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_grades_has_classes1_idx` (`grades_has_classes_id`),
  KEY `fk_student_genders1_idx` (`genders_id`),
  CONSTRAINT `fk_student_genders1` FOREIGN KEY (`genders_id`) REFERENCES `genders` (`id`),
  CONSTRAINT `fk_student_grades_has_classes1` FOREIGN KEY (`grades_has_classes_id`) REFERENCES `grades_has_classes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
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
  `value` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
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
  `grades_has_classes_id` int DEFAULT NULL,
  `status_id` int NOT NULL,
  `created_at` datetime NOT NULL,
  `user_roles_id` int NOT NULL,
  `service_number` varchar(15) NOT NULL,
  `address` varchar(60) NOT NULL,
  `mobile1` varchar(10) NOT NULL,
  `mobile2` varchar(10) DEFAULT NULL,
  `genders_id` int NOT NULL,
  `sys_username` varchar(45) NOT NULL,
  `sys_password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_users_grades_has_classes1_idx` (`grades_has_classes_id`),
  KEY `fk_users_status1_idx` (`status_id`),
  KEY `fk_users_user_roles1_idx` (`user_roles_id`),
  KEY `fk_users_genders1_idx` (`genders_id`),
  CONSTRAINT `fk_users_genders1` FOREIGN KEY (`genders_id`) REFERENCES `genders` (`id`),
  CONSTRAINT `fk_users_grades_has_classes1` FOREIGN KEY (`grades_has_classes_id`) REFERENCES `grades_has_classes` (`id`),
  CONSTRAINT `fk_users_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  CONSTRAINT `fk_users_user_roles1` FOREIGN KEY (`user_roles_id`) REFERENCES `user_roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitor_details`
--

LOCK TABLES `visitor_details` WRITE;
/*!40000 ALTER TABLE `visitor_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `visitor_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-23 20:28:31
