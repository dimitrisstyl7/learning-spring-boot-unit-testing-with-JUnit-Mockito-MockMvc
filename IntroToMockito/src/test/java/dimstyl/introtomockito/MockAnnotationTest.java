package dimstyl.introtomockito;

import dimstyl.introtomockito.dao.ApplicationDao;
import dimstyl.introtomockito.models.CollegeStudent;
import dimstyl.introtomockito.models.StudentGrades;
import dimstyl.introtomockito.service.ApplicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

@DisplayNameGeneration(CustomDisplayNameGenerator.ReplaceCamelCase.class)
@SpringBootTest
class MockAnnotationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CollegeStudent student;

    @Autowired
    private StudentGrades studentGrades;

    //    @Mock
    @MockitoBean
    private ApplicationDao applicationDao;

    //    @InjectMocks
    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    void setUp() {
        student.setFirstName("John");
        student.setLastName("Smith");
        student.setEmailAddress("johnSmith@email.com");
        student.setStudentGrades(studentGrades);
    }

    @Test
    void calculateGradesSum_ShouldReturnExpectedSum() {
        List<Double> grades = student.getStudentGrades().getGrades();
        Mockito.when(applicationDao.calculateGradesSum(grades)).thenReturn(100.0);

        double expectedValue = 100;
        double actualValue = applicationService.calculateGradesSum(grades);
        Assertions.assertEquals(expectedValue, actualValue,
                "failure - expected value %f, actual value %f".formatted(expectedValue, actualValue));

        Mockito.verify(applicationDao).calculateGradesSum(grades);
//        Mockito.verify(applicationDao, Mockito.times(1)).calculateGradesSum(grades);
    }

    @Test
    void findGradePointAverage_ShouldReturnExpectedAverage() {
        List<Double> grades = student.getStudentGrades().getGrades();
        Mockito.when(applicationDao.findGradePointAverage(grades)).thenReturn(88.31);

        double expectedValue = 88.31;
        double actualValue = applicationService.findGradePointAverage(grades);

        Assertions.assertEquals(expectedValue, actualValue,
                "failure - expected value %f, actual value %f".formatted(expectedValue, actualValue));
    }

    @Test
    void gradesNotNull_ShouldReturnTrue() {
        List<Double> grades = student.getStudentGrades().getGrades();
        Mockito.when(applicationDao.checkNull(grades)).thenReturn(true);
        Assertions.assertNotNull(applicationService.checkNull(grades), "failure - grades should not be null");
    }

    @Test
    void checkNull_ShouldThrowRuntimeException_WhenStudentIsNull() {
        CollegeStudent nullStudent = applicationContext.getBean(CollegeStudent.class);
        Mockito.doThrow(RuntimeException.class).when(applicationDao).checkNull(nullStudent);
        Assertions.assertThrows(RuntimeException.class, () -> applicationService.checkNull(nullStudent));
        Mockito.verify(applicationDao).checkNull(nullStudent);
    }

    @Test
    void checkNull_ShouldThrowRuntimeExceptionOnFirstCall_ThenReturnStringOnSecondCall() {
        CollegeStudent nullStudent = applicationContext.getBean(CollegeStudent.class);
        String stringValue = "Do not throw exception second time";

        Mockito.when(applicationDao.checkNull(nullStudent))
                .thenThrow(RuntimeException.class)
                .thenReturn(stringValue);

        Assertions.assertThrows(RuntimeException.class, () -> applicationService.checkNull(nullStudent));
        Assertions.assertEquals(stringValue, applicationService.checkNull(nullStudent));

        Mockito.verify(applicationDao, Mockito.times(2)).checkNull(nullStudent);
    }

}
