FROM openjdk:8-jdk-alpine
RUN mkdir /opt/snappbox-phonebook
COPY build/libs/*.jar /opt/snappbox-phonebook/app.jar
WORKDIR /opt/snappbox-phonebook
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.datasource.url=jdbc:mysql://snappbox-mysql:3306/phonebook?createDatabaseIfNotExist=true" ,"--spring.datasource.username=root", "--spring.datasource.password=root"]