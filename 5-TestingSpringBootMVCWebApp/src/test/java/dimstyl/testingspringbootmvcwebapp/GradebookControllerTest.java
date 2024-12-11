package dimstyl.testingspringbootmvcwebapp;

import dimstyl.testingspringbootmvcwebapp.enums.GradeType;
import dimstyl.testingspringbootmvcwebapp.models.*;
import dimstyl.testingspringbootmvcwebapp.properties.StudentProperties;
import dimstyl.testingspringbootmvcwebapp.service.StudentAndGradeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.yaml")
@DisplayNameGeneration(CustomDisplayNameGenerator.ReplaceCamelCase.class)
class GradebookControllerTest {

    @Autowired
    private StudentProperties student;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentAndGradeService studentAndGradeService;

    @Test
    void getStudents_ShouldReturnIndexPageWithAllStudents() throws Exception {
        CollegeStudent studentOne =
                new GradebookCollegeStudent("Alice", "White", "alice.white@email.com");
        CollegeStudent studentTwo =
                new GradebookCollegeStudent("Veronica", "Smith", "veronica.smith@email.com");
        List<CollegeStudent> students = Arrays.asList(studentOne, studentTwo);
        Mockito.when(studentAndGradeService.getAllStudents()).thenReturn(students);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        Assertions.assertNotNull(modelAndView, "failure - modelAndView should not be null");
        ModelAndViewAssert.assertViewName(modelAndView, "index");
        Mockito.verify(studentAndGradeService).getAllStudents();
    }

