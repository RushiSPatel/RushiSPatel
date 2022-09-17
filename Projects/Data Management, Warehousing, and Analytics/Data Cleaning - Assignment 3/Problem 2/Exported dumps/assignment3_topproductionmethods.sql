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
-- Table structure for table `topproductionmethods`
--

DROP TABLE IF EXISTS `topproductionmethods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topproductionmethods` (
  `ï»¿RANK` int DEFAULT NULL,
  `PRODUCTION METHODS` text,
  `MOVIES` text,
  `TOTAL GROSS` text,
  `AVERAGE GROSS` text,
  `MARKET SHARE` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topproductionmethods`
--

LOCK TABLES `topproductionmethods` WRITE;
/*!40000 ALTER TABLE `topproductionmethods` DISABLE KEYS */;
INSERT INTO `topproductionmethods` VALUES (1,'Live Action','14,613','$179,637,201,848','$12,292,972',75.56),(2,'Animation/Live Action','264','$30,346,622,254','$114,949,327',12.76),(3,'Digital Animation','365','$23,920,180,508','$65,534,741',10.06),(4,'Hand Animation','164','$2,960,497,487','$18,051,814',1.25),(5,'Stop-Motion Animation','37','$676,490,120','$18,283,517',0.28),(6,'Multiple Production Methods','26','$43,728,300','$1,681,858',0.02),(7,'Rotoscoping','4','$8,468,385','$2,117,096',0);
/*!40000 ALTER TABLE `topproductionmethods` ENABLE KEYS */;
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
