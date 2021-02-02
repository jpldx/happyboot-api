FROM 10.10.0.14:5000/gmaslowski/jdk:8u172
LABEL maintainer="cxd@ltit.info"
ADD happyboot-mq-consumer/target/happyboot-mq-consumer-1.0.0.jar /api.jar
ENTRYPOINT ["java","-Dserver.port=8080","-jar","/api.jar"]