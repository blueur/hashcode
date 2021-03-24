# Hash Code - Java

## Run

```bash
gradlew run
# or
gradle run
# or
gradle run -Papp=com.blueur.hashcode.traffic_signaling.TrafficSignalingApplication
```

## Configuration

1. Create new package in `src/main/java/com/blueur/hashcode`
2. Create a main class at the root of your package
3. Update `app` property in `gradle.properties`

## Docker

```bash
docker build -t blueur/hashcode:latest .

# linux
docker run -it --rm -v $(pwd)/../problem:/opt/problem blueur/hashcode:latest

# cmd
docker run -it --rm -v %cd%\..\problem:/opt/problem blueur/hashcode:latest

# PowerShell
docker run -it --rm -v ${PWD}\..\problem:/opt/problem blueur/hashcode:latest
```
