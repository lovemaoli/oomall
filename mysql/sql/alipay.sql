LOCK TABLES `oomall_alipay_payment` WRITE;
/*!40000 ALTER TABLE `oomall_weight_freight` DISABLE KEYS */;
INSERT INTO `oomall_alipay_payment` (`id`, `out_trade_no`, `send_pay_date`, `total_amount`, `buyer_pay_amount`, `trade_status`) VALUES (20, '1', '2021-12-01 10:16:46', 100, 99, 0);
INSERT INTO `oomall_alipay_payment` (`id`, `out_trade_no`, `send_pay_date`, `total_amount`, `buyer_pay_amount`, `trade_status`) VALUES (21, '2', '2021-12-01 10:17:10', 100, 100, 1);
INSERT INTO `oomall_alipay_payment` (`id`, `out_trade_no`, `send_pay_date`, `total_amount`, `buyer_pay_amount`, `trade_status`) VALUES (22, '3', '2021-12-01 10:17:41', 100, 99, 2);
/*!40000 ALTER TABLE `oomall_weight_freight` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `oomall_alipay_refund` WRITE;
/*!40000 ALTER TABLE `oomall_piece_freight` DISABLE KEYS */;
INSERT INTO `oomall_alipay_refund` (`id`, `out_trade_no`, `out_request_no`, `total_amount`, `refund_amount`, `gmt_refund_pay`, `refund_status`) VALUES (1, '2', '1', 100, 90, '2021-12-01 10:56:17', 0);
/*!40000 ALTER TABLE `oomall_piece_freight` ENABLE KEYS */;
UNLOCK TABLES;