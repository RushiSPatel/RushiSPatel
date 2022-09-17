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
-- Table structure for table `highestgrossers`
--

DROP TABLE IF EXISTS `highestgrossers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `highestgrossers` (
  `ï»¿YEAR` int DEFAULT NULL,
  `MOVIE` text,
  `GENRE` text,
  `MPAA RATING` text,
  `DISTRIBUTOR` text,
  `TOTAL FOR YEAR` text,
  `TOTAL IN 2019 DOLLARS` text,
  `TICKETS SOLD` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `highestgrossers`
--

LOCK TABLES `highestgrossers` WRITE;
/*!40000 ALTER TABLE `highestgrossers` DISABLE KEYS */;
INSERT INTO `highestgrossers` VALUES (1995,'Batman Forever','Drama','PG-13','Warner Bros.','$184,031,112','$387,522,978','4,23,06,002'),(1996,'Independence Day','Adventure','PG-13','20th Century Fox','$306,169,255','$634,504,608','6,92,69,062'),(1997,'Men in Black','Adventure','PG-13','Sony Pictures','$250,650,052','$500,207,943','5,46,07,854'),(1998,'Titanic','Adventure','PG-13','Paramount Pictures','$443,319,081','$865,842,808','9,45,24,324'),(1999,'Star Wars Ep. I: The Phantom Menace','Adventure','PG','20th Century Fox','$430,443,350','$776,153,749','8,47,32,942'),(2000,'How the Grinch Stole Christmas','Adventure','PG','Universal','$253,367,455','$430,583,644','4,70,06,948'),(2001,'Harry Potter and the Sorcererâ€™s Stone','Adventure','PG','Warner Bros.','$300,404,434','$486,166,890','5,30,74,988'),(2002,'Spider-Man','Adventure','PG-13','Sony Pictures','$403,706,375','$636,480,273','6,94,84,746'),(2003,'Finding Nemo','Adventure','G','Walt Disney','$339,714,367','$516,050,346','5,63,37,374'),(2004,'Shrek 2','Adventure','PG','Dreamworks SKG','$441,226,247','$650,826,473','7,10,50,925'),(2005,'Star Wars Ep. III: Revenge of the Sith','Action','PG-13','20th Century Fox','$380,270,577','$543,413,171','5,93,24,582'),(2006,'Pirates of the Caribbean: Dead Manâ€™s Chest','Action','PG-13','Walt Disney','$423,315,812','$591,995,851','6,46,28,368'),(2007,'Spider-Man 3','Adventure','PG-13','Sony Pictures','$336,530,303','$448,054,878','4,89,14,288'),(2008,'The Dark Knight','Adventure','PG-13','Warner Bros.','$531,001,578','$677,433,772','7,39,55,652'),(2009,'Transformers: Revenge of the Fallen','Action','PG-13','Paramount Pictures','$402,111,870','$491,112,631','5,36,14,916'),(2010,'Toy Story 3','Action','G','Walt Disney','$415,004,880','$481,805,411','5,25,98,844'),(2011,'Harry Potter and the Deathly Hallows: Part II','Action','PG-13','Warner Bros.','$381,011,219','$440,108,798','4,80,46,812'),(2012,'The Avengers','Adventure','PG-13','Walt Disney','$623,357,910','$717,331,462','7,83,11,295'),(2013,'Iron Man 3','Adventure','PG-13','Walt Disney','$408,992,272','$460,808,016','5,03,06,552'),(2014,'Guardians of the Galaxy','Adventure','PG-13','Walt Disney','$333,055,258','$373,413,235','4,07,65,637'),(2015,'Star Wars Ep. VII: The Force Awakens','Action','PG-13','Walt Disney','$742,208,942','$806,480,887','8,80,43,765'),(2016,'Finding Dory','Action','PG','Walt Disney','$486,295,561','$514,967,322','5,62,19,140'),(2017,'Star Wars Ep. VIII: The Last Jedi','Action','PG-13','Walt Disney','$517,218,368','$528,173,936','5,76,60,910'),(2018,'Black Panther','Action','PG-13','Walt Disney','$700,059,566','$703,901,821','7,68,45,177'),(2019,'Avengers: Endgame',NULL,'PG-13','Walt Disney','$858,373,000','$858,373,002','9,37,08,843'),(2020,'Bad Boys For Life',NULL,'R','Sony Pictures','$204,417,855','$204,417,848','2,23,16,359'),(2021,'Shang-Chi and the Legend of the Ten Rings',NULL,'PG-13','Walt Disney','$224,226,704','$224,226,704','2,44,78,897');
/*!40000 ALTER TABLE `highestgrossers` ENABLE KEYS */;
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
