package dimstyl.testingspringbootrestapi.controller;

import dimstyl.testingspringbootrestapi.enums.GradeType;
import dimstyl.testingspringbootrestapi.exceptions.GradeAdditionFailedException;
import dimstyl.testingspringbootrestapi.exceptions.GradeNotFoundException;
import dimstyl.testingspringbootrestapi.exceptions.StudentNotFoundException;
import dimstyl.testingspringbootrestapi.models.*;
import dimstyl.testingspringbootrestapi.service.StudentAndGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GradebookController {

    private final StudentAndGradeService studentAndGradeService;

    @GetMapping
    public List<GradebookCollegeStudent> getStudents() {
        return studentAndGradeService.getGradebook().getGradebookCollegeStudents();
    }

    @PostMapping
    public List<GradebookCollegeStudent> createStudent(@RequestBody CollegeStudent student) {
        studentAndGradeService.createStudent(student);
        return studentAndGradeService.getGradebook().getGradebookCollegeStudents();
    }

    @GetMapping("/student/{id}/information")
    public GradebookCollegeStudent getStudentInformation(@PathVariable int id) {
        if (!studentAndGradeService.isStudentPresent(id)) throw new StudentNotFoundException("Student does not exist");
        return studentAndGradeService.getStudentInformation(id);
    }

    @DeleteMapping("/students/{id}")
    public List<GradebookCollegeStudent> deleteStudent(@PathVariable int id) {
        if (!studentAndGradeService.isStudentPresent(id)) throw new StudentNotFoundException("Student does not exist");
        studentAndGradeService.deleteStudent(id);
        return studentAndGradeService.getGradebook().getGradebookCollegeStudents();
    }

    @PostMapping("/grades")
    public GradebookCollegeStudent addGrade(@RequestParam("grade") double grade,
                                            @RequestParam("gradeType") GradeType gradeType,
                                            @RequestParam("studentId") int studentId) {
        if (!studentAndGradeService.isStudentPresent(studentId))
            throw new StudentNotFoundException("Student does not exist");

        Grade newGrade = switch (gradeType) {
            case GradeType.math -> new MathGrade(studentId, grade);
            case GradeType.history -> new HistoryGrade(studentId, grade);
            case GradeType.science -> new ScienceGrade(studentId, grade);
        };

        if (!studentAndGradeService.addGrade(newGrade)) throw new GradeAdditionFailedException("Grade addition failed");

        return studentAndGradeService.getStudentInformation(studentId);
    }

    @DeleteMapping("/grades/{id}/{gradeType}")
    public GradebookCollegeStudent deleteGrade(@PathVariable int id, @PathVariable GradeType gradeType) {
        int studentId = studentAndGradeService.deleteGrade(id, gradeType);
        if (studentId == -1) throw new GradeNotFoundException("Grade does not exist");
        return studentAndGradeService.getStudentInformation(studentId);
    }

}