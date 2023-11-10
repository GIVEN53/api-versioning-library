# api-versioning-library
![last-commit](https://img.shields.io/github/last-commit/GIVEN53/api-versioning-library?logo=github)
![last-release](https://img.shields.io/github/release-date/GIVEN53/api-versioning-library?logo=github&label=last%20release)
![release-version](https://img.shields.io/github/v/release/GIVEN53/api-versioning-library?logo=github&label=version)

The api-versioning Java library helps you manage the API version using Spring Boot projects.
The library automatically adds the version to the URI and provides a way to manage the version of the controller.

Supports JDK 17, Spring Boot 3.x
<br>

## Features
- Automatically appends the version to the URI through the `@ApiVersion` annotation.
- Allows customization of the URI prefix.
<br>

## Getting Started
### 1. Add the JitPack repository to your build file
Maven:
``` xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Gradle:
``` groovy
repositories {
    // ...
    maven { url 'https://jitpack.io' }
}
```

### 2. Add the dependency
Maven:
``` xml
<dependency>
    <groupId>com.github.GIVEN53</groupId>
    <artifactId>api-versioning-library</artifactId>
    <version>{version}</version>
</dependency>
```

Gradle:
``` groovy
dependencies {
        implementation 'com.github.GIVEN53:api-versioning-library:{version}'
}
```
> **Warning**
> you need to replace `{version}` with the latest version.

<br>

## Usage
### 1. `@EnableApiVersion` annotation on your Application class
``` java
@EnableApiVersion
@SpringBootApplication
public class FooApplication {
    public static void main(String[] args) {
        SpringApplication.run(FooApplication.class, args);
    }
}
```

### 2. `@ApiVersion` annotation on your Controller class or method
``` java
@ApiVersion("1")
@RestController
@RequestMapping("/foo")
public class FooController {

    @GetMapping
    public ResponseEntity<?> getFoo() {
        return ResponseEntity.ok("get v1");
    }

    @ApiVersion({"1.1", "1.2"})
    @PostMapping("/bar")
    public ResponseEntity<?> postBar() {
        return ResponseEntity.ok("post v1.1 or v1.2");
    }
}
```

### 3. Request
``` http
GET http://localhost:8080/v1/foo

POST http://localhost:8080/v1.1/foo/bar
POST http://localhost:8080/v1.2/foo/bar
```
<br>

## Setting properties
``` yml
api:
  version:
    uri-prefix: # The prefix of URI. Default is "". if you set "/api", the URI will be "/api/v1/..."
```
<br>

## Changelog
### 0.1.1
- Fixed build error

### 0.1.0
- Initial release
