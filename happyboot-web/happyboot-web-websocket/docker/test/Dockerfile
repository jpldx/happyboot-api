FROM 10.10.0.14:5000/gmaslowski/jdk:8u172
ADD ltit-cloud-web/ltit-cloud-web-admin/target/ltit-cloud-web-admin-1.0-SNAPSHOT.jar backend-api.jar
ENTRYPOINT ["java","-Dspring.profiles.active=test","-Dserver.port=8080","-jar","/backend-api.jar"]