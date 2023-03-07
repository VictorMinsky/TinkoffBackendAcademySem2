# syntax=docker/dockerfile:experimental
FROM gradle:7.5.1-jdk17-alpine AS build
# First stage of our container.
# In this stage we build .jar file and
# unpack it in order to get plain .class files

# Set current working dirictory
WORKDIR /workspace/app

# Copy contents of our app from local to container
COPY . /workspace/app
# Install required lib for grpc, build our project with gradle
# Unpack .jar file so only required .class will be used
RUN --mount=type=cache,target=/root/.gradle apk add gcompat && \
    ./gradlew clean build -x test && \
    mkdir -p build/dependency && \
    (cd build/dependency; jar -xf ../libs/*-SNAPSHOT.jar)

FROM gradle:7.5.1-jdk17-alpine
# Second stage of our container.
# In this stage we copy plaint .class files
# And start our app

# Recomendation from Spring to use /tmp volume for Tomcat
VOLUME /tmp

# Common path where our .jar file was unpacked
ARG DEPENDENCY=/workspace/app/build/dependency
# Copy only required .class files
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
# Set the workdir
WORKDIR /
# Start our application
ENTRYPOINT ["java","-cp","app:app/lib/*","com.tinkoff.handyman.HandymanApplication"]