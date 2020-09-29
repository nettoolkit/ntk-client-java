# ntk-client-java
[![Maven Central](https://img.shields.io/maven-central/v/com.nettoolkit/ntk-client-java.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.nettoolkit%22%20AND%20a:%22ntk-client-java%22)

Official Java client for the [NetToolKit](https://www.nettoolkit.com) web API.

## Installation

### Requirements

Java 11 or later. If you'd like to use the client with an earlier Java version, [please let us know.](https://www.nettoolkit.com/contact)

### Maven

```xml
<dependency>
  <groupId>com.nettoolkit</groupId>
  <artifactId>ntk-client-java</artifactId>
  <version>1.0.0-beta</version>
</dependency>
```

## Documentation

See the [web API docs](https://www.nettoolkit.com/docs/overview) for a general explanation.

## Usage

Example.java
```java
import com.nettoolkit.gatekeeper.GatekeeperClient;
import com.nettoolkit.gatekeeper.Visit;
import com.nettoolkit.exception.NetToolKitException;

public class Example {
    public static void main() throws Exception {
        String apiKey = "NTK_API_KEY";
        try {
            GatekeeperClient gatekeeperClient = new GatekeeperClient(apiKey);
            Visit visit = gatekeeperClient.newAuthorizeVisitRequest()
                .ip("1.2.3.4")
                .url("https://example.com/hello")
                .send();
            System.out.println(visit);
        } catch (NetToolKitException ntke) {
            ntke.printStackTrace();
        }
    }
}
```

