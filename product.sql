-- MariaDB dump 10.19  Distrib 10.4.24-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: product
-- ------------------------------------------------------
-- Server version	10.4.24-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_detail` (
  `order_detail_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `product_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `product_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `FK_OrderDetail_Order` (`order_id`),
  KEY `FK_OrderDetail_Product` (`product_id`),
  CONSTRAINT `FK_OrderDetail_Order` FOREIGN KEY (`order_id`) REFERENCES `order_product` (`order_id`),
  CONSTRAINT `FK_OrderDetail_Product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES ('DETAIL1','ORDER1','PRO_1',1),('DETAIL10','ORDER8','PRO_1',1),('DETAIL11','ORDER8','PRO_2',1),('DETAIL2','ORDER1','PRO_3',1),('DETAIL3','ORDER2','PRO_1',1),('DETAIL4','ORDER2','PRO_2',2),('DETAIL5','ORDER3','PRO_2',2),('DETAIL6','ORDER4','PRO_4',2),('DETAIL7','ORDER5','PRO_3',3),('DETAIL8','ORDER6','PRO_3',2),('DETAIL9','ORDER7','PRO_3',2);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_product`
--

DROP TABLE IF EXISTS `order_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_product` (
  `order_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `order_date` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_product`
--

LOCK TABLES `order_product` WRITE;
/*!40000 ALTER TABLE `order_product` DISABLE KEYS */;
INSERT INTO `order_product` VALUES ('ORDER1','2022-04-19',1),('ORDER2','2022-04-13',0),('ORDER3','2022-05-11',1),('ORDER4','2022-05-11',1),('ORDER5','2022-05-11',1),('ORDER6','2022-05-11',1),('ORDER7','2022-05-11',1),('ORDER8','2022-05-11',1);
/*!40000 ALTER TABLE `order_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `product_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `product_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `product_quantity` int(11) DEFAULT NULL,
  `product_detail` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('PRO_1','product 1',1,'detail product 1'),('PRO_2','product 2',2,'detail product 2'),('PRO_3','product 3',0,'detail product 3'),('PRO_4','product 4',0,'detail product 4'),('PRO_5','product 5',0,'detail product 5'),('PRO_6','product 6',0,'detail product 6'),('PRO_7','product 7',0,'detail product 7');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slot`
--

DROP TABLE IF EXISTS `slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slot` (
  `slot_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `storage_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `product_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`slot_id`),
  KEY `FK_Slot_Storage` (`storage_id`),
  KEY `FK_Slot_Product` (`product_id`),
  CONSTRAINT `FK_Slot_Product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FK_Slot_Storage` FOREIGN KEY (`storage_id`) REFERENCES `storage` (`storage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slot`
--

LOCK TABLES `slot` WRITE;
/*!40000 ALTER TABLE `slot` DISABLE KEYS */;
/*!40000 ALTER TABLE `slot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage`
--

DROP TABLE IF EXISTS `storage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storage` (
  `storage_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`storage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage`
--

LOCK TABLES `storage` WRITE;
/*!40000 ALTER TABLE `storage` DISABLE KEYS */;
/*!40000 ALTER TABLE `storage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `tag_id` varchar(30) CHARACTER SET utf8 NOT NULL,
  `product_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `tag_gate_in` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tag_date_in` date DEFAULT NULL,
  `tag_gate_out` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tag_date_out` date DEFAULT NULL,
  PRIMARY KEY (`tag_id`),
  KEY `FK_Tag_Product` (`product_id`),
  CONSTRAINT `FK_Tag_Product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES ('3500 0000 1000 0010 0000 1690','PRO_2',NULL,NULL,'gate3','2022-05-11'),('3500 0000 1000 0010 0000 20E8','PRO_2',NULL,NULL,'gate3','2022-05-11'),('4D41 5400','PRO_1',NULL,NULL,'gate3','2022-05-11'),('4D4F 5300','PRO_2',NULL,NULL,'gate3','2022-05-11'),('E200 1026 8110 0159 1490 7999','PRO_3',NULL,NULL,'gate3','2022-05-11'),('E200 1026 8110 0274 1500 77CE','PRO_3',NULL,NULL,'gate3','2022-05-11'),('E200 3072 0205 0238 2530 142E','PRO_1',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-12 14:37:33
