
-- auto Generated on 2023-12-04
DROP TABLE IF EXISTS `wechatpay_div_pay_trans`;

CREATE TABLE `wechatpay_div_pay_trans`(
	`id` bigint NOT NULL AUTO_INCREMENT,
	`appid` VARCHAR (32) COMMENT 'appid',
	`sub_mchid` VARCHAR (32) COMMENT 'subMchid',
	`transaction_id` VARCHAR (32) COMMENT 'transactionId',
	`out_order_no` VARCHAR (32) COMMENT 'outOrderNo',
	`order_id` VARCHAR (32) UNIQUE COMMENT 'start with "O"',
	`state` VARCHAR (32) COMMENT 'state',
	`success_time` DATETIME COMMENT 'successTime',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'wechatpay_div_pay_trans';

-- auto Generated on 2023-12-04
DROP TABLE IF EXISTS `wechatpay_div_receiver`;

CREATE TABLE `wechatpay_div_receiver`(
	`id` bigint NOT NULL AUTO_INCREMENT,
	`amount` INT (11) COMMENT 'amount',
	`description` VARCHAR (32) COMMENT 'description',
    `order_id` VARCHAR (32),
	`type` VARCHAR (32) COMMENT 'type',
	`account` VARCHAR (32) COMMENT 'account',
	`result` VARCHAR (32) COMMENT 'result',
	`fail_reason` VARCHAR (32) COMMENT 'failReason',
	`create_time` DATETIME COMMENT 'createTime',
	`finish_time` DATETIME COMMENT 'finishTime',
	`detail_id` VARCHAR (32) UNIQUE COMMENT 'start with "D"',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'wechatpay_div_receiver';

-- auto Generated on 2023-12-04
DROP TABLE IF EXISTS `wechatpay_div_refund_trans`;

CREATE TABLE `wechatpay_div_refund_trans`(
	`id` bigint NOT NULL AUTO_INCREMENT,
	`sub_mchid` VARCHAR (32) COMMENT 'subMchid',
	`order_id` VARCHAR (32) COMMENT 'orderId',
	`out_order_no` VARCHAR (32) COMMENT 'outOrderNo',
	`out_return_no` VARCHAR (32) COMMENT 'outReturnNo',
	`return_mchid` VARCHAR (32) COMMENT 'returnMchid',
	`amount` INT (11) COMMENT 'amount',
	`description` VARCHAR (32) COMMENT 'description',
	`return_id` VARCHAR (32) UNIQUE COMMENT 'start with "RT"',
	`result` VARCHAR (32) COMMENT 'result',
	`create_time` DATETIME COMMENT 'createTime',
	`finish_time` DATETIME COMMENT 'finishTime',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'wechatpay_div_refund_trans';

-- auto Generated on 2023-12-04
DROP TABLE IF EXISTS `wechatpay_pay_trans`;

CREATE TABLE `wechatpay_pay_trans`(
	`id` bigint NOT NULL AUTO_INCREMENT,
	`sp_appid` VARCHAR (32) COMMENT 'spAppid',
	`sp_mchid` VARCHAR (32) COMMENT 'spMchid',
	`sub_mchid` VARCHAR (32) COMMENT 'subMchid',
	`description` VARCHAR (32) COMMENT 'description',
	`out_trade_no` VARCHAR (32) COMMENT 'outTradeNo',
	`time_expire` DATETIME COMMENT 'timeExpire',
	`payer_sp_openid` VARCHAR (32) COMMENT 'payer_sp_openid',
	`amount_total` Integer COMMENT 'amount_total',
	`transaction_id` VARCHAR (32) UNIQUE COMMENT 'start with "T"',
	`trade_state` VARCHAR (32) COMMENT 'tradeState',
	`trade_state_desc` VARCHAR (32) COMMENT 'tradeStateDesc',
	`success_time` DATETIME COMMENT 'successTime',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'wechatpay_pay_trans';

-- auto Generated on 2023-12-04
DROP TABLE IF EXISTS `wechatpay_refund_trans`;

CREATE TABLE `wechatpay_refund_trans`(
	`id` bigint NOT NULL AUTO_INCREMENT,
	`sub_mchid` VARCHAR (32) COMMENT 'subMchid',
	`transaction_id` VARCHAR (32) COMMENT 'transactionId',
	`amount_total` Integer,
	`amount_payer_total` Integer,
	`amount_settlement_total` Integer,
	`amount_refund` Integer,
	`amount_payer_refund` Integer,
	`amount_settlement_refund` Integer,
	`amount_discount_refund` Integer,
	`out_refund_no` VARCHAR (32) COMMENT 'outRefundNo',
	`refund_id` VARCHAR (32) UNIQUE COMMENT 'start with "RF"',
	`out_trade_no` VARCHAR (32) COMMENT 'outTradeNo',
	`user_received_account` VARCHAR (32) COMMENT 'userReceivedAccount',
	`create_time` DATETIME COMMENT 'createTime',
	`status` VARCHAR (32) COMMENT 'status',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'wechatpay_refund_trans';


LOCK TABLES `wechatpay_pay_trans` WRITE;
INSERT INTO `wechatpay_pay_trans` VALUES (1, 'wx8888888888888888', '1230000109', '1900000109', 'OOMALL-福建厦带-QQ公仔', 'OOMALL12177525012', '2023-12-02 15:20:33', 'oUpF8uMuAJO_M2pxb1Q9zNjWeS6o', 100, 'T99732', 'SUCCESS', '支付成功', '2023-12-02 14:50:53');
INSERT INTO `wechatpay_pay_trans` VALUES (2, 'wx8888888888888888', '1230000109', '1900000109', 'OOMALL-北京厦带-QQ公仔', 'OOMALL23323338018', '2023-12-02 15:20:33', 'oUpF8uMuAJO_M2123459zNjWeS6o', 100, 'T93232', 'REFUND', '转入退款', '2023-12-02 14:50:53');
INSERT INTO `wechatpay_pay_trans` VALUES (3, 'wx8888888888888888', '1230000109', '1900000109', 'OOMALL-上海厦带-QQ公仔', 'OOMALL12132118018', '2023-12-02 15:20:33', 'oUpF8123AJO_M2pxb1Q9zNjWeS6o', 100, 'T91232', 'CLOSED', '支付已取消', '2023-12-02 14:50:53');
INSERT INTO `wechatpay_pay_trans` VALUES (4, 'wx8888888888888888', '1230000109', '1900000109', 'OOMALL-广州厦带-QQ公仔', 'OOMALL91321453318', '2023-12-02 15:20:33', 'oUpF1234AJO_M2pxb1Q9zNjWeS6o', 100, 'T94432', 'NOTPAY', '未支付', '2023-12-02 14:50:53');
INSERT INTO `wechatpay_pay_trans` VALUES (5, 'wx8888888888888888', '1230000109', '1900000109', 'OOMALL-南京厦带-QQ公仔', 'OOMALL91123453318', '2023-12-02 15:20:33', 'oUpF1234AJO_M2pxb1Q9zNjWeS6o', 1000, 'T95821', 'SUCCESS', '支付成功', '2023-12-02 14:50:53');
UNLOCK TABLES;


LOCK TABLES `wechatpay_refund_trans` WRITE;
INSERT INTO `wechatpay_refund_trans` VALUES (1, '1900000109', 'T93232', 100, 100, 100, 50, 50, 50, 0, 'OOMALL12153368018', 'RF33246', 'OOMALL23323338018', 'oUpF8uMuAJO_M2pxb1Q9zNjWeS6o', '2023-12-02 15:08:42', 'SUCCESS');
INSERT INTO `wechatpay_refund_trans` VALUES (2, '1900000109', 'T93232', 100, 100, 100, 20, 20, 20, 0, 'OOMALL12177336018', 'RF33346', 'OOMALL23323338018', 'oUpF8uMuAJO_M2pxb1Q9zNjWeS6o', '2023-12-02 15:18:42', 'ABNORMAL');
UNLOCK TABLES;

LOCK TABLES `wechatpay_div_pay_trans` WRITE;
INSERT INTO `wechatpay_div_pay_trans` VALUES (1, 'wx8888888888888888', '86693852', 'T99732', 'OOMALL20150812125046', 'O89769', 'FINISHIED', '2023-12-02 15:28:42');
UNLOCK TABLES;

LOCK TABLES `wechatpay_div_receiver` WRITE;
INSERT INTO `wechatpay_div_receiver` VALUES (1, 10, '分给OOMALL', 'O89769', 'MERCHANT_ID', '1900000109', 'SUCCESS', null, '2023-12-02 15:28:42', '2023-12-02 15:38:42', 'D12345');
INSERT INTO `wechatpay_div_receiver` VALUES (2, 10, '分给OOMALL', 'O89769', 'MERCHANT_ID', '1900000109', 'PENDING', null, '2023-12-02 15:18:42', null, 'D14445');
UNLOCK TABLES;

LOCK TABLES `wechatpay_div_refund_trans` WRITE;
INSERT INTO `wechatpay_div_refund_trans` VALUES (1, '86693852', 'O89769', 'OOMALL20150812125046', 'OOMALL20190516001', '1900000109', 10, '用户退款', 'RT99887', 'SUCCESS', '2023-05-20 13:29:35', '2023-05-20 13:59:35');
UNLOCK TABLES;




