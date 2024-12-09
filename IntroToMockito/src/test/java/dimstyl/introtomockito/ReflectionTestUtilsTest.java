package dimstyl.introtomockito;

import dimstyl.introtomockito.models.CollegeStudent;
import dimstyl.introtomockito.models.StudentGrades;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

@DisplayNameGeneration(CustomDisplayNameGenerator.ReplaceCamelCase.class)
@SpringBootTest
class ReflectionTestUtilsTest {

    @Autowired
    private CollegeStudent student;

    @BeforeEach
    void setUp() {
        StudentGrades studentGrades = new StudentGrades(List.of(100.0, 85.0, 76.5, 91.75));
        ReflectionTestUtils.setField(student, "id", 1);
        ReflectionTestUtils.setField(student, "firstName", "John");
        ReflectionTestUtils.setField(student, "lastName", "Smith");
        ReflectionTestUtils.setField(student, "emailAddress", "john.smith@gmail.com");
        ReflectionTestUtils.setField(student, "studentGrades", studentGrades);
    }

    @Test
    void getPrivateField() {
        int expectedValue = 1;
        var actualValue = ReflectionTestUtils.getField(student, "id");
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void invokePrivateMethod() {
        String expectedValue = "John 1";
        String actualValue = ReflectionTestUtils.invokeMethod(student, "getFirstNameAndId");
        Assertions.assertEquals(expectedValue, actualValue);
    }

}
