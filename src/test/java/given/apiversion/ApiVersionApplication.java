package given.apiversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import given.apiversion.autoconfigure.EnableApiVersion;

@EnableApiVersion
@SpringBootApplication
public class ApiVersionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiVersionApplication.class, args);
    }
}
