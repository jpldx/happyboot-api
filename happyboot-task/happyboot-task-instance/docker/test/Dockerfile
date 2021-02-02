FROM 10.10.0.14:5000/gmaslowski/jdk:8u172
# VOLUME /tmp
ADD ltit-cloud-task/ltit-cloud-task-instance/target/ltit-cloud-task-instance-1.0-SNAPSHOT.jar xxl-job-executor.jar
ENTRYPOINT ["java","-Dspring.profiles.active=test","-Dserver.port=8080","-jar","/xxl-job-executor.jar"]