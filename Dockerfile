FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY /build/libs/bot-0.0.1-SNAPSHOT.jar /app/bot.jar
ENTRYPOINT ["sh", "-c", "java -jar bot.jar --TELEGRAM_BOT_NAME=${TELEGRAM_BOT_NAME} --TELEGRAM_BOT_TOKEN=${TELEGRAM_BOT_TOKEN} --MIDDLE_URL=${MIDDLE_URL}"]