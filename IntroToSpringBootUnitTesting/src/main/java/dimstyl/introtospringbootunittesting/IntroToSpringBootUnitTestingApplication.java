package dimstyl.introtospringbootunittesting;

import dimstyl.introtospringbootunittesting.models.CollegeStudent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class IntroToSpringBootUnitTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntroToSpringBootUnitTestingApplication.class, args);
    }

    @Bean
    @Scope(value = "prototype")
    CollegeStudent getCollegeStudent() {
        return new CollegeStudent();
    }

}
