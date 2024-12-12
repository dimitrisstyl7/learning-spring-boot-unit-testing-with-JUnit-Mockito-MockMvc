package dimstyl.testingspringbootrestapi.service;

import dimstyl.testingspringbootrestapi.enums.GradeType;
import dimstyl.testingspringbootrestapi.models.CollegeStudent;
import dimstyl.testingspringbootrestapi.models.Grade;
import dimstyl.testingspringbootrestapi.models.Gradebook;
import dimstyl.testingspringbootrestapi.models.GradebookCollegeStudent;

import java.util.List;
import java.util.Optional;

public interface StudentAndGradeService {

    CollegeStudent createStudent(CollegeStudent student);

    boolean addGrade(Grade grade);

    List<CollegeStudent> getAllStudents();

    GradebookCollegeStudent getStudentInformation(int id);

    Gradebook getGradebook();

    Optional<CollegeStudent> findStudentById(int id);

    CollegeStudent findByEmailAddress(String emailAddress);

    List<? extends Grade> findGradesByStudentId(int id, GradeType gradeType);

    void deleteStudent(int id);

    void deleteAllGradesByStudentId(int id);

    int deleteGrade(int id, GradeType gradeType);

    boolean isStudentPresent(int id);

}
