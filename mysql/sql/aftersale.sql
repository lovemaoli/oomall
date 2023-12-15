-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: mysql    Database: aftersale
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aftersale_aftersale`
--

DROP TABLE IF EXISTS `aftersale_aftersale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aftersale_aftersale` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_item_id` bigint DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `shop_id` bigint DEFAULT NULL,
  `aftersale_sn` varchar(128) DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `conclusion` varchar(500) DEFAULT NULL,
  `quantity` bigint DEFAULT NULL,
  `region_id` bigint DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `consignee` varchar(128) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `service_id` bigint DEFAULT NULL,
  `serial_no` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `in_arbitrated` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aftersale_aftersale`
--

LOCK TABLES `aftersale_aftersale` WRITE;
/*!40000 ALTER TABLE `aftersale_aftersale` DISABLE KEYS */;
/*!40000 ALTER TABLE `aftersale_aftersale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aftersale_arbitration`
--

DROP TABLE IF EXISTS `aftersale_arbitration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aftersale_arbitration` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `aftersale_id` bigint DEFAULT NULL,
  `reason` varchar(256) COLLATE utf8mb4_general_ci NOT NULL,
  `result` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `arbitrator_id` bigint DEFAULT NULL,
  `arbitrator_name` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gmtArbitration` datetime DEFAULT NULL,
  `applicant_type` tinyint NOT NULL DEFAULT '0',
  `gmtAccept` datetime DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aftersale_arbitration`
--

LOCK TABLES `aftersale_arbitration` WRITE;
/*!40000 ALTER TABLE `aftersale_arbitration` DISABLE KEYS */;
/*!40000 ALTER TABLE `aftersale_arbitration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aftersale_history`
--

DROP TABLE IF EXISTS `aftersale_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aftersale_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `aftersale_id` bigint DEFAULT NULL,
  `content` varchar(256) COLLATE utf8mb4_general_ci NOT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aftersale_history`
--

LOCK TABLES `aftersale_history` WRITE;
/*!40000 ALTER TABLE `aftersale_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `aftersale_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aftersale_order`
--

DROP TABLE IF EXISTS `aftersale_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aftersale_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `aftersale_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aftersale_order`
--

LOCK TABLES `aftersale_order` WRITE;
/*!40000 ALTER TABLE `aftersale_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `aftersale_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aftersale_package`
--

DROP TABLE IF EXISTS `aftersale_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aftersale_package` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` tinyint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `aftersale_id` bigint NOT NULL,
  `package_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aftersale_package`
--

LOCK TABLES `aftersale_package` WRITE;
/*!40000 ALTER TABLE `aftersale_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `aftersale_package` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-22 15:50:59
