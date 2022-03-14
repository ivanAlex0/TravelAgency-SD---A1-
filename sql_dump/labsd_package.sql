-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: labsd
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package` (
  `id` int NOT NULL AUTO_INCREMENT,
  `availableSpots` int DEFAULT NULL,
  `extraDetails` varchar(255) DEFAULT NULL,
  `fromDate` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `package_status` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `toDate` date DEFAULT NULL,
  `destination_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lg8k39b5slhk6f4hyo0o3v2jt` (`name`),
  KEY `FK992xsdxbjya8hl5omifa3w5l6` (`destination_id`),
  CONSTRAINT `FK992xsdxbjya8hl5omifa3w5l6` FOREIGN KEY (`destination_id`) REFERENCES `destination` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
INSERT INTO `package` VALUES (1,1,'','2022-03-02','P1',1,23,'2022-03-04',1),(2,0,'','2022-03-02','P2',2,23,'2022-03-04',2),(3,0,'','2022-03-02','P3',2,23,'2022-03-04',3),(4,0,'','2022-03-02','P4',2,23,'2022-03-04',4),(5,0,'','2022-03-02','P5',2,23,'2022-03-04',1),(6,0,'','2022-03-02','P6',2,23,'2022-03-04',2),(7,0,'','2022-03-02','P8',2,23,'2022-03-04',2),(8,0,'','2022-03-02','P9',2,23,'2022-03-04',2),(9,0,'','2022-03-02','P90',2,23,'2022-03-04',2),(10,0,'','2022-03-02','P908',2,23,'2022-03-04',2),(11,0,'','2022-03-02','P9089',2,23,'2022-03-04',2),(12,0,'','2022-03-02','P90898',2,23,'2022-03-04',2),(13,0,'','2022-03-02','P908980',2,23,'2022-03-04',2),(14,0,'','2022-03-02','P9089808',2,23,'2022-03-04',2),(15,0,'','2022-03-02','P90898080',2,23,'2022-03-04',2),(16,0,'','2022-03-02','P908980808',2,23,'2022-03-04',2),(17,0,'','2022-03-02','P9089808080',2,23,'2022-03-04',2);
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-14 10:59:24
