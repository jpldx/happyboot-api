FROM 172.19.127.136:5000/jdk:8
LABEL maintainer="979309838@qq.com"
ADD happyboot-mq-consumer/target/*.jar /app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=aliyun","-Dserver.port=8080","-jar","/app.jar"]