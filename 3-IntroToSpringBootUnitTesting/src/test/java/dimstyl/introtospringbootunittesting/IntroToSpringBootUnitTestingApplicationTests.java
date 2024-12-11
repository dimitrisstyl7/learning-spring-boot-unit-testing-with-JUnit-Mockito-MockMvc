package dimstyl.introtospringbootunittesting;

import dimstyl.introtospringbootunittesting.models.CollegeStudent;
import dimstyl.introtospringbootunittesting.models.StudentGrades;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.List;

@DisplayNameGeneration(CustomDisplayNameGenerator.ReplaceCamelCase.class)
@SpringBootTest
class IntroToSpringBootUnitTestingApplicationTests {

    private static int counter = 0;

    @Autowired
    private CollegeStudent student;

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${info.app.name}")
    private String appName;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @BeforeEach
    void beforeEach() {
        counter += 1;
        System.out.printf("Testing: %s which is %s Version: %s. Execution of the method %d%n",
                appName, appDescription, appVersion, counter);
        student.setFirstName("John");
        student.setLastName("Smith");
        student.setEmailAddress("johnSmith@email.com");
        StudentGrades studentGrades = new StudentGrades(List.of(100.0, 85.0, 76.5, 91.75));
        student.setStudentGrades(studentGrades);
    }

    @Test
    void calculateGradesSum_ShouldReturnExpectedSum() {
        double expectedValue = 353.25;
        double actualValue = student.getStudentGrades().calculateGradesSum();
        Assertions.assertEquals(expectedValue, actualValue, "failure - should return expected sum");
    }

    @Test
    void calculateGradesSum_ShouldNotReturnUnexpectedSum() {
        double unexpectedValue = 0;
        double actualValue = student.getStudentGrades().calculateGradesSum();
        Assertions.assertNotEquals(unexpectedValue, actualValue, "failure - should not return unexpected sum");
    }

    @Test
    void isGradeGreater_ShouldReturnTrue() {
        int gradeOne = 90, gradeTwo = 87;
        boolean condition = StudentGrades.isGradeGreater(gradeOne, gradeTwo);
        Assertions.assertTrue(condition, "failure - %d should be greater than %d".formatted(gradeOne, gradeTwo));
    }

    @Test
    void isGradeGreater_ShouldReturnFalse() {
        int gradeOne = 87, gradeTwo = 90;
        boolean condition = StudentGrades.isGradeGreater(gradeOne, gradeTwo);
        Assertions.assertFalse(condition, "failure - %d should be smaller than %d".formatted(gradeOne, gradeTwo));
    }

    @Test
    void gradesNotNull_ShouldReturnTrue() {
        Assertions.assertNotNull(student.getStudentGrades(), "failure - grades should not be null");
    }

    @Test
    void createStudentWithoutGrades_ShouldSetAttributesCorrectly() {
        CollegeStudent student = applicationContext.getBean(CollegeStudent.class);
        student.setFirstName("Alice");
        student.setLastName("White");
        student.setEmailAddress("aliceWWhite@email.com");
        Assertions.assertNotNull(student.getFirstName(), "failure - first name should not be null");
        Assertions.assertNotNull(student.getLastName(), "failure - last name should not be null");
        Assertions.assertNotNull(student.getEmailAddress(), "failure - email address should not be null");
        Assertions.assertNull(student.getStudentGrades(), "failure - studentGrades should be null");
    }

    @Test
    void verifyStudentsArePrototypes() {
        CollegeStudent unexpected = applicationContext.getBean(CollegeStudent.class);
        CollegeStudent actual = applicationContext.getBean(CollegeStudent.class);
        Assertions.assertNotSame(unexpected, actual, "failure - students should not be the same");
    }

    @Test
    void findGradePointAverage_ShouldReturnExpectedAverage() {
        double expectedValue = 88.31;
        double actualValue = student.getStudentGrades().findGradePointAverage();
        Assertions.assertEquals(expectedValue, actualValue, "failure - should return expected average");
    }

}