    @Test
    void createStudent_ShouldSaveStudentAndReturnIndexPage() throws Exception {
        CollegeStudent savedStudent = new CollegeStudent(
                student.getFirstName(), student.getLastName(), student.getEmailAddress()
        );
        savedStudent.setId(1);  // Assuming JPA assigns an ID to the student after saving
        Mockito.when(studentAndGradeService.createStudent(Mockito.any(CollegeStudent.class))).thenReturn(savedStudent);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("firstName", student.getFirstName())
                                .param("lastName", student.getLastName())
                                .param("emailAddress", student.getEmailAddress()))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        Assertions.assertNotNull(modelAndView, "failure - modelAndView should not be null");
        ModelAndViewAssert.assertViewName(modelAndView, "index");
        Mockito.verify(studentAndGradeService).createStudent(Mockito.any(CollegeStudent.class));
    }

    @Test
    void deleteStudent_WhenStudentExists_ShouldDeleteAndReturnIndexPage() throws Exception {
        Mockito.when(studentAndGradeService.isStudentPresent(student.getId())).thenReturn(true);
        Mockito.doNothing().when(studentAndGradeService).deleteStudent(student.getId());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/delete/student/{id}", student.getId()))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        Assertions.assertNotNull(modelAndView, "failure - modelAndView should not be null");
        ModelAndViewAssert.assertViewName(modelAndView, "index");
        Mockito.verify(studentAndGradeService).deleteStudent(student.getId());
    }

    @Test
    void deleteStudent_WhenStudentDoesNotExist_ShouldReturnErrorPage() throws Exception {
        Mockito.when(studentAndGradeService.isStudentPresent(student.getId())).thenReturn(false);
        Mockito.doNothing().when(studentAndGradeService).deleteStudent(student.getId());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/delete/student/{id}", student.getId()))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        Assertions.assertNotNull(modelAndView, "failure - modelAndView should not be null");
        ModelAndViewAssert.assertViewName(modelAndView, "error");
        Mockito.verify(studentAndGradeService, Mockito.never()).deleteStudent(student.getId());
    }

    @Test
    void getStudentInformation_WhenStudentExists_ShouldReturnStudentInformationPageWithStudentInformation() throws Exception {
        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(
                student.getId(), student.getFirstName(),
                student.getLastName(), student.getEmailAddress(),
                new StudentGrades(List.of(), List.of(), List.of())
        );
        Mockito.when(studentAndGradeService.isStudentPresent(student.getId())).thenReturn(true);
        Mockito.when(studentAndGradeService.getStudentInformation(student.getId())).thenReturn(gradebookCollegeStudent);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", student.getId()))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        Assertions.assertNotNull(modelAndView, "failure - modelAndView should not be null");
        ModelAndViewAssert.assertViewName(modelAndView, "student-information");
        Mockito.verify(studentAndGradeService).getStudentInformation(student.getId());
    }

    @Test
    void getStudentInformation_WhenStudentDoesNotExist_ShouldReturnErrorPage() throws Exception {
        Mockito.when(studentAndGradeService.isStudentPresent(student.getId())).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", student.getId()))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        Assertions.assertNotNull(modelAndView, "failure - modelAndView should not be null");
        ModelAndViewAssert.assertViewName(modelAndView, "error");
        Mockito.verify(studentAndGradeService, Mockito.never()).getStudentInformation(student.getId());
    }

    @Test
    void addGrade_WhenStudentExists_ShouldSaveGradeAndReturnStudentInformationPage() throws Exception {
        Grade grade = new MathGrade(student.getId(), 85.0);
        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(
                student.getId(), student.getFirstName(),
                student.getLastName(), student.getEmailAddress(),
                new StudentGrades(List.of(), List.of(), List.of())
        );

        Mockito.when(studentAndGradeService.isStudentPresent(student.getId())).thenReturn(true);
        Mockito.when(studentAndGradeService.addGrade(Mockito.any(Grade.class))).thenReturn(true);
        Mockito.when(studentAndGradeService.getStudentInformation(student.getId())).thenReturn(gradebookCollegeStudent);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/grades")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("grade", String.valueOf(grade.getGrade()))
                                .param("gradeType", GradeType.math.name())
                                .param("studentId", String.valueOf(grade.getStudentId())))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        Assertions.assertNotNull(modelAndView, "failure - modelAndView should not be null");
        ModelAndViewAssert.assertViewName(modelAndView, "student-information");
        Mockito.verify(studentAndGradeService).addGrade(Mockito.any(Grade.class));
    }

    @Test
    void addGrade_WhenStudentDoesNotExist_ShouldReturnErrorPage() throws Exception {
        Mockito.when(studentAndGradeService.isStudentPresent(student.getId())).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/grades")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("grade", "85.0")
                                .param("gradeType", GradeType.math.name())
                                .param("studentId", String.valueOf(student.getId())))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        Assertions.assertNotNull(modelAndView, "failure - modelAndView should not be null");
        ModelAndViewAssert.assertViewName(modelAndView, "error");
        Mockito.verify(studentAndGradeService, Mockito.never()).addGrade(Mockito.any(Grade.class));
    }

    @Test
    void deleteGrade_WhenGradeIdIsValid_ShouldDeleteGradeAndReturnStudentInformationPage() throws Exception {
        int gradeId = 1;
        GradeType gradeType = GradeType.math;
        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(
                student.getId(), student.getFirstName(),
                student.getLastName(), student.getEmailAddress(),
                new StudentGrades(List.of(), List.of(), List.of())
        );

        Mockito.when(studentAndGradeService.deleteGrade(gradeId, gradeType)).thenReturn(student.getId());
        Mockito.when(studentAndGradeService.getStudentInformation(student.getId())).thenReturn(gradebookCollegeStudent);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/delete/grades/{id}/{gradeType}", gradeId, gradeType.name()))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        Assertions.assertNotNull(modelAndView, "failure - modelAndView should not be null");
        ModelAndViewAssert.assertViewName(modelAndView, "student-information");
        Mockito.verify(studentAndGradeService).deleteGrade(gradeId, gradeType);
    }

    @Test
    void deleteGrade_WhenGradeIdIsNotValid_ShouldReturnErrorPage() throws Exception {
        int gradeId = 1000;
        GradeType gradeType = GradeType.math;

        Mockito.when(studentAndGradeService.deleteGrade(gradeId, gradeType)).thenReturn(-1);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/delete/grades/{id}/{gradeType}", gradeId, gradeType.name()))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        Assertions.assertNotNull(modelAndView, "failure - modelAndView should not be null");
        ModelAndViewAssert.assertViewName(modelAndView, "error");
        Mockito.verify(studentAndGradeService).deleteGrade(gradeId, gradeType);
    }


}
