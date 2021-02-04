FROM gmaslowski/jdk:8u172
LABEL maintainer="979309838@qq.com"
ADD happyboot-web/happyboot-web-admin/target/*.jar /app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=aliyun","-Dserver.port=8080","-jar","/app.jar"]