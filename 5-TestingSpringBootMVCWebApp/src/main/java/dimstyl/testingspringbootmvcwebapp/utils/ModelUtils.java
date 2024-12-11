package dimstyl.testingspringbootmvcwebapp.utils;

import dimstyl.testingspringbootmvcwebapp.models.*;
import lombok.experimental.UtilityClass;
import org.springframework.ui.Model;

import java.util.List;

@UtilityClass
public final class ModelUtils {

    public static void configureStudentInformationModel(GradebookCollegeStudent gradebookCollegeStudent, Model model) {
        StudentGrades grades = gradebookCollegeStudent.getStudentGrades();
        List<Double> mathGrades = grades.getMathGradeResults().stream().map(MathGrade::getGrade).toList();
        List<Double> historyGrades = grades.getHistoryGradeResults().stream().map(HistoryGrade::getGrade).toList();
        List<Double> scienceGrades = grades.getScienceGradeResults().stream().map(ScienceGrade::getGrade).toList();

        model.addAttribute("student", gradebookCollegeStudent);
        if (!mathGrades.isEmpty()) {
            model.addAttribute("mathAverage", GradeUtils.findGradePointAverage(mathGrades));
        } else model.addAttribute("mathAverage", "N/A");

        if (!historyGrades.isEmpty()) {
            model.addAttribute("historyAverage", GradeUtils.findGradePointAverage(historyGrades));
        } else model.addAttribute("historyAverage", "N/A");

        if (!scienceGrades.isEmpty()) {
            model.addAttribute("scienceAverage", GradeUtils.findGradePointAverage(scienceGrades));
        } else model.addAttribute("scienceAverage", "N/A");
    }

}
