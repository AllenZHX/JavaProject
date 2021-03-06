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
-- Table structure for table `checkoutlist`
--

DROP TABLE IF EXISTS `checkoutlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkoutlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(35) NOT NULL DEFAULT '',
  `idnum` char(20) NOT NULL DEFAULT '',
  `room` char(8) NOT NULL DEFAULT '',
  `intime` char(20) NOT NULL DEFAULT '',
  `outtime` char(20) NOT NULL DEFAULT '',
  `totalfee` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkoutlist`
--

LOCK TABLES `checkoutlist` WRITE;
/*!40000 ALTER TABLE `checkoutlist` DISABLE KEYS */;
INSERT INTO `checkoutlist` VALUES (15,'Sol','66666223','D11','2016-04-16 11:23:00','2016-04-16 11:55:58',175),(16,'Hong','10000001','S1','2016-04-15 19:30:39','2016-04-17 21:29:24',280),(17,'Nasus','11223388','S6','2016-04-17 12:51:50','2016-04-18 17:37:26',180),(18,'Ashe','23768230','D6','2016-04-15 20:28:12','2016-04-20 20:09:45',975),(19,'Huaasu','55555003','D1','2016-04-16 11:24:28','2016-04-20 20:10:28',804),(20,'Amumu','77554400','D2','2016-04-18 17:36:23','2016-04-20 20:10:50',480),(21,'Lulu','32132132','D3','2016-04-17 12:50:12','2016-04-20 20:11:01',648),(22,'Joey','33388899','D4','2016-04-17 12:51:22','2016-04-20 20:11:14',680),(23,'IU','55008833','S4','2016-04-17 12:52:16','2016-04-20 20:11:32',363),(24,'Yasuo','99999003','S5','2016-04-15 23:39:23','2016-04-20 20:11:39',542),(25,'Garen','22222009','S7','2016-04-17 12:46:51','2016-04-20 20:11:46',364),(26,'Mikey','55002211','F6','2016-04-16 11:54:00','2016-04-20 20:12:06',1136),(27,'Olaf','77224433','D10','2016-04-17 12:52:40','2016-04-20 20:12:12',650),(28,'Lee Jian','44433322','S2','2016-04-17 12:50:41','2016-04-22 14:19:07',554),(29,'Curry','11111009','D8','2016-04-16 11:24:50','2016-04-22 14:19:29',1128),(30,'Pirece','88998899','S9','2016-04-20 20:14:57','2016-04-23 12:10:52',360),(31,'Yao','11122266','D1','2016-04-20 20:14:34','2016-04-23 12:11:09',640);
/*!40000 ALTER TABLE `checkoutlist` ENABLE KEYS */;
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
