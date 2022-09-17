-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: assignment3
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `topgenres`
--

DROP TABLE IF EXISTS `topgenres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topgenres` (
  `ï»¿RANK` int DEFAULT NULL,
  `GENRES` text,
  `MOVIES` text,
  `TOTAL GROSS` text,
  `AVERAGE GROSS` text,
  `MARKET SHARE` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topgenres`
--

LOCK TABLES `topgenres` WRITE;
/*!40000 ALTER TABLE `topgenres` DISABLE KEYS */;
INSERT INTO `topgenres` VALUES (1,'Adventure','1,102','$64,529,536,530','$58,556,748',27.14),(2,'Action','1,098','$49,339,974,493','$44,936,224',20.75),(3,'Drama','5,479','$35,586,177,269','$6,495,013',14.97),(4,'Comedy','2,418','$33,687,992,318','$13,932,172',14.17),(5,'Thriller/Suspense','1,186','$19,810,201,102','$16,703,374',8.33),(6,'Horror','716','$13,430,378,699','$18,757,512',5.65),(7,'Romantic Comedy','630','$10,480,124,374','$16,635,118',4.41),(8,'Musical','201','$4,293,988,317','$21,363,126',1.81),(9,'Documentary','2,415','$2,519,513,142','$1,043,277',1.06),(10,'Black Comedy','213','$2,185,433,323','$10,260,250',0.92);
/*!40000 ALTER TABLE `topgenres` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 20:31:12
