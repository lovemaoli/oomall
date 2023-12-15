FROM ubuntu:20.04
MAINTAINER mingqiu mingqiu@xmu.edu.cn
WORKDIR /app

# 设置环境变量
ENV TZ Asia/Shanghai
ENV JDK_HOME /opt/jdk
ENV PATH $JDK_HOME/bin:$PATH

# 安装JDK17
RUN apt-get update
RUN apt-get install -y wget
RUN wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.tar.gz
RUN tar -xzf jdk-17_linux-x64_bin.tar.gz -C /opt
RUN rm -f jdk-17_linux-x64_bin.tar.gz
RUN mv /opt/jdk-17.0.9 /opt/jdk

# 配置Java环境变量
RUN echo "export JAVA_HOME=$JDK_HOME" >> /etc/profile && \
    echo "export PATH=$JDK_HOME/bin:$PATH" >> /etc/profile

#ARG JAR_FILE
#ADD ${JAR_FILE} /app/app.jar


COPY ./gateway/target/gateway-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]