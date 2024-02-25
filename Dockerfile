FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY /build/libs/spring-qrcode-generator.jar spring-qrcode-generator.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "spring-qrcode-generator.jar"]