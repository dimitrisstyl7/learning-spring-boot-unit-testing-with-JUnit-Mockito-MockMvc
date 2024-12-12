package dimstyl.testingspringbootrestapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import dimstyl.testingspringbootrestapi.enums.GradeType;
import dimstyl.testingspringbootrestapi.models.*;
import dimstyl.testingspringbootrestapi.properties.StudentProperties;
import dimstyl.testingspringbootrestapi.service.StudentAndGradeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.yaml")
@DisplayNameGeneration(CustomDisplayNameGenerator.ReplaceCamelCase.class)
class GradebookControllerTest {

    @Autowired
    private StudentProperties student;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentAndGradeService studentAndGradeService;

    @Test
    void getStudentsEndpoint_ShouldReturnListOfGradebookCollegeStudent() throws Exception {
        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(
                student.getId(), student.getFirstName(),
                student.getLastName(), student.getEmailAddress(),
                new StudentGrades(new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );
        Gradebook gradebook = new Gradebook(List.of(gradebookCollegeStudent));

        Mockito.when(studentAndGradeService.getGradebook()).thenReturn(gradebook);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(gradebook.getGradebookCollegeStudents().size())));

        Mockito.verify(studentAndGradeService).getGradebook();
    }

    @Test
    void createStudentEndpoint_ShouldSaveStudentAndReturnListOfGradebookCollegeStudent() throws Exception {
        CollegeStudent testStudent = new CollegeStudent(
                student.getFirstName(), student.getLastName(), student.getEmailAddress()
        );
        CollegeStudent savedStudent = new CollegeStudent(
                student.getFirstName(), student.getLastName(), student.getEmailAddress()
        );
        savedStudent.setId(1);  // Assuming JPA assigns an ID to the student after saving

        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(
                savedStudent.getId(), savedStudent.getFirstName(),
                savedStudent.getLastName(), savedStudent.getEmailAddress(),
                new StudentGrades(new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );
        Gradebook gradebook = new Gradebook(List.of(gradebookCollegeStudent));

        Mockito.when(studentAndGradeService.getGradebook()).thenReturn(gradebook);
        Mockito.when(studentAndGradeService.createStudent(Mockito.any(CollegeStudent.class))).thenReturn(savedStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStudent)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(gradebook.getGradebookCollegeStudents().size())));

        Mockito.verify(studentAndGradeService).createStudent(Mockito.any(CollegeStudent.class));
    }

    @Test
    void deleteStudentEndpoint_WhenStudentExists_ShouldDeleteStudentAndReturnListOfGradebookCollegeStudent() throws Exception {
        Gradebook gradebook = new Gradebook(List.of());

        Mockito.when(studentAndGradeService.getGradebook()).thenReturn(gradebook);
        Mockito.when(studentAndGradeService.isStudentPresent(student.getId())).thenReturn(true);
        Mockito.doNothing().when(studentAndGradeService).deleteStudent(student.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/students/{id}", student.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(gradebook.getGradebookCollegeStudents().size())));

        Mockito.verify(studentAndGradeService).deleteStudent(student.getId());
    }

    @Test
    void deleteStudentEndpoint_WhenStudentDoesNotExist_ShouldReturnErrorResponse() throws Exception {
        int studentId = 0;

        Mockito.when(studentAndGradeService.isStudentPresent(studentId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/students/{id}", studentId))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode", Matchers.is(404)))
                .andExpect(jsonPath("$.message", Matchers.is("Student does not exist")));

        Mockito.verify(studentAndGradeService, Mockito.never()).deleteStudent(studentId);
    }

    @Test
    void getStudentInformationEndpoint_WhenStudentExists_ShouldReturnStudentInformation() throws Exception {
        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(
                student.getId(), student.getFirstName(),
                student.getLastName(), student.getEmailAddress(),
                new StudentGrades(List.of(), List.of(), List.of())
        );
        Mockito.when(studentAndGradeService.isStudentPresent(student.getId())).thenReturn(true);
        Mockito.when(studentAndGradeService.getStudentInformation(student.getId())).thenReturn(gradebookCollegeStudent);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}/information", student.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(student.getId())))
                .andExpect(jsonPath("$.firstName", Matchers.is(student.getFirstName())))
                .andExpect(jsonPath("$.lastName", Matchers.is(student.getLastName())))
                .andExpect(jsonPath("$.emailAddress", Matchers.is(student.getEmailAddress())))
                .andExpect(jsonPath("$.studentGrades", Matchers.anything()));

        Mockito.verify(studentAndGradeService).getStudentInformation(student.getId());
    }

    @Test
    void getStudentInformationEndpoint_WhenStudentDoesNotExist_ShouldReturnErrorResponse() throws Exception {
        int studentId = 0;

        Mockito.when(studentAndGradeService.isStudentPresent(studentId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}/information", studentId))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode", Matchers.is(404)))
                .andExpect(jsonPath("$.message", Matchers.is("Student does not exist")));

        Mockito.verify(studentAndGradeService, Mockito.never()).getStudentInformation(studentId);
    }

    @Test
    void addGradeEndpoint_WhenStudentExists_ShouldSaveGradeAndReturnStudentInformation() throws Exception {
        Grade grade = new MathGrade(student.getId(), 85.0);
        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(
                student.getId(), student.getFirstName(),
                student.getLastName(), student.getEmailAddress(),
                new StudentGrades(List.of(), List.of(), List.of())
        );
        Mockito.when(studentAndGradeService.isStudentPresent(student.getId())).thenReturn(true);
        Mockito.when(studentAndGradeService.addGrade(Mockito.any(Grade.class))).thenReturn(true);
        Mockito.when(studentAndGradeService.getStudentInformation(student.getId())).thenReturn(gradebookCollegeStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", String.valueOf(grade.getGrade()))
                        .param("gradeType", GradeType.math.name())
                        .param("studentId", String.valueOf(grade.getStudentId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(student.getId())))
                .andExpect(jsonPath("$.firstName", Matchers.is(student.getFirstName())))
                .andExpect(jsonPath("$.lastName", Matchers.is(student.getLastName())))
                .andExpect(jsonPath("$.emailAddress", Matchers.is(student.getEmailAddress())))
                .andExpect(jsonPath("$.studentGrades", Matchers.anything()));

        Mockito.verify(studentAndGradeService).addGrade(Mockito.any(Grade.class));
    }

    @Test
    void addGradeEndpoint_WhenStudentDoesNotExist_ShouldReturnErrorResponse() throws Exception {
        int studentId = 0;

        Mockito.when(studentAndGradeService.isStudentPresent(studentId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", "85.0")
                        .param("gradeType", GradeType.math.name())
                        .param("studentId", String.valueOf(studentId)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode", Matchers.is(404)))
                .andExpect(jsonPath("$.message", Matchers.is("Student does not exist")));

        Mockito.verify(studentAndGradeService, Mockito.never()).addGrade(Mockito.any(Grade.class));
    }

    @Test
    void addGradeEndpoint_WhenInvalidGrade_ShouldReturnErrorResponse() throws Exception {
        Grade grade = new MathGrade(student.getId(), 185.0);

        Mockito.when(studentAndGradeService.isStudentPresent(student.getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", String.valueOf(grade.getGrade()))
                        .param("gradeType", GradeType.math.name())
                        .param("studentId", String.valueOf(grade.getStudentId())))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode", Matchers.is(422)))
                .andExpect(jsonPath("$.message", Matchers.is("Grade addition failed")));

        Mockito.verify(studentAndGradeService).addGrade(Mockito.any(Grade.class));

    }

    @Test
    void deleteGradeEndpoint_WhenGradeIdIsValid_ShouldDeleteGradeAndReturnStudentInformation() throws Exception {
        int gradeId = 1;
        GradeType gradeType = GradeType.math;
        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(
                student.getId(), student.getFirstName(),
                student.getLastName(), student.getEmailAddress(),
                new StudentGrades(List.of(), List.of(), List.of())
        );

        Mockito.when(studentAndGradeService.deleteGrade(gradeId, gradeType)).thenReturn(student.getId());
        Mockito.when(studentAndGradeService.getStudentInformation(student.getId())).thenReturn(gradebookCollegeStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/grades/{id}/{gradeType}", gradeId, gradeType.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(student.getId())))
                .andExpect(jsonPath("$.firstName", Matchers.is(student.getFirstName())))
                .andExpect(jsonPath("$.lastName", Matchers.is(student.getLastName())))
                .andExpect(jsonPath("$.emailAddress", Matchers.is(student.getEmailAddress())))
                .andExpect(jsonPath("$.studentGrades", Matchers.anything()));

        Mockito.verify(studentAndGradeService).deleteGrade(gradeId, gradeType);
    }

    @Test
    void deleteGradeEndpoint_WhenGradeIdIsNotValid_ShouldReturnErrorResponse() throws Exception {
        int gradeId = 1000;
        GradeType gradeType = GradeType.math;

        Mockito.when(studentAndGradeService.deleteGrade(gradeId, gradeType)).thenReturn(-1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/grades/{id}/{gradeType}", gradeId, gradeType.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode", Matchers.is(404)))
                .andExpect(jsonPath("$.message", Matchers.is("Grade does not exist")));

        Mockito.verify(studentAndGradeService).deleteGrade(gradeId, gradeType);
    }

}
