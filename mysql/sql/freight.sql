-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: mysql    Database: freight
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `freight_express`
--

DROP TABLE IF EXISTS `freight_express`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `freight_express` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bill_code` varchar(128) DEFAULT NULL,
  `shop_logistics_id` bigint NOT NULL,
  `send_region_id` bigint DEFAULT NULL,
  `send_address` varchar(128) NOT NULL,
  `receiv_region_id` bigint NOT NULL,
  `receiv_address` varchar(128) NOT NULL,
  `send_name` varchar(128) NOT NULL,
  `send_mobile` varchar(64) NOT NULL,
  `receiv_name` varchar(128) NOT NULL,
  `status` tinyint NOT NULL DEFAULT '0',
  `shop_id` bigint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `receiv_mobile` varchar(64) DEFAULT NULL,
  `goods_type` varchar(32) DEFAULT NULL,
  `weight` bigint DEFAULT NULL,
  `order_code` varchar(128) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='运单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freight_express`
--

LOCK TABLES `freight_express` WRITE;
/*!40000 ALTER TABLE `freight_express` DISABLE KEYS */;
INSERT INTO freight.freight_express (id, bill_code, shop_logistics_id, send_region_id, send_address, receiv_region_id, receiv_address, send_name, send_mobile, receiv_name, status, shop_id, creator_id, creator_name, modifier_id, modifier_name, gmt_create, gmt_modified, receiv_mobile) VALUES (7, '202312141', 1, 21, '钟鼓社区居委会', 499, '金台里社区居委会', '李华', '123456', '张三', 0, 1, 1, 'admin', null, null, '2023-12-14 00:03:53', null, '123');
INSERT INTO freight.freight_express (id, bill_code, shop_logistics_id, send_region_id, send_address, receiv_region_id, receiv_address, send_name, send_mobile, receiv_name, status, shop_id, creator_id, creator_name, modifier_id, modifier_name, gmt_create, gmt_modified, receiv_mobile) VALUES (8, '202312142', 1, 21, '钟鼓社区居委会', 499, '金台里社区居委会', '李华', '123456', '张三', 1, 1, 1, 'admin', null, null, '2023-12-14 00:03:53', null, '123');
INSERT INTO freight.freight_express (id, bill_code, shop_logistics_id, send_region_id, send_address, receiv_region_id, receiv_address, send_name, send_mobile, receiv_name, status, shop_id, creator_id, creator_name, modifier_id, modifier_name, gmt_create, gmt_modified, receiv_mobile) VALUES (9, '202312143', 1, 21, '钟鼓社区居委会', 499, '金台里社区居委会', '李华', '123456', '张三', 2, 1, 1, 'admin', null, null, '2023-12-14 00:03:53', null, '123');
INSERT INTO freight.freight_express (id, bill_code, shop_logistics_id, send_region_id, send_address, receiv_region_id, receiv_address, send_name, send_mobile, receiv_name, status, shop_id, creator_id, creator_name, modifier_id, modifier_name, gmt_create, gmt_modified, receiv_mobile) VALUES (25, '202312144', 1, 21, '钟鼓社区居委会', 499, '金台里社区居委会', '李华', '123456', '张三', 5, 1, 1, 'admin', null, null, '2023-12-14 00:03:53', null, '123');
/*!40000 ALTER TABLE `freight_express` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `freight_logistics`
--

DROP TABLE IF EXISTS `freight_logistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `freight_logistics` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `sn_pattern` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `secret` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `logistics_class` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freight_logistics`
--

LOCK TABLES `freight_logistics` WRITE;
/*!40000 ALTER TABLE `freight_logistics` DISABLE KEYS */;
INSERT INTO `freight_logistics` VALUES (1,'顺丰快递','SF1001',1,'admin',NULL,NULL,'2022-12-02 18:59:35',NULL,'^SF[A-Za-z0-9-]{4,35}$',NULL,'sFAdaptor'),(2,'中通快递','ZTO0002',1,'admin',NULL,NULL,'2022-12-02 19:07:09',NULL,'^ZTO[0-9]{12}$',NULL,'zTOAdaptor'),(3,'极兔速递','JT11122',1,'admin',NULL,NULL,'2022-12-02 19:09:32',NULL,'^JT[0-9]{13}$',NULL,'jTAdaptor');
/*!40000 ALTER TABLE `freight_logistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `freight_shop_logistics`
--

DROP TABLE IF EXISTS `freight_shop_logistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `freight_shop_logistics` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint NOT NULL,
  `logistics_id` bigint NOT NULL,
  `secret` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `invalid` tinyint NOT NULL DEFAULT '0',
  `priority` int NOT NULL DEFAULT '1000',
  `account` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freight_shop_logistics`
--

LOCK TABLES `freight_shop_logistics` WRITE;
/*!40000 ALTER TABLE `freight_shop_logistics` DISABLE KEYS */;
INSERT INTO `freight_shop_logistics` VALUES (1,1,1,'secret1',1,'admin',NULL,NULL,'2022-12-02 11:32:34',NULL,0,3,NULL),(2,1,2,'secret2',1,'admin',NULL,NULL,'2022-12-02 11:32:34',NULL,0,113,NULL),(3,1,3,'secret3',1,'admin',NULL,NULL,'2022-12-02 11:32:34',NULL,1,2,NULL),(4,2,1,'secret1',1,'admin',NULL,NULL,'2022-12-02 11:33:09',NULL,0,3,NULL),(6,2,3,'secret3',1,'admin',NULL,NULL,'2022-12-02 11:33:09',NULL,0,5,NULL),(7,3,1,'secret1',1,'admin',NULL,NULL,'2022-12-02 11:33:16',NULL,0,3,NULL),(8,3,2,'secret2',1,'admin',NULL,NULL,'2022-12-02 11:33:16',NULL,0,4,NULL),(9,3,3,'secret3',1,'admin',NULL,NULL,'2022-12-02 11:33:16',NULL,0,5,NULL),(10,4,1,'secret1',1,'admin',NULL,NULL,'2022-12-02 11:33:22',NULL,0,3,NULL),(11,4,2,'secret2',1,'admin',NULL,NULL,'2022-12-02 11:33:22',NULL,0,4,NULL),(12,4,3,'secret3',1,'admin',NULL,NULL,'2022-12-02 11:33:22',NULL,0,5,NULL),(13,5,1,'secret1',1,'admin',NULL,NULL,'2022-12-02 11:33:28',NULL,0,3,NULL),(14,5,2,'secret2',1,'admin',NULL,NULL,'2022-12-02 11:33:28',NULL,0,4,NULL),(15,5,3,'secret3',1,'admin',NULL,NULL,'2022-12-02 11:33:28',NULL,0,123,NULL),(16,6,1,'secret1',1,'admin',NULL,NULL,'2022-12-02 11:33:34',NULL,1,3,NULL),(17,6,2,'secret2',1,'admin',NULL,NULL,'2022-12-02 11:33:34',NULL,0,4,NULL),(18,6,3,'secret3',1,'admin',NULL,NULL,'2022-12-02 11:33:34',NULL,0,3,NULL),(19,7,1,'secret1',1,'admin',NULL,NULL,'2022-12-02 11:33:40',NULL,0,35,NULL),(20,7,2,'secret2',1,'admin',NULL,NULL,'2022-12-02 11:33:40',NULL,0,44,NULL),(21,7,3,'secret3',1,'admin',NULL,NULL,'2022-12-02 11:33:40',NULL,0,5,NULL),(22,8,1,'secret1',1,'admin',NULL,NULL,'2022-12-02 11:33:46',NULL,0,23,NULL),(23,8,2,'secret2',1,'admin',NULL,NULL,'2022-12-02 11:33:46',NULL,0,4,NULL),(24,8,3,'secret3',1,'admin',NULL,NULL,'2022-12-02 11:33:46',NULL,1,10,NULL),(25,9,1,'secret1',1,'admin',NULL,NULL,'2022-12-02 11:33:52',NULL,0,3,NULL),(26,9,2,'secret2',1,'admin',NULL,NULL,'2022-12-02 11:33:52',NULL,0,4,NULL),(27,9,3,'secret3',1,'admin',NULL,NULL,'2022-12-02 11:33:52',NULL,0,5,NULL),(28,10,1,'secret1',1,'admin',NULL,NULL,'2022-12-02 11:33:57',NULL,0,5,NULL),(29,10,2,'secret2',1,'admin',NULL,NULL,'2022-12-02 11:33:57',NULL,0,4,NULL),(30,10,3,'secret3',1,'admin',NULL,NULL,'2022-12-02 11:33:57',NULL,0,22,NULL);
/*!40000 ALTER TABLE `freight_shop_logistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `freight_undeliverable`
--

DROP TABLE IF EXISTS `freight_undeliverable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `freight_undeliverable` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `region_id` bigint NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `shop_logistics_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freight_undeliverable`
--

LOCK TABLES `freight_undeliverable` WRITE;
/*!40000 ALTER TABLE `freight_undeliverable` DISABLE KEYS */;
INSERT INTO `freight_undeliverable` VALUES (1,483250,'2022-12-02 22:28:43','2023-12-02 22:28:49',1,'admin',NULL,NULL,'2022-12-02 14:29:21',NULL,1);
/*!40000 ALTER TABLE `freight_undeliverable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `freight_warehouse`
--

DROP TABLE IF EXISTS `freight_warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `freight_warehouse` (
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint NOT NULL,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sender_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `region_id` bigint NOT NULL,
  `sender_mobile` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `priority` int NOT NULL DEFAULT '1000',
  `invalid` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freight_warehouse`
--

LOCK TABLES `freight_warehouse` WRITE;
/*!40000 ALTER TABLE `freight_warehouse` DISABLE KEYS */;
INSERT INTO `freight_warehouse` VALUES ('北京,朝阳,东坝,朝阳新城第二曙光路14号',1,1,'朝阳新城第二仓库','阮杰',1,'admin',NULL,NULL,'2022-12-02 11:55:26',NULL,1043,'139542562579',1000,0),('北京,朝阳,黑庄户,黑庄户曙光路14号',2,2,'黑庄户仓库','刘雨堡',1,'admin',NULL,NULL,'2022-12-02 11:56:07',NULL,1068,'139630558019',1000,0),('北京,丰台,长辛店,朱家坟西山坡曙光路14号',3,3,'朱家坟西山坡仓库','隋问',1,'admin',NULL,NULL,'2022-12-02 11:56:39',NULL,1362,'139206174517',1000,0),('北京,朝阳,小红门,牌坊曙光路14号',4,4,'牌坊仓库','张三',1,'admin',NULL,NULL,'2022-12-02 11:56:41',NULL,797,'139266427223',1000,0),('北京,丰台,方庄,芳城园一区曙光路14号',5,5,'芳城园一区仓库','张三',1,'admin',NULL,NULL,'2022-12-02 11:56:41',NULL,1396,'139144166588',1000,0),('北京,西城,天桥,香厂路曙光路14号',6,6,'香厂路仓库','张三',1,'admin',NULL,NULL,'2022-12-02 11:56:42',NULL,374,'139282650380',1000,0),('天津,静海,唐官屯,长张屯曙光路14号',7,7,'长张屯仓库','张三',1,'admin',NULL,NULL,'2022-12-02 11:57:23',NULL,11881,'139936103327',1000,0),('北京,房山,长阳,水碾屯一曙光路14号',8,8,'水碾屯一仓库','张三',1,'admin',NULL,NULL,'2022-12-02 11:58:19',NULL,3048,'13937159674',1000,0),('内蒙古,包头,固阳,兴顺西,佘太和村委曙光路14号',9,3,'佘太和村委仓库','下官云',1,'admin',NULL,NULL,'2022-12-02 11:58:51',NULL,101672,'139251493531',1000,0),('湖北,潜江,渔洋,苏湖林场曙光路14号',10,1,'苏湖林场仓库','钱峰',1,'admin',NULL,NULL,'2022-12-02 11:59:51',NULL,450826,'13948733198',1000,0),('河北,张家口,康保,康保,道北村曙光路14号',11,9,'道北村仓库','张三',1,'admin',NULL,NULL,'2022-12-02 12:00:06',NULL,46047,'139429301542',1000,0),('广东,梅州,兴宁,大坪,吴田曙光路14号',12,1,'吴田仓库','邹宇',1,'admin',NULL,NULL,'2022-12-02 12:00:16',NULL,501765,'139464827803',1000,0),('浙江,温州,苍南,灵溪,徐溪曙光路14号',13,2,'徐溪仓库','汪天',1,'admin',NULL,NULL,'2022-12-02 12:00:23',NULL,200397,'139703721355',1000,0),('浙江,湖州,吴兴,飞英,墙壕里曙光路14号',14,1,'墙壕里仓库','钱王',1,'admin',NULL,NULL,'2022-12-02 12:00:40',NULL,205140,'139844014684',1000,0),('山东,东营,东营,龙居,大张曙光路14号',15,2,'大张仓库','刘玉',1,'admin',NULL,NULL,'2022-12-02 12:00:47',NULL,304646,'13942544704',1000,0),('河南,商丘,宁陵,阳驿,汤林王曙光路14号',16,1,'汤林王仓库','刘曼娜',1,'admin',NULL,NULL,'2022-12-02 12:01:15',NULL,405443,'139891799202',1000,0),('内蒙古,通辽,科尔沁,庆和,五家子曙光路14号',17,2,'五家子仓库','吴国强',1,'admin',NULL,NULL,'2022-12-02 12:01:24',NULL,104859,'139979882074',1000,0),('内蒙古,锡林郭勒,正蓝旗,桑根达来,塔安图嘎查村曙光路14号',18,1,'塔安图嘎查村仓库','王天然',1,'admin',NULL,NULL,'2022-12-02 12:01:47',NULL,114759,'139465529695',1000,0),('辽宁,营口,老边,柳树,西柳曙光路14号',19,1,'西柳仓库','邹强',1,'admin',NULL,NULL,'2022-12-02 12:01:52',NULL,125484,'139801990328',1000,0),('吉林,长春,榆树,太安,发展曙光路14号',20,1,'发展仓库','刘宏',1,'admin',NULL,NULL,'2022-12-02 12:01:57',NULL,134975,'139323756249',1000,0),('湖北,荆门,钟祥,双河,石龙曙光路14号',21,1,'石龙仓库','赵四',1,'admin',NULL,NULL,'2022-12-02 12:02:02',NULL,434154,'139410853107',1000,0),('河北,保定,涿州,林家屯,小庄村曙光路14号',22,10,'小庄村仓库','张三',1,'admin',NULL,NULL,'2022-12-02 12:02:10',NULL,43810,'139268281627',1000,0),('河北,沧州,盐山,边务,东王庄曙光路14号',23,3,'东王庄仓库','张三',1,'admin',NULL,NULL,'2022-12-02 12:02:15',NULL,54099,'139155752960',1000,0),('福建,漳州,漳浦,官浔,康庄曙光路14号',24,1,'康庄仓库','王五',1,'admin',NULL,NULL,'2022-12-02 12:04:47',NULL,254544,'13996693776',1000,0),('山东,聊城,东阿,新城,西王集曙光路14号',25,1,'西王集仓库','张三',1,'admin',NULL,NULL,'2022-12-02 12:04:58',NULL,353427,'13933202143',1000,0);
/*!40000 ALTER TABLE `freight_warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `freight_warehouse_logistics`
--

DROP TABLE IF EXISTS `freight_warehouse_logistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `freight_warehouse_logistics` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `warehouse_id` bigint NOT NULL,
  `shop_logistics_id` bigint NOT NULL,
  `begin_time` datetime NOT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `end_time` datetime NOT NULL,
  `invalid` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=911 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freight_warehouse_logistics`
--

LOCK TABLES `freight_warehouse_logistics` WRITE;
/*!40000 ALTER TABLE `freight_warehouse_logistics` DISABLE KEYS */;
INSERT INTO `freight_warehouse_logistics` VALUES (797,25,1,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(798,24,1,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(799,21,1,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(800,20,1,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(801,19,1,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(802,18,1,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(803,16,1,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(804,14,1,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(805,12,1,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(806,10,1,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(807,1,1,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(808,25,2,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(809,24,2,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(810,21,2,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(811,20,2,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(812,19,2,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(813,18,2,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(814,16,2,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(815,14,2,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(816,12,2,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(817,10,2,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(818,1,2,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(819,25,3,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(820,24,3,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(821,21,3,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(822,20,3,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(823,19,3,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(824,18,3,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(825,16,3,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(826,14,3,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(827,12,3,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(828,10,3,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(829,1,3,'2022-12-02 13:57:43',NULL,NULL,NULL,NULL,'2022-12-02 13:57:43',NULL,'2023-04-02 13:57:43',0),(860,17,4,'2022-12-02 13:57:58',NULL,NULL,NULL,NULL,'2022-12-02 13:57:58',NULL,'2023-04-02 13:57:58',0),(861,15,4,'2022-12-02 13:57:58',NULL,NULL,NULL,NULL,'2022-12-02 13:57:58',NULL,'2023-04-02 13:57:58',0),(862,13,4,'2022-12-02 13:57:58',NULL,NULL,NULL,NULL,'2022-12-02 13:57:58',NULL,'2023-04-02 13:57:58',0),(863,2,4,'2022-12-02 13:57:58',NULL,NULL,NULL,NULL,'2022-12-02 13:57:58',NULL,'2023-04-02 13:57:58',0),(864,17,6,'2022-12-02 13:57:58',NULL,NULL,NULL,NULL,'2022-12-02 13:57:58',NULL,'2023-04-02 13:57:58',0),(865,15,6,'2022-12-02 13:57:58',NULL,NULL,NULL,NULL,'2022-12-02 13:57:58',NULL,'2023-04-02 13:57:58',0),(866,13,6,'2022-12-02 13:57:58',NULL,NULL,NULL,NULL,'2022-12-02 13:57:58',NULL,'2023-04-02 13:57:58',0),(867,2,6,'2022-12-02 13:57:58',NULL,NULL,NULL,NULL,'2022-12-02 13:57:58',NULL,'2023-04-02 13:57:58',0),(875,23,7,'2022-12-02 13:58:09',NULL,NULL,NULL,NULL,'2022-12-02 13:58:09',NULL,'2023-04-02 13:58:09',0),(876,9,7,'2022-12-02 13:58:09',NULL,NULL,NULL,NULL,'2022-12-02 13:58:09',NULL,'2023-04-02 13:58:09',0),(877,3,7,'2022-12-02 13:58:09',NULL,NULL,NULL,NULL,'2022-12-02 13:58:09',NULL,'2023-04-02 13:58:09',0),(878,23,8,'2022-12-02 13:58:09',NULL,NULL,NULL,NULL,'2022-12-02 13:58:09',NULL,'2023-04-02 13:58:09',0),(879,9,8,'2022-12-02 13:58:09',NULL,NULL,NULL,NULL,'2022-12-02 13:58:09',NULL,'2023-04-02 13:58:09',0),(880,3,8,'2022-12-02 13:58:09',NULL,NULL,NULL,NULL,'2022-12-02 13:58:09',NULL,'2023-04-02 13:58:09',0),(881,23,9,'2022-12-02 13:58:09',NULL,NULL,NULL,NULL,'2022-12-02 13:58:09',NULL,'2023-04-02 13:58:09',0),(882,9,9,'2022-12-02 13:58:09',NULL,NULL,NULL,NULL,'2022-12-02 13:58:09',NULL,'2023-04-02 13:58:09',0),(883,3,9,'2022-12-02 13:58:09',NULL,NULL,NULL,NULL,'2022-12-02 13:58:09',NULL,'2023-04-02 13:58:09',0),(890,4,10,'2022-12-02 13:58:16',NULL,NULL,NULL,NULL,'2022-12-02 13:58:16',NULL,'2023-04-02 13:58:16',0),(891,4,11,'2022-12-02 13:58:16',NULL,NULL,NULL,NULL,'2022-12-02 13:58:16',NULL,'2023-04-02 13:58:16',0),(892,4,12,'2022-12-02 13:58:16',NULL,NULL,NULL,NULL,'2022-12-02 13:58:16',NULL,'2023-04-02 13:58:16',0),(893,5,13,'2022-12-02 13:58:22',NULL,NULL,NULL,NULL,'2022-12-02 13:58:22',NULL,'2023-04-02 13:58:22',0),(894,5,14,'2022-12-02 13:58:22',NULL,NULL,NULL,NULL,'2022-12-02 13:58:22',NULL,'2023-04-02 13:58:22',0),(895,5,15,'2022-12-02 13:58:22',NULL,NULL,NULL,NULL,'2022-12-02 13:58:22',NULL,'2023-04-02 13:58:22',0),(896,6,16,'2022-12-02 13:58:29',NULL,NULL,NULL,NULL,'2022-12-02 13:58:29',NULL,'2023-04-02 13:58:29',0),(897,6,17,'2022-12-02 13:58:29',NULL,NULL,NULL,NULL,'2022-12-02 13:58:29',NULL,'2023-04-02 13:58:29',0),(898,6,18,'2022-12-02 13:58:29',NULL,NULL,NULL,NULL,'2022-12-02 13:58:29',NULL,'2023-04-02 13:58:29',0),(899,7,19,'2022-12-02 13:58:37',NULL,NULL,NULL,NULL,'2022-12-02 13:58:37',NULL,'2023-04-02 13:58:37',0),(900,7,20,'2022-12-02 13:58:37',NULL,NULL,NULL,NULL,'2022-12-02 13:58:37',NULL,'2023-04-02 13:58:37',0),(901,7,21,'2022-12-02 13:58:37',NULL,NULL,NULL,NULL,'2022-12-02 13:58:37',NULL,'2023-04-02 13:58:37',0),(902,8,22,'2022-12-02 13:58:43',NULL,NULL,NULL,NULL,'2022-12-02 13:58:43',NULL,'2023-04-02 13:58:43',0),(903,8,23,'2022-12-02 13:58:43',NULL,NULL,NULL,NULL,'2022-12-02 13:58:43',NULL,'2023-04-02 13:58:43',0),(904,8,24,'2022-12-02 13:58:43',NULL,NULL,NULL,NULL,'2022-12-02 13:58:43',NULL,'2023-04-02 13:58:43',0),(905,11,25,'2022-12-02 13:58:50',NULL,NULL,NULL,NULL,'2022-12-02 13:58:50',NULL,'2023-04-02 13:58:50',0),(906,11,26,'2022-12-02 13:58:50',NULL,NULL,NULL,NULL,'2022-12-02 13:58:50',NULL,'2023-04-02 13:58:50',0),(907,11,27,'2022-12-02 13:58:50',NULL,NULL,NULL,NULL,'2022-12-02 13:58:50',NULL,'2023-04-02 13:58:50',0),(908,22,28,'2022-12-02 13:58:56',NULL,NULL,NULL,NULL,'2022-12-02 13:58:56',NULL,'2023-04-02 13:58:56',0),(909,22,29,'2022-12-02 13:58:56',NULL,NULL,NULL,NULL,'2022-12-02 13:58:56',NULL,'2023-04-02 13:58:56',0),(910,22,30,'2022-12-02 13:58:56',NULL,NULL,NULL,NULL,'2022-12-02 13:58:56',NULL,'2023-04-02 13:58:56',0);
/*!40000 ALTER TABLE `freight_warehouse_logistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `freight_warehouse_region`
--

DROP TABLE IF EXISTS `freight_warehouse_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `freight_warehouse_region` (
  `warehouse_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `region_id` bigint NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freight_warehouse_region`
--

LOCK TABLES `freight_warehouse_region` WRITE;
/*!40000 ALTER TABLE `freight_warehouse_region` DISABLE KEYS */;
INSERT INTO `freight_warehouse_region` VALUES (1,1,1,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,NULL,NULL,NULL,'2022-12-02 14:20:05',NULL),(2,2,1,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(3,3,1,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(4,4,1,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(5,5,1,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(6,6,1,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(7,7,7362,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(8,8,1,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(9,9,99537,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(10,10,420824,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(11,11,13267,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(12,12,483250,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(13,13,191019,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(14,14,191019,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(15,15,285860,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(16,16,367395,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(17,17,99537,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(18,18,99537,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(19,19,115224,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(20,20,133208,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(21,21,420824,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(22,22,13267,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(23,23,13267,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(24,24,244377,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(25,25,285860,'2022-12-02 14:20:05','2023-04-02 14:20:05',1,'admin',NULL,NULL,'2022-12-02 14:20:05',NULL),(24,32,483250,'2022-12-02 22:25:49','2023-12-02 22:25:52',1,'admin',NULL,NULL,'2022-12-02 14:26:54',NULL);
/*!40000 ALTER TABLE `freight_warehouse_region` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-16  7:02:22
