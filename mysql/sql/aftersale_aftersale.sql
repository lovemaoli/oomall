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
-- Table structure for table `aftersale`
--

DROP TABLE IF EXISTS `aftersale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aftersale`
(
    `id`              bigint NOT NULL AUTO_INCREMENT,
    `aftersale_sn`    bigint                                  DEFAULT NULL,
    `type`            tinyint                                 DEFAULT NULL,
    `reason`          varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `conclusion`      varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `quantity`        bigint                                  DEFAULT NULL,
    `contact`         varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `mobile`          varchar(32) COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `address`         varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `status`          bigint                                  DEFAULT NULL,
    `gmt_apply`       datetime                                DEFAULT NULL,
    `gmt_end`         datetime                                DEFAULT NULL,
    `order_item_id`   bigint                                  DEFAULT NULL,
    `product_id`      bigint                                  DEFAULT NULL,
    `product_item_id` bigint                                  DEFAULT NULL,
    `shop_id`         bigint                                  DEFAULT NULL,
    `customer_id`     bigint                                  DEFAULT NULL,
    `in_arbitration`  tinyint                                 DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aftersale`
--

LOCK
TABLES `aftersale` WRITE;
/*!40000 ALTER TABLE `aftersale` DISABLE KEYS */;
/*!40000 ALTER TABLE `aftersale` ENABLE KEYS */;


INSERT INTO `aftersale`(`aftersale_sn`, `type`, `reason`, `conclusion`, `quantity`, `contact`, `mobile`, `address`, `status`, `gmt_apply`, `gmt_end`, `order_item_id`, `product_id`, `product_item_id`, `shop_id`, `customer_id`, `in_arbitration`)
VALUES  (NULL, 0, '退乐驰不锈钢鞋架', '审核不通过', 3, '李四', '22222', '广东省深圳市福田区香蜜湖1号', 5, '2023-12-25 12:03:53', NULL, 1005, 227, 227, 2, 3, 1),
        (NULL, 1, '换货', '审核不通过', 1, '刘宏', '556', '广东省深圳市福田区香蜜湖31号', 5, '2023-12-25 12:03:53', NULL, 1005, 227, 227, 2, 3, 1),
        (NULL, 2, '佳威计算机维修', '审核通过', 1, '王五', '2222', '广东省深圳市福田区香蜜湖31号', 2, '2023-12-25 12:03:53', NULL, 17496, 2665, 2665, 3, 3, 0),
        (NULL, 1, '换洗衣粉', '审核通过', 1, '张三', '11111', '上海市浦东新区南京路89号', 3, '2023-12-25 12:03:53', NULL, 170, 1719, 1719, 1, 3, 0),
        (NULL, 2, '维修电脑', '审核通过', 1, '王五', '2222', '广东省深圳市福田区香蜜湖351号', 3, '2023-12-25 12:03:53', NULL, 1116, 2665, 2665, 3, 1, 0),
        (NULL, 2, '得生收音机维修', '审核通过', 1, '周器', '33333', '上海市浦东新区南京路789号', 3, '2023-12-24 23:03:53', NULL, 2834, 4383, 4383, 5, 1, 0),
        (NULL, 2, '美的电磁炉维修', '审核通过', 1, '常遇春', '66667', '福建省厦门市翔安区沙美街351号', 3, '2023-12-12 23:03:53', NULL, 1237, 2786, 2786, 8, 2, 0),
        (NULL, 2, '美的电磁炉维修', '审核通过', 1, '王五', '2222', '上海市浦东新区南京路899号', 3, '2023-10-25 23:03:53', NULL, 1238, 2787, 2787, 3, 2, 0);

UNLOCK
TABLES;

--
-- Table structure for table `arbitration`
--

DROP TABLE IF EXISTS `arbitration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `arbitration`
(
    `id`              bigint NOT NULL AUTO_INCREMENT,
    `arbitration_sn`  bigint                                  DEFAULT NULL,
    `status`          bigint                                  DEFAULT NULL,
    `reason`          varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `shop_reason`     varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `result`          varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `arbitrator`      varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `gmt_arbitration` datetime                                DEFAULT NULL,
    `gmt_accept`      datetime                                DEFAULT NULL,
    `gmt_apply`       datetime                                DEFAULT NULL,
    `gmt_reply`       datetime                                DEFAULT NULL,
    `shop_id`         bigint                                  DEFAULT NULL,
    `aftersale_id`    bigint                                  DEFAULT NULL,
    `customer_id`     bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arbitration`
--

LOCK
TABLES `arbitration` WRITE;

INSERT INTO `arbitration`(`arbitration_sn`, `status`, `reason`, `shop_reason`, `result`, `arbitrator`, `gmt_arbitration`, `gmt_accept`, `gmt_apply`, `gmt_reply`, `shop_id`, `aftersale_id`, `customer_id`)
VALUES  (NULL, 2, '商户不给退', '不是我家商品', NULL, '1', '2023-12-25 01:12:34', '2023-12-26 01:13:27', '2023-12-27 12:03:53', NULL, 2, 2, 3),
        (NULL, 2, '不给换', '超过七天无理由', NULL, '1', '2023-12-24 01:12:39', '2023-12-25 01:13:34', '2023-12-26 01:13:14', NULL, 2, 2, 3);

/*!40000 ALTER TABLE `arbitration` DISABLE KEYS */;
/*!40000 ALTER TABLE `arbitration` ENABLE KEYS */;
UNLOCK
TABLES;




--
-- Table structure for table `aftersale_express`
--

DROP TABLE IF EXISTS `aftersale_express`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aftersale_express`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `bill_code`    bigint  DEFAULT NULL,
    `aftersale_id` bigint  DEFAULT NULL,
    `sender`       tinyint DEFAULT NULL,
    `status`       bigint  DEFAULT NULL,
    `express_id`   bigint  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aftersale_express`
--

LOCK
TABLES `aftersale_express` WRITE;

INSERT INTO `aftersale_express`(`bill_code`, `aftersale_id`, `sender`, `status`, `express_id`)
VALUES  (100020003, 5, 0, 1, NULL),
        (100020004, 6, 0, 1, NULL);
/*!40000 ALTER TABLE `aftersale_express` DISABLE KEYS */;
/*!40000 ALTER TABLE `aftersale_express` ENABLE KEYS */;
UNLOCK
TABLES;



