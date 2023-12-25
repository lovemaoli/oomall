CREATE USER IF NOT EXISTS 'demouser'@'localhost' IDENTIFIED BY '123456';
CREATE USER IF NOT EXISTS 'demouser'@'%' IDENTIFIED BY '123456';
GRANT PROCESS ON *.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS aftersale;
GRANT ALL ON aftersale.* TO 'demouser'@'localhost';
GRANT ALL ON aftersale.* TO 'demouser'@'%';

CREATE DATABASE IF NOT EXISTS service;
GRANT ALL ON service.* TO 'demouser'@'localhost';
GRANT ALL ON service.* TO 'demouser'@'%';

FLUSH PRIVILEGES;