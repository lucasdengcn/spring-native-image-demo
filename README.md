# Spring Native Image Demo

## Capabilities to Verify

| Component      | Able To Run? |
|----------------|--------------|
| JPA            | Yes          |
| Controllers    | Yes          |
| Flyway         | No           |
| Rest Client    |              |
| Redis Client   | Yes          |
| Kafka Client   | Yes          |
| Resilience4j   |              |
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


## Build Env

export JAVA_HOME="/Library/Java/JavaVirtualMachines/graalvm-jdk-21.0.5+9.1/Contents/Home"
export GRAALVM_HOME=$JAVA_HOME
export PATH=$GRAALVM_HOME/bin:$PATH

### Build and Run the Demo

```shell

gradle clean nativeCompile

# BUILD SUCCESSFUL in 5m 18s
# 11 actionable tasks: 11 executed

```

### Package size

~250M

## Running the Native Image

```shell

cd ~./build/native/nativeCompile

```


  


