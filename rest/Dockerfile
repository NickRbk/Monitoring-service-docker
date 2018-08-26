FROM maven:alpine
WORKDIR /app
COPY . .
RUN mvn install
EXPOSE 8080
CMD mvn spring-boot:run -Dspring.profiles.active=dev