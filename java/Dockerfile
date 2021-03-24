FROM gradle:jdk11

WORKDIR /opt/java

COPY build.gradle /opt/java
COPY gradle.properties /opt/java

RUN gradle resolveDependencies

COPY . /opt/java

RUN gradle build

CMD ["gradle", "run"]
