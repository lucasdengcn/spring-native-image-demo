# Spring Native Image Demo

## Tech Stack

- JDK 21
- Graalvm-jdk-21.0.5+9.1
- Kotlin 1.9.x
- Spring Boot 3.3.x
- Spring Cloud 2023.0.3
- Resilience4j
- Hibernate
- Spring Data JPA
- MapStruct
- Undertow
- OpenFeign
- CloudEvents
- Open API Specification
- PostgreSQL
- JUnit 5
- DataFaker
- Kafka
- Redis
- Gradle
- Kapt
- Gradle Native Build tools
- RFC-9457 

## Capabilities to Verify

| Component      | Able To Run? |
|----------------|--------------|
| JPA            | Yes          |
| Controllers    | Yes          |
| Flyway         | No           |
| OpenFeign      | Yes          |
| Redis Client   | Yes          |
| Kafka Client   | Yes          |
| Resilience4j   | Yes          |
| Map Struct     | Yes          |
| Swagger UI     | Yes          |
| Validation     | Yes          |
| Cacheable      | Yes          |
| Kafka Listener | Yes          |


## OTEL

https://opentelemetry.io/blog/2023/spring-native/
https://github.com/open-telemetry/opentelemetry-java-examples/tree/main/spring-native

## Toolchain

https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html

## Map Struct

given kotlin data class will generate few constructs, 
do not set default value for data class, otherwise MapStruct will not assign value to properties.

## Cache

https://spring.io/guides/gs/caching

## OpenFeign

https://docs.spring.io/spring-cloud-openfeign/reference/spring-cloud-openfeign.html#spring-cloud-feign

## Resilience4j



## Build Env

```shell
export JAVA_HOME="/Library/Java/JavaVirtualMachines/graalvm-jdk-21.0.5+9.1/Contents/Home"
export GRAALVM_HOME=$JAVA_HOME
export PATH=$GRAALVM_HOME/bin:$PATH
```

### Build and Run the Demo

```shell

gradle clean nativeCompile

# BUILD SUCCESSFUL in 5m 18s
# 11 actionable tasks: 11 executed

```

#### Runtime info

Package size: ~250M

Duration: Started DemoApplicationKt in 1.121 seconds (process running for 1.132)

#### Running the Native Image

```shell
./build/native/nativeCompile/spring-native-image-demo
```

#### Run Native directly

```shell
gradle clean nativeRun
```



