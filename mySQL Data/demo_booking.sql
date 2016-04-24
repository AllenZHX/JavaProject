-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	5.7.11-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(35) NOT NULL DEFAULT '',
  `idnum` char(20) NOT NULL DEFAULT '',
  `room` char(8) NOT NULL DEFAULT '',
  `roomid` int(11) NOT NULL,
  `fromday` char(30) NOT NULL DEFAULT '',
  `today` char(30) NOT NULL DEFAULT '',
  `status` char(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (20,'Gem','55446677','D11',22,'2016-04-17','2016-04-25','Booked!'),(29,'Annie','33997777','D5',11,'2016-04-20','2016-04-24','Booked!'),(34,'Eddie','77885544','D3',4,'2016-05-01','2016-05-04','Booked!'),(35,'Harry','22888899','S1',6,'2016-04-28','2016-05-04','Booked!'),(36,'Dinaa','55999333','D2',3,'2016-04-24','2016-04-28','Booked!'),(37,'Nassia','99777333','S1',6,'2016-04-22','2016-04-25','Booked!'),(38,'Kathy','33229966','S2',7,'2016-04-23','2016-04-26','Booked!'),(39,'Bieoea','88833551','D8',14,'2016-04-25','2016-04-29','Booked!'),(42,'Ramma','22288855','S8',25,'2016-04-25','2016-04-27','Booked!'),(43,'Pyton','77771212','D10',21,'2016-04-28','2016-04-30','Booked!'),(44,'Leonard','54542211','S2',7,'2016-04-27','2016-04-29','Booked!'),(45,'Dounan','99922211','F1',0,'2016-05-01','2016-05-06','Booked!'),(46,'Onoal','44499111','D6',12,'2016-05-01','2016-05-06','Booked!'),(47,'Xin Zhao','35536464','S2',7,'2016-05-01','2016-05-04','Booked!'),(48,'Cathy','89984422','F2',1,'2016-05-02','2016-05-05','Booked!'),(49,'Harden','66699666','D8',14,'2016-05-01','2016-05-03','Booked!'),(50,'Green','67675544','F3',9,'2016-04-25','2016-04-28','Booked!'),(51,'Qinue','77332200','S4',15,'2016-04-23','2016-04-25','Booked!'),(52,'Tirria','55778822','S8',25,'2016-05-01','2016-05-05','Booked!'),(54,'Ying Ruo','67672233','S9',26,'2016-04-24','2016-04-28','Booked!'),(55,'Dao','777777888','D1',2,'2016-05-01','2016-05-03','Booked!'),(56,'Zhibo','23452345','S5',16,'2016-04-27','2016-04-30','Booked!'),(57,'Lissade','55667722','D7',13,'2016-04-28','2016-05-01','Booked!');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-24 15:18:03
