FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine as builder
RUN apk add --no-cache bash curl zip

RUN curl -s "https://get.sdkman.io" | bash

SHELL ["/bin/bash", "-c"]

RUN source /root/.sdkman/bin/sdkman-init.sh; sdk install gradle 6.8.3
RUN source /root/.sdkman/bin/sdkman-init.sh; sdk install maven 3.6.3



FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine as dependencies
RUN apk add --no-cache bash wget

COPY --from=builder /root/.sdkman/candidates/gradle/current /usr/bin/gradle
COPY --from=builder /root/.sdkman/candidates/maven/current /usr/bin/maven

SHELL ["/bin/bash", "-c"]
ENV PATH="/usr/bin/maven/bin:/usr/bin/maven/:/usr/bin/gradle:/usr/bin/gradle/bin:${PATH}"

RUN mkdir -p /root/backend

ADD . /root/backend

WORKDIR /root/backend/

RUN gradle \
    clean \
    build
RUN gradle wrapper



FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine
RUN apk add --no-cache bash

COPY --from=dependencies /root/backend/gradle /root/backend/
COPY --from=dependencies /root/backend/gradlew /root/backend/gradlew
COPY --from=builder /root/.sdkman/candidates/maven/current /usr/bin/maven

SHELL ["/bin/bash", "-c"]
ENV PATH="/usr/bin/maven/bin:/usr/bin/maven/:/usr/bin/gradle:/usr/bin/gradle/bin:${PATH}"

COPY --from=dependencies /root/backend /root/backend

EXPOSE 8081

WORKDIR /root/backend/build/libs

ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]