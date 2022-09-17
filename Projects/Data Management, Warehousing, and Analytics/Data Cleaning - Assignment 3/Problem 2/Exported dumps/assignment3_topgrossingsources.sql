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
-- Table structure for table `topgrossingsources`
--

DROP TABLE IF EXISTS `topgrossingsources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topgrossingsources` (
  `ï»¿RANK` int DEFAULT NULL,
  `SOURCES` text,
  `MOVIES` text,
  `TOTAL GROSS` text,
  `AVERAGE GROSS` text,
  `MARKET SHARE` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topgrossingsources`
--

LOCK TABLES `topgrossingsources` WRITE;
/*!40000 ALTER TABLE `topgrossingsources` DISABLE KEYS */;
INSERT INTO `topgrossingsources` VALUES (1,'Original Screenplay','7,946','$106,375,196,782','$13,387,264',44.74),(2,'Based on Fiction Book/Short Story','2,150','$47,005,613,207','$21,863,076',19.77),(3,'Based on Comic/Graphic Novel','249','$23,369,989,130','$93,855,378',9.83),(4,'Remake','328','$12,832,659,970','$39,123,963',5.4),(5,'Based on Real Life Events','3,225','$11,398,356,297','$3,534,374',4.79),(6,'Based on TV','231','$11,305,006,312','$48,939,421',4.75),(7,'Based on Factual Book/Article','295','$7,443,681,990','$25,232,820',3.13),(8,'Spin-Off','41','$3,833,128,331','$93,490,935',1.61),(9,'Based on Folk Tale/Legend/Fairytale','78','$3,406,118,495','$43,668,186',1.43),(10,'Based on Play','271','$2,111,190,923','$7,790,372',0.89);
/*!40000 ALTER TABLE `topgrossingsources` ENABLE KEYS */;
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
