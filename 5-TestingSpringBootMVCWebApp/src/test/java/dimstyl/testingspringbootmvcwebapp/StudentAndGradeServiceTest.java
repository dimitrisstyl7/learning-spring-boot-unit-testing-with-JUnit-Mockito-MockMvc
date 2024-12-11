package dimstyl.testingspringbootmvcwebapp;

import dimstyl.testingspringbootmvcwebapp.enums.GradeType;
import dimstyl.testingspringbootmvcwebapp.models.*;
import dimstyl.testingspringbootmvcwebapp.properties.StudentProperties;
import dimstyl.testingspringbootmvcwebapp.service.StudentAndGradeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@SpringBootTest
@TestPropertySource("/application.yaml")
@DisplayNameGeneration(CustomDisplayNameGenerator.ReplaceCamelCase.class)
class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentAndGradeService studentAndGradeService;

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    @Autowired
    private StudentProperties student;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute(sqlAddStudent);
        jdbcTemplate.execute(sqlAddMathGrade);
        jdbcTemplate.execute(sqlAddScienceGrade);
        jdbcTemplate.execute(sqlAddHistoryGrade);
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);
    }

    @Test
    void createStudent_ShouldSaveStudentSuccessfully() {
        CollegeStudent innerStudent = new CollegeStudent("Alice", "White", "alice.white@email.com");
        studentAndGradeService.createStudent(innerStudent);
        innerStudent = studentAndGradeService.findByEmailAddress(innerStudent.getEmailAddress());
        Assertions.assertNotNull(innerStudent, "failure - student should not be null");
    }

    @Test
    void isStudentPresent_ShouldIndicatePresenceOrAbsenceCorrectly() {
        int id = student.getId();
        Assertions.assertTrue(studentAndGradeService.isStudentPresent(id), "failure - student with id %d should present".formatted(id));
        Assertions.assertFalse(studentAndGradeService.isStudentPresent(0), "failure - student with id 0 should not present");
    }

    @Test
    void deleteStudent_ShouldDeleteStudentSuccessfully() {
        int id = student.getId();

        CollegeStudent innerStudent = studentAndGradeService.findByEmailAddress(student.getEmailAddress());
        Assertions.assertNotNull(innerStudent, "failure - student should not be null");

        studentAndGradeService.deleteStudent(id);

        Optional<CollegeStudent> optionalStudent = studentAndGradeService.findStudentById(id);
        Assertions.assertFalse(optionalStudent.isPresent(), "failure - student should be null");

        studentAndGradeService.deleteAllGradesByStudentId(id);

        int expectedValue = 0;
        int actualValue = studentAndGradeService.findGradesByStudentId(id, GradeType.math).size();
        actualValue += studentAndGradeService.findGradesByStudentId(id, GradeType.history).size();
        actualValue += studentAndGradeService.findGradesByStudentId(id, GradeType.science).size();
        Assertions.assertEquals(expectedValue, actualValue, "failure - student with id %d should not have any grade".formatted(id));
    }

    @Test
    @Sql("/insert-test-data.sql")
    void getAllStudents_ShouldReturnAllStudents() {
        Iterable<CollegeStudent> students = studentAndGradeService.getAllStudents();
        long expectedValue = 5; // student + 4 records from insert-test-data.sql
        long actualValue = StreamSupport.stream(students.spliterator(), false).count();
        Assertions.assertEquals(expectedValue, actualValue, "failure - student count should be %d".formatted(expectedValue));
    }

    @Test
    void addGrade_ShouldReturnTrueIfSavedSuccessfully() {
        int expectedValue = 2, actualValue;
        List<? extends Grade> grades;
        Grade grade;

        grade = new MathGrade(student.getId(), 80.5);
        Assertions.assertTrue(studentAndGradeService.addGrade(grade));
        grades = studentAndGradeService.findGradesByStudentId(student.getId(), GradeType.math);
        actualValue = grades.size();
        Assertions.assertEquals(expectedValue, actualValue, "failure - math grade count should be %d".formatted(expectedValue));

        grade = new HistoryGrade(student.getId(), 80.5);
        Assertions.assertTrue(studentAndGradeService.addGrade(grade));
        grades = studentAndGradeService.findGradesByStudentId(student.getId(), GradeType.history);
        actualValue = grades.size();
        Assertions.assertEquals(expectedValue, actualValue, "failure - history grade count should be %d".formatted(expectedValue));

        grade = new ScienceGrade(student.getId(), 80.5);
        Assertions.assertTrue(studentAndGradeService.addGrade(grade));
        grades = studentAndGradeService.findGradesByStudentId(student.getId(), GradeType.science);
        actualValue = grades.size();
        Assertions.assertEquals(expectedValue, actualValue, "failure - science grade count should be %d".formatted(expectedValue));
    }

    @Test
    void addGrade_ShouldReturnFalseIfNotSavedSuccessfully() {
        int expectedValue = 1, actualValue;
        List<? extends Grade> grades;
        Grade grade;

        grade = new MathGrade(student.getId(), 1000);
        Assertions.assertFalse(studentAndGradeService.addGrade(grade));
        grades = studentAndGradeService.findGradesByStudentId(student.getId(), GradeType.math);
        actualValue = grades.size();
        Assertions.assertEquals(expectedValue, actualValue, "failure - math grade count should be %d".formatted(expectedValue));

        grade = new HistoryGrade(student.getId(), 1000);
        Assertions.assertFalse(studentAndGradeService.addGrade(grade));
        grades = studentAndGradeService.findGradesByStudentId(student.getId(), GradeType.history);
        actualValue = grades.size();
        Assertions.assertEquals(expectedValue, actualValue, "failure - history grade count should be %d".formatted(expectedValue));

        grade = new ScienceGrade(student.getId(), 1000);
        Assertions.assertFalse(studentAndGradeService.addGrade(grade));
        grades = studentAndGradeService.findGradesByStudentId(student.getId(), GradeType.science);
        actualValue = grades.size();
        Assertions.assertEquals(expectedValue, actualValue, "failure - science grade count should be %d".formatted(expectedValue));
    }

    @Test
    void deleteGrade_ShouldReturnTheStudentIdIfDeletedSuccessfully() {
        int expectedValue = student.getId();

        int gradeId = studentAndGradeService.findGradesByStudentId(expectedValue, GradeType.math).getFirst().getId();
        int actualValue = studentAndGradeService.deleteGrade(gradeId, GradeType.math);
        Assertions.assertEquals(expectedValue, actualValue, "failure - student id should be %d".formatted(expectedValue));

        gradeId = studentAndGradeService.findGradesByStudentId(expectedValue, GradeType.history).getFirst().getId();
        actualValue = studentAndGradeService.deleteGrade(gradeId, GradeType.history);
        Assertions.assertEquals(expectedValue, actualValue, "failure - student id should be %d".formatted(expectedValue));

        gradeId = studentAndGradeService.findGradesByStudentId(expectedValue, GradeType.science).getFirst().getId();
        actualValue = studentAndGradeService.deleteGrade(gradeId, GradeType.science);
        Assertions.assertEquals(expectedValue, actualValue, "failure - student id should be %d".formatted(expectedValue));
    }

    @Test
    void deleteGrade_ShouldReturnMinusOneIfNotDeletedSuccessfully() {
        int expectedValue = -1;
        int actualValue = studentAndGradeService.deleteGrade(1000, GradeType.math);
        Assertions.assertEquals(expectedValue, actualValue, "failure - student id should be %d".formatted(expectedValue));

        actualValue = studentAndGradeService.deleteGrade(1000, GradeType.history);
        Assertions.assertEquals(expectedValue, actualValue, "failure - student id should be %d".formatted(expectedValue));

        actualValue = studentAndGradeService.deleteGrade(1000, GradeType.science);
        Assertions.assertEquals(expectedValue, actualValue, "failure - student id should be %d".formatted(expectedValue));
    }

    @Test
    void getStudentInformation_ShouldReturnTheStudentInformationIfStudentExists() {
        String expectedValueStr, actualValueStr;
        int expectedValueInt, actualValueInt;

        GradebookCollegeStudent gradebookCollegeStudent = studentAndGradeService.getStudentInformation(student.getId());
        Assertions.assertNotNull(gradebookCollegeStudent, "failure - student information should not be null");

        expectedValueInt = student.getId();
        actualValueInt = gradebookCollegeStudent.getId();
        Assertions.assertEquals(expectedValueInt, actualValueInt, "failure - student id should be %d".formatted(expectedValueInt));

        expectedValueStr = student.getFirstName();
        actualValueStr = gradebookCollegeStudent.getFirstName();
        Assertions.assertEquals(expectedValueStr, actualValueStr, "failure - student first name should be %s".formatted(expectedValueStr));

        expectedValueStr = student.getLastName();
        actualValueStr = gradebookCollegeStudent.getLastName();
        Assertions.assertEquals(expectedValueStr, actualValueStr, "failure - student last name should be %s".formatted(expectedValueStr));

        expectedValueStr = student.getEmailAddress();
        actualValueStr = gradebookCollegeStudent.getEmailAddress();
        Assertions.assertEquals(expectedValueStr, actualValueStr, "failure - student email address should be %s".formatted(expectedValueStr));

        expectedValueInt = 1;
        actualValueInt = gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size();
        Assertions.assertEquals(expectedValueInt, actualValueInt, "failure - student math grade count should be %d".formatted(expectedValueInt));

        actualValueInt = gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size();
        Assertions.assertEquals(expectedValueInt, actualValueInt, "failure - student history grade count should be %d".formatted(expectedValueInt));

        actualValueInt = gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size();
        Assertions.assertEquals(expectedValueInt, actualValueInt, "failure - student science grade count should be %d".formatted(expectedValueInt));
    }

    @Test
    void getStudentInformation_ShouldReturnNullIfStudentDoesNotExist() {
        GradebookCollegeStudent gradebookCollegeStudent = studentAndGradeService.getStudentInformation(1000);
        Assertions.assertNull(gradebookCollegeStudent, "failure - student information should be null");
    }

}
