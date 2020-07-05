FROM openjdk:13

ARG AES_KEY

COPY /build/libs/*.jar /app/pvault/app.jar
WORKDIR /app/pvault
ENTRYPOINT ["java", "-jar", "app.jar", "-DAES_KEY=${AES_KEY}"]
