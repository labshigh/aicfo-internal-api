FROM openjdk:8-jdk-alpine
LABEL maintainer="nogah@nftmoa.io"

ENV env local
ENV heapOpt "-Xms2048m -Xmx2048m"

EXPOSE 35200

# Timezone 보정
RUN apk add tzdata
RUN cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN echo "Asia/Seoul" > /etc/timezone
RUN apk add curl

ARG JAR_FILE=./build/libs/aicfo-internal-api-1.0.0.0.jar
ADD ${JAR_FILE} /aicfo-internal-api.jar

ENTRYPOINT java \
${heapOpt} \
-Djava.security.egd=file:/dev/./urandom \
-Dspring.profiles.active="${env}" \
-jar /aicfo-internal-api.jar