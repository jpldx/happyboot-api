FROM 10.10.0.14:5000/gmaslowski/jdk:8u172
LABEL maintainer="cxd@ltit.info"
ADD happyboot-web/happyboot-web-admin/target/happyboot-web-admin-1.0.0.jar backend-api.jar
ENTRYPOINT ["java","-Dspring.profiles.active=test","-Dserver.port=8080","-jar","/backend-api.jar"]