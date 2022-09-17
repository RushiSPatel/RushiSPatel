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
-- Table structure for table `popularcreativetypes`
--

DROP TABLE IF EXISTS `popularcreativetypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `popularcreativetypes` (
  `ï»¿RANK` int DEFAULT NULL,
  `CREATIVE TYPES` text,
  `MOVIES` text,
  `TOTAL GROSS` text,
  `AVERAGE GROSS` text,
  `MARKET SHARE` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `popularcreativetypes`
--

LOCK TABLES `popularcreativetypes` WRITE;
/*!40000 ALTER TABLE `popularcreativetypes` DISABLE KEYS */;
INSERT INTO `popularcreativetypes` VALUES (1,'Contemporary Fiction','7,442','$96,203,727,036','$12,927,133',40.46),(2,'Kids Fiction','564','$32,035,539,746','$56,800,602',13.47),(3,'Science Fiction','724','$29,922,660,857','$41,329,642',12.59),(4,'Fantasy','759','$21,724,062,575','$28,621,953',9.14),(5,'Super Hero','129','$20,273,157,911','$157,156,263',8.53),(6,'Historical Fiction','1,487','$18,521,260,744','$12,455,454',7.79),(7,'Dramatization','1,175','$15,715,191,699','$13,374,631',6.61),(8,'Factual','2,467','$2,960,327,207','$1,199,970',1.25),(9,'Multiple Creative Types','42','$117,574,526','$2,799,393',0.05);
/*!40000 ALTER TABLE `popularcreativetypes` ENABLE KEYS */;
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
