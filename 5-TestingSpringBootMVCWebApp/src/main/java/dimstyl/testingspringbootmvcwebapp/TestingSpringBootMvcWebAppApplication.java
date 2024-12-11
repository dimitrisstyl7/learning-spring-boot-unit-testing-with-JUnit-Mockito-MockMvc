package dimstyl.testingspringbootmvcwebapp;

import dimstyl.testingspringbootmvcwebapp.properties.StudentProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StudentProperties.class)
public class TestingSpringBootMvcWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestingSpringBootMvcWebAppApplication.class, args);
    }

}
