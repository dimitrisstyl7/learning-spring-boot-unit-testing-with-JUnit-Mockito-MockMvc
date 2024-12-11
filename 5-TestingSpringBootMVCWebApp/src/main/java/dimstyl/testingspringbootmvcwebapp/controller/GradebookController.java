package dimstyl.testingspringbootmvcwebapp.controller;

import dimstyl.testingspringbootmvcwebapp.enums.GradeType;
import dimstyl.testingspringbootmvcwebapp.models.*;
import dimstyl.testingspringbootmvcwebapp.service.StudentAndGradeService;
import dimstyl.testingspringbootmvcwebapp.utils.ModelUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class GradebookController {

    private final StudentAndGradeService studentAndGradeService;

    @GetMapping
    public String getStudents(Model model) {
        model.addAttribute("students", studentAndGradeService.getAllStudents());
        return "index";
    }

    @PostMapping
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model model) {
        studentAndGradeService.createStudent(student);
        model.addAttribute("students", studentAndGradeService.getAllStudents());
        return "index";
    }

    @GetMapping("/studentInformation/{id}")
    public String getStudentInformation(@PathVariable int id, Model model) {
        if (!studentAndGradeService.isStudentPresent(id)) return "error";
        ModelUtils.configureStudentInformationModel(studentAndGradeService.getStudentInformation(id), model);
        return "student-information";
    }

    @GetMapping("/delete/student/{id}")
    public String deleteStudent(@PathVariable int id, Model model) {
        if (!studentAndGradeService.isStudentPresent(id)) return "error";

        studentAndGradeService.deleteAllGradesByStudentId(id);
        studentAndGradeService.deleteStudent(id);

        model.addAttribute("students", studentAndGradeService.getAllStudents());

        return "index";
    }

    @PostMapping("/grades")
    public String addGrade(@RequestParam("grade") double grade,
                           @RequestParam("gradeType") GradeType gradeType,
                           @RequestParam("studentId") int studentId,
                           Model model) {
        if (!studentAndGradeService.isStudentPresent(studentId)) return "error";

        Grade newGrade = switch (gradeType) {
            case GradeType.math -> new MathGrade(studentId, grade);
            case GradeType.history -> new HistoryGrade(studentId, grade);
            case science -> new ScienceGrade(studentId, grade);
        };

        if (!studentAndGradeService.addGrade(newGrade)) return "error";

        ModelUtils.configureStudentInformationModel(studentAndGradeService.getStudentInformation(studentId), model);

        return "student-information";
    }

    @GetMapping("/delete/grades/{id}/{gradeType}")
    public String deleteGrade(@PathVariable int id, @PathVariable GradeType gradeType, Model model) {
        int studentId = studentAndGradeService.deleteGrade(id, gradeType);
        if (studentId == -1) return "error";
        ModelUtils.configureStudentInformationModel(studentAndGradeService.getStudentInformation(studentId), model);
        return "student-information";
    }
}
