package dimstyl.testingspringbootmvcwebapp.service;

import dimstyl.testingspringbootmvcwebapp.enums.GradeType;
import dimstyl.testingspringbootmvcwebapp.models.CollegeStudent;
import dimstyl.testingspringbootmvcwebapp.models.Grade;
import dimstyl.testingspringbootmvcwebapp.models.GradebookCollegeStudent;

import java.util.List;
import java.util.Optional;

public interface StudentAndGradeService {

    CollegeStudent createStudent(CollegeStudent student);

    boolean addGrade(Grade grade);

    Iterable<CollegeStudent> getAllStudents();

    GradebookCollegeStudent getStudentInformation(int id);

    Optional<CollegeStudent> findStudentById(int id);

    CollegeStudent findByEmailAddress(String emailAddress);

    List<? extends Grade> findGradesByStudentId(int id, GradeType gradeType);

    void deleteStudent(int id);

    void deleteAllGradesByStudentId(int id);

    int deleteGrade(int id, GradeType gradeType);

    boolean isStudentPresent(int id);

}
