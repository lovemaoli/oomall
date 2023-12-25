-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: mysql    Database: service
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
-- Table structure for table `service_order`
--

DROP TABLE IF EXISTS `service_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_order`
(
    `id`                      bigint NOT NULL AUTO_INCREMENT,
    `type`                    tinyint                                 DEFAULT NULL,
    `address`                 varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `consignee`               varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `mobile`                  varchar(32) COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `service_sn`              bigint                                  DEFAULT NULL,
    `status`                  bigint                                  DEFAULT NULL,
    `description`             varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `service_provider_name`   varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `service_provider_mobile` varchar(32) COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `shop_id`                 bigint                                  DEFAULT NULL,
    `customer_id`             bigint                                  DEFAULT NULL,
    `service_provider_id`     bigint                                  DEFAULT NULL,
    `service_id`              bigint                                  DEFAULT NULL,
    `order_item_id`           bigint                                  DEFAULT NULL,
    `product_id`              bigint                                  DEFAULT NULL,
    `product_item_id`         bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_order`
--

-- LOCK
-- TABLES `service_order` WRITE;
/*!40000 ALTER TABLE `service_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_order` ENABLE KEYS */;



INSERT INTO `service_order`(`type`, `address`, `consignee`, `mobile`, `service_sn`, `status`, `description`,
                                      `service_provider_name`, `service_provider_mobile`, `shop_id`, `customer_id`,
                                      `service_provider_id`, `service_id`, `order_item_id`, `product_id`,
                                      `product_item_id`)
VALUES (1, '广东省深圳市福田区香蜜湖351号', '李四', '13878389456', NULL, 1, '佳威计算机维修', '张丽', '13787654321', 3,
        1, 4, 3, 1116, 2665, 2665),
       (1, '上海市浦东新区南京路789号', '王五', '15796254462', NULL, 2, '得生收音机维修', '王明', '13812345678', 5, 1,
        1, 1, 2834, 4383, 4383),
       (0, '福建省厦门市翔安区沙美街351号', '赵六', '16987456823', NULL, 2, '美的电磁炉维修', '王明', '13812345678', 8,
        2, 1, 1, 1237, 2786, 2786),
       (1, '上海市浦东新区南京路899号', '吴七', '18965324586', NULL, 0, '美的电磁炉', '', '', 3, 2, NULL, NULL, 1238,
        2787, 2787);

-- UNLOCK
-- TABLES;

--
-- Table structure for table `service_provider`
--

DROP TABLE IF EXISTS `service_provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_provider`
(
    `id`                bigint NOT NULL AUTO_INCREMENT,
    `name`              varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `consignee`         varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `address`           varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `mobile`            varchar(32) COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `service_max_num`   bigint                                  DEFAULT NULL,
    `deposit`           bigint                                  DEFAULT NULL,
    `deposit_threshold` bigint                                  DEFAULT NULL,
    `status`            bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_provider`
--

-- LOCK
-- TABLES `service_provider` WRITE;
/*!40000 ALTER TABLE `service_provider` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_provider` ENABLE KEYS */;


-- insert service_provider example data
INSERT INTO `service_provider` (`name`, `consignee`, `address`, `mobile`, `service_max_num`, `deposit`,`deposit_threshold`, `status`)
VALUES ('家电维修有限公司', '王明', '上海市浦东新区张江路789号', '13812345678', 10, 5000, 2000, 1),
       ('速修手机服务中心', '李静', '北京市朝阳区建国路456号', '13698765432', 15, 8000, 3000, 2),
       ('优质快递服务', '陈刚', '广东省广州市天河区天河路123号', '15865432109', 8, 3000, 1500, 1),
       ('电脑维修服务', '张丽', '广东省深圳市福田区香蜜湖321号', '13787654321', 12, 4000, 1800, 1),
       ('智能家居安装服务', '刘强', '四川省成都市锦江区锦华路555号', '15923456789', 20, 6000, 2500, 1),
       ('手机维修服务公司', '李明', '上海市静安区愚园路789号', '13987654321', 15, 5000, 2500, 0);

-- UNLOCK
-- TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service`
(
    `id`                  bigint NOT NULL AUTO_INCREMENT,
    `name`                varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `description`         varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `type`                tinyint                                 DEFAULT NULL,
    `service_area`        varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `category_name`       varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `service_provider_id` bigint                                  DEFAULT NULL,
    `status`              tinyint                                 DEFAULT NULL,
    `region_id`           bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

-- LOCK
-- TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
/*!40000 ALTER TABLE `service` ENABLE KEYS */;


INSERT INTO `service` (`name`, `description`, `type`, `service_area`, `category_name`, `service_provider_id`, `status`,
                       `region_id`)
VALUES ('家电维修', '提供家电设备的维修服务', 0, '上海市浦东新区', '家电维修', 1, 1, 165026),
       ('手机维修', '专业手机维修服务', 0, '北京市朝阳区', '手机维修', 2, 1, 480),
       ('电脑维修服务', '专业电脑维修服务', 0, '深圳市福田区', '电脑维修服务', 4, 1, 487849),
       ('家居安装', '智能家居设备的安装服务', 0, '成都市锦江区', '家居安装', 5, 1, 545535),
       ('空调维修', '提供空调设备的专业维修服务', 0, '上海市浦东新区', '家电维修', 1, 1, 165026),
       ('水管维修', '解决水管问题的维修服务', 0, '广州市天河区', '水管维修', 3, 1, 483979),
       ('智能家居配置', '配置智能家居设备的服务', 0, '成都市锦江区', '家居安装', 5, 1, 545535);
-- UNLOCK
-- TABLES;
--
-- Table structure for table `draft_service`
--

DROP TABLE IF EXISTS `draft_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `draft_service`
(
    `id`                  bigint NOT NULL AUTO_INCREMENT,
    `name`                varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `description`         varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `type`                tinyint                                 DEFAULT NULL,
    `status`              tinyint                                 DEFAULT NULL,
    `service_provider_id` bigint                                  DEFAULT NULL,
    `service_id`          bigint                                  DEFAULT NULL,
    `category_name`       varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `region_id`           bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `draft_service`
--

-- LOCK
-- TABLES `draft_service` WRITE;
/*!40000 ALTER TABLE `draft_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `draft_service` ENABLE KEYS */;


-- insert draft_service
INSERT INTO `draft_service` (`name`, `description`, `type`, `status`, `service_provider_id`, `category_name`,`region_id`)
VALUES ('华为手机维修服务', '提供华为手机维修服务', 0, 0, 6, '手机', 162505),
       ('联想电脑维修', '提供华为联想电脑维修服务', 0, 0, 2, '电脑', 480);

-- UNLOCK
-- TABLES;

--
-- Table structure for table `shop_service`
--

DROP TABLE IF EXISTS `shop_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_service`
(
    `id`                  bigint   NOT NULL AUTO_INCREMENT,
    `name`                varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `shop_id`             bigint                                  DEFAULT NULL,
    `service_id`          bigint                                  DEFAULT NULL,
    `create_time`         datetime NOT NULL                       DEFAULT CURRENT_TIMESTAMP,
    `shop_status`         bigint                                  DEFAULT NULL,
    `product_id`          bigint                                  DEFAULT NULL,
    `service_provider_id` bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_service`
--

-- LOCK
-- TABLES `shop_service` WRITE;
/*!40000 ALTER TABLE `shop_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `shop_service` ENABLE KEYS */;


INSERT INTO `shop_service` (`name`, `shop_id`, `service_id`, `create_time`, `shop_status`, `product_id`,`service_provider_id`)
VALUES ('佳威计算机深圳维修', 3, 3, '2023-12-25 23:03:53', 100, 2665, 4),
       ('得生收音机维修', 5, 1, '2023-12-25 15:25:28', 201, 4383, 1),
       ('美的电磁炉', 8, 1, '2023-12-25 23:30:08', 100, 2786, 1),
       ('美的电磁炉', 3, 1, '2023-12-25 23:30:08', 100, 2787, 1),
       ('美的电饭锅', 9, 1, '2023-12-25 23:34:49', 201, 2788, 1);

-- UNLOCK
-- TABLES;


--
-- Table structure for table `express`
--

DROP TABLE IF EXISTS `express`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `express`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `service_order_id` bigint  DEFAULT NULL,
    `bill_code`        bigint  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `express`
--

-- LOCK
-- TABLES `express` WRITE;
/*!40000 ALTER TABLE `express` DISABLE KEYS */;
/*!40000 ALTER TABLE `express` ENABLE KEYS */;
-- UNLOCK TABLES;




