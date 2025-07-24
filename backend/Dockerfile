FROM eclipse-temurin:23-alpine AS builder
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN jar xf application.jar
# Analyze the dependencies contained into the fat jar
RUN jdeps --ignore-missing-deps -q  \
    --recursive  \
    --multi-release 23  \
    --print-module-deps  \
    --class-path 'BOOT-INF/lib/*'  \
    application.jar > deps.info

# Remove duplicates and format the module list
RUN cat deps.info | tr ',' '\n' | sort | uniq | tr '\n' ',' | sed 's/,$//' > modules.info

# Create the custom JRE
RUN jlink \
    --verbose \
    --add-modules $(cat modules.info) \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /custom-jre

FROM alpine:latest

RUN apk add --no-cache \
    gcompat \
    ca-certificates \
    && rm -rf /var/cache/apk/*

WORKDIR /app
# Copy the custom JRE and the application files
COPY --from=builder /custom-jre /custom-jre
COPY --from=builder /app/BOOT-INF /app/BOOT-INF
COPY --from=builder /app/META-INF /app/META-INF
COPY --from=builder /app/org /app/org

ENV PATH="/custom-jre/bin:${PATH}"
ENV JAVA_HOME="/custom-jre"

EXPOSE 8080
# Set JVM options for containers
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["/custom-jre/bin/java", "-cp", "BOOT-INF/classes:BOOT-INF/lib/*", "com.omega.jobportal.JobPortalApplication"]