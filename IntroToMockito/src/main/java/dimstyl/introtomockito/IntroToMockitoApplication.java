package dimstyl.introtomockito;

import dimstyl.introtomockito.dao.ApplicationDao;
import dimstyl.introtomockito.models.CollegeStudent;
import dimstyl.introtomockito.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class IntroToMockitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntroToMockitoApplication.class, args);
    }

    @Bean
    @Scope(value = "prototype")
    CollegeStudent getCollegeStudent() {
        return new CollegeStudent();
    }

    @Bean
    ApplicationDao getApplicationDao() {
        return new ApplicationDao();
    }

    @Bean
    ApplicationService getApplicationService() {
        return new ApplicationService();
    }

}
