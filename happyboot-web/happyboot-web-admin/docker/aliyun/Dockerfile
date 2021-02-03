FROM localhost:5000/jdk:8
LABEL maintainer="979309838@qq.com"
ADD happyboot-web-admin-1.0.0.jar /app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=aliyun","-Dserver.port=8080","-jar","/app.jar"]