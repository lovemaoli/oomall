CREATE USER 'demouser'@'localhost' IDENTIFIED BY '123456';
CREATE USER 'demouser'@'%' IDENTIFIED BY '123456';
GRANT PROCESS ON *.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS payment;
GRANT ALL ON payment.* TO 'demouser'@'localhost';
GRANT ALL ON payment.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS product;
GRANT ALL ON product.* TO 'demouser'@'localhost';
GRANT ALL ON product.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS shop;
GRANT ALL ON shop.* TO 'demouser'@'localhost';
GRANT ALL ON shop.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS freight;
GRANT ALL ON freight.* TO 'demouser'@'localhost';
GRANT ALL ON freight.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS aftersale;
GRANT ALL ON aftersale.* TO 'demouser'@'localhost';
GRANT ALL ON aftersale.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS privilege;
GRANT ALL ON privilege.* TO 'demouser'@'localhost';
GRANT ALL ON privilege.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS service;
GRANT ALL ON service.* TO 'demouser'@'localhost';
GRANT ALL ON service.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS prodorder;
GRANT ALL ON prodorder.* TO 'demouser'@'localhost';
GRANT ALL ON prodorder.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS customer;
GRANT ALL ON customer.* TO 'demouser'@'localhost';
GRANT ALL ON customer.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS region;
GRANT ALL ON region.* TO 'demouser'@'localhost';
GRANT ALL ON region.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS wechatpay;
GRANT ALL ON wechatpay.* TO 'demouser'@'localhost';
GRANT ALL ON wechatpay.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS alipay;
GRANT ALL ON alipay.* TO 'demouser'@'localhost';
GRANT ALL ON alipay.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS sfexpress;
GRANT ALL ON sfexpress.* TO 'demouser'@'localhost';
GRANT ALL ON sfexpress.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS jtexpress;
GRANT ALL ON jtexpress.* TO 'demouser'@'localhost';
GRANT ALL ON jtexpress.* TO 'demouser'@'%';

FLUSH PRIVILEGES;