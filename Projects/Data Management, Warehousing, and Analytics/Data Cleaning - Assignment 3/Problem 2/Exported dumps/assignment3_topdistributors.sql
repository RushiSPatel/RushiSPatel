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
-- Table structure for table `topdistributors`
--

DROP TABLE IF EXISTS `topdistributors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topdistributors` (
  `ï»¿RANK` int DEFAULT NULL,
  `DISTRIBUTORS` text,
  `MOVIES` int DEFAULT NULL,
  `TOTAL GROSS` text,
  `AVERAGE GROSS` text,
  `MARKET SHARE` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topdistributors`
--

LOCK TABLES `topdistributors` WRITE;
/*!40000 ALTER TABLE `topdistributors` DISABLE KEYS */;
INSERT INTO `topdistributors` VALUES (1,'Walt Disney',588,'$40,472,424,278','$68,830,654',17.02),(2,'Warner Bros.',824,'$36,269,425,479','$44,016,293',15.25),(3,'Sony Pictures',747,'$29,113,002,302','$38,973,229',12.24),(4,'Universal',535,'$28,089,932,569','$52,504,547',11.81),(5,'20th Century Fox',525,'$25,857,839,756','$49,253,028',10.88),(6,'Paramount Pictures',493,'$24,361,425,304','$49,414,656',10.25),(7,'Lionsgate',426,'$9,631,837,781','$22,609,948',4.05),(8,'New Line',209,'$6,195,268,024','$29,642,431',2.61),(9,'Dreamworks SKG',77,'$4,278,649,271','$55,566,874',1.8),(10,'Miramax',385,'$3,836,019,208','$9,963,686',1.61);
/*!40000 ALTER TABLE `topdistributors` ENABLE KEYS */;
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
