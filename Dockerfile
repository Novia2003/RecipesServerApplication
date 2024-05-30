FROM openjdk:17.0.2-jdk-slim-buster

WORKDIR /app

# Копируем файл с зависимостями в контейнер
COPY build/libs/recipesServerApplication-0.0.1-SNAPSHOT.jar /app/app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
