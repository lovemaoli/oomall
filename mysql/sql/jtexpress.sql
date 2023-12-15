GRANT ALL ON jtexpress.* TO 'demouser'@'localhost';
GRANT ALL ON jtexpress.* TO 'demouser'@'%';

USE jtexpress;

--
-- Table structure for table `api_account`
--

DROP TABLE IF EXISTS `jtexpress_api_account`;
CREATE TABLE `jtexpress_api_account`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT,
    `account`     bigint      NOT NULL UNIQUE,
    `private_key` varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX index_account (account)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1207
  DEFAULT
      CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

LOCK TABLES `jtexpress_api_account` WRITE;
INSERT INTO `jtexpress_api_account`
VALUES (1, 177855, 'OOMALL123');
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `jtexpress_customer`;
CREATE TABLE `jtexpress_customer`
(
    `id`       bigint      NOT NULL AUTO_INCREMENT,
    `code`     varchar(30) NOT NULL,
    `password` varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX index_code (code)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7021
  DEFAULT
      CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

LOCK TABLES `jtexpress_customer` WRITE;
INSERT INTO jtexpress.jtexpress_customer (id, code, password)
VALUES (1, 'J33331', 'OOMALLEXPRESS1');
INSERT INTO jtexpress.jtexpress_customer (id, code, password)
VALUES (7021, 'J0086474299', '1111');
UNLOCK TABLES;

--
-- Table structure for table `person_info`
--

DROP TABLE IF EXISTS `jtexpress_person_info`;
CREATE TABLE `jtexpress_person_info`
(
    `id`           BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name`         VARCHAR(32)  NOT NULL,
    `company`      VARCHAR(100),
    `post_code`    VARCHAR(32),
    `mail_box`     VARCHAR(150),
    `mobile`       VARCHAR(30),
    `phone`        VARCHAR(30),
    `country_code` VARCHAR(20)  NOT NULL,
    `prov`         VARCHAR(32)  NOT NULL,
    `city`         VARCHAR(32)  NOT NULL,
    `area`         VARCHAR(32)  NOT NULL,
    `town`         VARCHAR(32),
    `street`       VARCHAR(32),
    `address`      VARCHAR(150) NOT NULL,
    CHECK ((`mobile` IS NOT NULL) OR (`phone` IS NOT NULL))
) ENGINE = InnoDB
  AUTO_INCREMENT = 2273
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

LOCK TABLES `jtexpress_person_info` WRITE;
INSERT INTO jtexpress.jtexpress_person_info (id, name, company, post_code, mail_box, mobile, phone, country_code, prov,
                                             city, area, town, street, address)
VALUES (2283, '小九', null, null, null, '15546168286', '', 'CHN', '上海', '上海市', '青浦区', null, null,
        '庆丰三路28号');
INSERT INTO jtexpress.jtexpress_person_info (id, name, company, post_code, mail_box, mobile, phone, country_code, prov,
                                             city, area, town, street, address)
VALUES (2284, '田丽', null, null, null, '13766245825', '', 'CHN', '上海', '上海市', '嘉定区', null, null,
        '站前西路永利酒店斜对面童装店');
INSERT INTO jtexpress.jtexpress_person_info (id, name, company, post_code, mail_box, mobile, phone, country_code, prov,
                                             city, area, town, street, address)
VALUES (2335, '小九', null, null, null, '15546168286', '', 'CHN', '上海', '上海市', '青浦区', null, null,
        '庆丰三路28号');
INSERT INTO jtexpress.jtexpress_person_info (id, name, company, post_code, mail_box, mobile, phone, country_code, prov,
                                             city, area, town, street, address)
VALUES (2336, '田丽', null, null, null, '13766245825', '', 'CHN', '上海', '上海市', '嘉定区', null, null,
        '站前西路永利酒店斜对面童装店');
INSERT INTO jtexpress.jtexpress_person_info (id, name, company, post_code, mail_box, mobile, phone, country_code, prov,
                                             city, area, town, street, address)
VALUES (2347, '小九', null, null, null, '15546168286', '', 'CHN', '上海', '上海市', '青浦区', null, null,
        '庆丰三路28号');
INSERT INTO jtexpress.jtexpress_person_info (id, name, company, post_code, mail_box, mobile, phone, country_code, prov,
                                             city, area, town, street, address)
VALUES (2348, '田丽', null, null, null, '13766245825', '', 'CHN', '上海', '上海市', '嘉定区', null, null,
        '站前西路永利酒店斜对面童装店');
INSERT INTO jtexpress.jtexpress_person_info (id, name, company, post_code, mail_box, mobile, phone, country_code, prov,
                                             city, area, town, street, address)
VALUES (2349, '小九', null, null, null, '15546168286', '', 'CHN', '上海', '上海市', '青浦区', null, null,
        '庆丰三路28号');
INSERT INTO jtexpress.jtexpress_person_info (id, name, company, post_code, mail_box, mobile, phone, country_code, prov,
                                             city, area, town, street, address)
VALUES (2350, '田丽', null, null, null, '13766245825', '', 'CHN', '上海', '上海市', '嘉定区', null, null,
        '站前西路永利酒店斜对面童装店');
UNLOCK TABLES;


--
-- Table structure for table `order`
--
DROP TABLE IF EXISTS `jtexpress_order`;
CREATE TABLE `jtexpress_order`
(
    `id`                     BIGINT PRIMARY KEY AUTO_INCREMENT,
    `customer_code`          varchar(30) NOT NULL,
    `network`                VARCHAR(30),
    `tx_logistic_id`         VARCHAR(50) NOT NULL UNIQUE,
    `express_type`           VARCHAR(30) NOT NULL,
    `order_type`             VARCHAR(11) NOT NULL,
    `service_type`           VARCHAR(30) NOT NULL,
    `delivery_type`          VARCHAR(30) NOT NULL,
    `pay_type`               VARCHAR(30) NOT NULL,
    `sender_id`              BIGINT      NOT NULL,
    `receiver_id`            BIGINT      NOT NULL,
    `send_start_time`        VARCHAR(30),
    `send_end_time`          VARCHAR(30),
    `goods_type`             VARCHAR(30) NOT NULL,
    `is_real_name`           BOOLEAN,
    `is_customs_declaration` BOOLEAN,
    `length`                 INT(6),
    `width`                  INT(6),
    `height`                 INT(6),
    `weight`                 VARCHAR(12) NOT NULL,
    `total_quantity`         INT(4),
    `items_value`            VARCHAR(12),
    `price_currency`         VARCHAR(32),
    `offer_fee`              VARCHAR(12),
    `remark`                 VARCHAR(200),
    `post_site_code`         VARCHAR(20),
    `post_site_name`         VARCHAR(100),
    `post_site_address`      VARCHAR(200),
    `bill_code`              VARCHAR(50) NOT NULL UNIQUE,
    `order_status`           TINYINT     NOT NULL,
    INDEX index_tx_logistic_id (`tx_logistic_id`),
    INDEX index_bill_code (`bill_code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2272
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

LOCK TABLES `jtexpress_order` WRITE;
INSERT INTO jtexpress.jtexpress_order (id, customer_code, network, tx_logistic_id, express_type, order_type,
                                       service_type, delivery_type, pay_type, sender_id, receiver_id, send_start_time,
                                       send_end_time, goods_type, is_real_name, is_customs_declaration, length, width,
                                       height, weight, total_quantity, items_value, price_currency, offer_fee, remark,
                                       post_site_code, post_site_name, post_site_address, bill_code, order_status)
VALUES (2272, 'J0086474299', null, 'TEST0000000000', 'EZ', '1', '01', '06', 'CC_CASH', 2283, 2284,
        '2023-07-04 22:00:00', '2025-07-04 18:00:00', 'bm000006', null, null, 0, 0, 0, '0.02', 0, null, null, null,
        null, null, null, null, 'JT108912787043904043991', 0);
INSERT INTO jtexpress.jtexpress_order (id, customer_code, network, tx_logistic_id, express_type, order_type,
                                       service_type, delivery_type, pay_type, sender_id, receiver_id, send_start_time,
                                       send_end_time, goods_type, is_real_name, is_customs_declaration, length, width,
                                       height, weight, total_quantity, items_value, price_currency, offer_fee, remark,
                                       post_site_code, post_site_name, post_site_address, bill_code, order_status)
VALUES (2298, 'J0086474299', null, 'TEST1111111111', 'EZ', '1', '01', '06', 'CC_CASH', 2335, 2336,
        '2023-07-04 22:00:00', '2023-07-04 23:00:00', 'bm000006', null, null, 0, 0, 0, '0.02', 0, null, null, null,
        null, null, null, null, 'JT1742686983284736054025', 1);
INSERT INTO jtexpress.jtexpress_order (id, customer_code, network, tx_logistic_id, express_type, order_type,
                                       service_type, delivery_type, pay_type, sender_id, receiver_id, send_start_time,
                                       send_end_time, goods_type, is_real_name, is_customs_declaration, length, width,
                                       height, weight, total_quantity, items_value, price_currency, offer_fee, remark,
                                       post_site_code, post_site_name, post_site_address, bill_code, order_status)
VALUES (2304, 'J0086474299', null, 'TEST2222222222', 'EZ', '1', '01', '06', 'CC_CASH', 2347, 2348,
        '2023-07-04 22:00:00', '2023-07-04 23:00:00', 'bm000006', null, null, 0, 0, 0, '0.02', 0, null, null, null,
        null, null, null, null, 'JT871345108962304099632', 2);
INSERT INTO jtexpress.jtexpress_order (id, customer_code, network, tx_logistic_id, express_type, order_type,
                                       service_type, delivery_type, pay_type, sender_id, receiver_id, send_start_time,
                                       send_end_time, goods_type, is_real_name, is_customs_declaration, length, width,
                                       height, weight, total_quantity, items_value, price_currency, offer_fee, remark,
                                       post_site_code, post_site_name, post_site_address, bill_code, order_status)
VALUES (2305, 'J0086474299', null, 'TEST2022070421007086', 'EZ', '1', '01', '06', 'CC_CASH', 2349, 2350,
        '2023-07-04 22:00:00', '2023-07-04 23:00:00', 'bm000006', null, null, 0, 0, 0, '0.02', 0, null, null, null,
        null, null, null, null, 'JT6970761327153152041421', 1);
UNLOCK TABLES;

--
-- Table structure for table `item`
--
DROP TABLE IF EXISTS `jtexpress_item`;
CREATE TABLE `jtexpress_item`
(
    `id`             BIGINT PRIMARY KEY AUTO_INCREMENT,
    `tx_logistic_id` VARCHAR(50) NOT NULL,
    `item_type`      VARCHAR(30),
    `item_name`      VARCHAR(30),
    `chinese_name`   VARCHAR(60),
    `english_name`   VARCHAR(60),
    `number`         INT(4),
    `item_value`     VARCHAR(20),
    `price_currency` VARCHAR(20) DEFAULT 'RMB',
    `desc`           VARCHAR(100),
    `item_url`       VARCHAR(100),
    INDEX index_tx_logistic_id (`tx_logistic_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 233
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Table structure for table `trace_detail`
--

DROP TABLE IF EXISTS `jtexpress_trace_detail`;
CREATE TABLE `jtexpress_trace_detail`
(
    `id`                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    `bill_code`           VARCHAR(50)  NOT NULL,
    `scan_time`           VARCHAR(255) NOT NULL,
    `desc`                VARCHAR(255),
    `scan_type`           VARCHAR(255) NOT NULL,
    `problem_type`        VARCHAR(255),
    `staff_name`          VARCHAR(255),
    `staff_contact`       VARCHAR(255),
    `scan_network_id`     BIGINT,
    `next_network_id`     BIGINT,
    `reback_status`       INT,
    `network_type`        VARCHAR(255),
    `sign_by_others_type` VARCHAR(255),
    `sign_by_others_name` VARCHAR(255),
    `sign_by_others_tel`  VARCHAR(255),
    `pick_code`           VARCHAR(255),
    UNIQUE KEY `unique_trace_detail_scan_time_type` (`bill_code`, `scan_time`, `scan_type`),
    INDEX index_bill_code (`bill_code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 233
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

LOCK TABLES `jtexpress_trace_detail` WRITE;
INSERT INTO jtexpress.jtexpress_trace_detail (id, bill_code, scan_time, `desc`, scan_type, problem_type, staff_name, staff_contact, scan_network_id, next_network_id, reback_status, network_type, sign_by_others_type, sign_by_others_name, sign_by_others_tel, pick_code) VALUES (235, 'JT1742686983284736054025', '2023-12-06 14:05:57.128815', null, '快件揽收', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO jtexpress.jtexpress_trace_detail (id, bill_code, scan_time, `desc`, scan_type, problem_type, staff_name, staff_contact, scan_network_id, next_network_id, reback_status, network_type, sign_by_others_type, sign_by_others_name, sign_by_others_tel, pick_code) VALUES (236, 'JT1742686983284736054025', '2025-12-06 14:06:07.128815', null, '快件签收', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO jtexpress.jtexpress_trace_detail (id, bill_code, scan_time, `desc`, scan_type, problem_type, staff_name, staff_contact, scan_network_id, next_network_id, reback_status, network_type, sign_by_others_type, sign_by_others_name, sign_by_others_tel, pick_code) VALUES (241, 'JT871345108962304099632', '2023-12-06 14:58:35.964072', null, '快件揽收', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO jtexpress.jtexpress_trace_detail (id, bill_code, scan_time, `desc`, scan_type, problem_type, staff_name, staff_contact, scan_network_id, next_network_id, reback_status, network_type, sign_by_others_type, sign_by_others_name, sign_by_others_tel, pick_code) VALUES (242, 'JT871345108962304099632', '2023-12-06 14:58:45.964072', null, '快件签收', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO jtexpress.jtexpress_trace_detail (id, bill_code, scan_time, `desc`, scan_type, problem_type, staff_name, staff_contact, scan_network_id, next_network_id, reback_status, network_type, sign_by_others_type, sign_by_others_name, sign_by_others_tel, pick_code) VALUES (243, 'JT6970761327153152041421', '2023-12-06 15:00:32.155724', null, '快件揽收', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO jtexpress.jtexpress_trace_detail (id, bill_code, scan_time, `desc`, scan_type, problem_type, staff_name, staff_contact, scan_network_id, next_network_id, reback_status, network_type, sign_by_others_type, sign_by_others_name, sign_by_others_tel, pick_code) VALUES (244, 'JT6970761327153152041421', '2023-12-06 15:00:42.155724', null, '快件签收', null, null, null, null, null, null, null, null, null, null, null);
UNLOCK TABLES ;
--
-- Table structure for table `network`
--
DROP TABLE IF EXISTS `jtexpress_network`;
CREATE TABLE `jtexpress_network`
(
    `id`       BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name`     VARCHAR(255) NOT NULL,
    `contact`  VARCHAR(255) NOT NULL,
    `province` VARCHAR(255) NOT NULL,
    `city`     VARCHAR(255) NOT NULL,
    `area`     VARCHAR(255) NOT NULL
) ENGINE = InnoDB
  AUTO_INCREMENT = 233
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

