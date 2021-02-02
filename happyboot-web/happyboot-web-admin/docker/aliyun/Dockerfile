FROM gmaslowski/jdk:8u172
ADD happyboot-web-admin-1.0.0.jar backend-api.jar
ENTRYPOINT ["java","-Dspring.profiles.active=aliyun","-Dserver.port=8080","-jar","/backend-api.jar"]