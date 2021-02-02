FROM gmaslowski/jdk
# VOLUME /tmp
ADD ltit-cloud-web-admin-1.0-SNAPSHOT.jar backend-api.jar
ENTRYPOINT ["java","-Dspring.profiles.active=test","-Dserver.port=8080","-jar","/backend-api.jar"]