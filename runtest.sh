#!/bin/bash
## 将文件结尾从CRLF改为LF，解决了cd 错误问题
#$1为目录名
TIME=$(date "+%Y-%m-%d-%H-%M-%S")

cd /test/unit-test
if [ -d $TIME ];then
  rm -r $TIME
fi
mkdir $TIME

cd /OOMALL
git pull

cp pom.bak.xml pom.xml
mvn clean install surefire-report:report > /test/unit-test/$TIME/core.log
mvn jacoco:report
#docker exec redis redis-cli -a 123456 flushdb

cd core
cp -r target/site/jacoco /test/unit-test/$TIME/core
cp target/site/surefire-report.html /test/unit-test/$TIME/core-test.html
cp -r /OOMALL/site/images /test/unit-test/$TIME
cp -r /OOMALL/site/css /test/unit-test/$TIME

cd /OOMALL
git restore pom.xml

for M in 'payment' 'shop' 'product' 'region' 'sfexpress' 'freight' 'wechatpay'
do
  cd /OOMALL/$M
  mkdir /test/unit-test/$TIME/$M
  mvn clean surefire-report:report > /test/unit-test/$TIME/$M/$M.log
  mvn jacoco:report
  cp -r target/site/jacoco /test/unit-test/$TIME/$M
  cp target/site/surefire-report.html /test/unit-test/$TIME/$M-test.html
done

cd /test/unit-test

if [ -d latest ];then
  rm -r latest
fi

mkdir latest
cp -r  /test/unit-test/$TIME/* latest

