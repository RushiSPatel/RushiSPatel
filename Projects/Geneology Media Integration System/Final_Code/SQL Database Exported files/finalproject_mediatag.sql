CREATE DATABASE  IF NOT EXISTS `finalproject` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `finalproject`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: finalproject
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `mediatag`
--

DROP TABLE IF EXISTS `mediatag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mediatag` (
  `id` int DEFAULT NULL,
  `fileName` varchar(90) DEFAULT NULL,
  `tag` varchar(90) DEFAULT NULL,
  `dateOfMedia` date DEFAULT NULL,
  KEY `id` (`id`),
  CONSTRAINT `mediatag_ibfk_1` FOREIGN KEY (`id`) REFERENCES `mediaarchive` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mediatag`
--

LOCK TABLES `mediatag` WRITE;
/*!40000 ALTER TABLE `mediatag` DISABLE KEYS */;
INSERT INTO `mediatag` VALUES (1,'D:\\New folder\\rspatel\\docs','Sports','1995-05-06'),(1,'D:\\New folder\\rspatel\\docs','Dance','1995-05-06'),(2,'C:\\New folder\\rspatel\\docs2','Sports','2000-05-00'),(2,'C:\\New folder\\rspatel\\docs2','Travel','2000-05-00'),(2,'C:\\New folder\\rspatel\\docs2','Date','2000-05-00'),(3,'E:\\New folder\\rspatel\\docs3','Sing','2003-05-06');
/*!40000 ALTER TABLE `mediatag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-14  2:16:09
