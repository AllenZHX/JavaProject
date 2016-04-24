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
-- Table structure for table `servicelist`
--

DROP TABLE IF EXISTS `servicelist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servicelist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `items` char(35) NOT NULL DEFAULT '',
  `price` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicelist`
--

LOCK TABLES `servicelist` WRITE;
/*!40000 ALTER TABLE `servicelist` DISABLE KEYS */;
INSERT INTO `servicelist` VALUES (1,'Apple Juice',2,64),(2,'Orange Juice',2,68),(3,'Spring Water',1,187),(4,'Coffee Latte',5,0),(5,'Cappuccino Coffee',5,17),(6,'Café Americano',4,12),(7,'Colombian Coffee',4,32),(8,'Slim Milk',2,5),(9,'Full Milk',2,51),(10,'Brandy',10,7),(11,'Vodka',10,96),(12,'Cocktail',8,100),(13,'Red Wine',5,62),(14,'Beer',2,70),(15,'Tea',1,43),(16,'Yoghurt',2,0),(17,'Coca Cola',1,3),(18,'Sprite',1,191),(19,'Soda',1,99),(20,'Coconut Juice',2,85),(21,'Ice Cream',3,14),(22,'Biscuit',3,44),(23,'Boiled Dumplings',5,17),(24,'Roll',2,0),(25,'Fish ball noodels',5,49),(26,'Seafood Noodle',8,88),(27,'Mashed Potatoes',3,40),(28,'Fried egg',1,44),(29,'Pudding',3,45),(30,'Hamburger',5,94);
/*!40000 ALTER TABLE `servicelist` ENABLE KEYS */;
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
