package dimstyl.testingspringbootmvcwebapp.service.impl;

import dimstyl.testingspringbootmvcwebapp.enums.GradeType;
import dimstyl.testingspringbootmvcwebapp.models.*;
import dimstyl.testingspringbootmvcwebapp.repositories.CollegeStudentRepository;
import dimstyl.testingspringbootmvcwebapp.repositories.HistoryGradeRepository;
import dimstyl.testingspringbootmvcwebapp.repositories.MathGradeRepository;
import dimstyl.testingspringbootmvcwebapp.repositories.ScienceGradeRepository;
import dimstyl.testingspringbootmvcwebapp.service.StudentAndGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentAndGradeServiceImpl implements StudentAndGradeService {

    private final CollegeStudentRepository collegeStudentRepository;
    private final MathGradeRepository mathGradeRepository;
    private final HistoryGradeRepository historyGradeRepository;
    private final ScienceGradeRepository scienceGradeRepository;

    @Override
    public CollegeStudent createStudent(CollegeStudent student) {
        return collegeStudentRepository.save(student);
    }

    @Override
    public boolean addGrade(Grade grade) {
        if (!isStudentPresent(grade.getStudentId())) return false;
        if (grade.getGrade() < 0 || grade.getGrade() > 100) return false;

        switch (grade) {
            case MathGrade mathGrade -> mathGradeRepository.save(mathGrade);
            case HistoryGrade historyGrade -> historyGradeRepository.save(historyGrade);
            case ScienceGrade scienceGrade -> scienceGradeRepository.save(scienceGrade);
            default -> {
                return false;
            }
        }

        return true;
    }

    @Override
    public Iterable<CollegeStudent> getAllStudents() {
        return collegeStudentRepository.findAll();
    }

    @Override
    public GradebookCollegeStudent getStudentInformation(int id) {
        Optional<CollegeStudent> studentOptional = collegeStudentRepository.findById(id);
        if (studentOptional.isEmpty()) return null;
        CollegeStudent student = studentOptional.get();

        List<MathGrade> mathGrades = mathGradeRepository.findAllByStudentId(id);
        List<HistoryGrade> historyGrades = historyGradeRepository.findAllByStudentId(id);
        List<ScienceGrade> scienceGrades = scienceGradeRepository.findAllByStudentId(id);

        return new GradebookCollegeStudent(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmailAddress(),
                new StudentGrades(mathGrades, scienceGrades, historyGrades)
        );
    }

    @Override
    public Optional<CollegeStudent> findStudentById(int id) {
        return collegeStudentRepository.findById(id);
    }

    @Override
    public CollegeStudent findByEmailAddress(String emailAddress) {
        return collegeStudentRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public List<? extends Grade> findGradesByStudentId(int id, GradeType gradeType) {
        return switch (gradeType) {
            case GradeType.math -> mathGradeRepository.findAllByStudentId(id);
            case GradeType.history -> historyGradeRepository.findAllByStudentId(id);
            case GradeType.science -> scienceGradeRepository.findAllByStudentId(id);
        };
    }

    @Override
    public void deleteStudent(int id) {
        collegeStudentRepository.deleteById(id);
    }

    @Override
    public void deleteAllGradesByStudentId(int id) {
        mathGradeRepository.deleteAllByStudentId(id);
        historyGradeRepository.deleteAllByStudentId(id);
        scienceGradeRepository.deleteAllByStudentId(id);
    }

    @Override
    public int deleteGrade(int id, GradeType gradeType) {
        return switch (gradeType) {
            case GradeType.math -> {
                Optional<MathGrade> gradeOptional = mathGradeRepository.findById(id);
                yield deleteGradeHelperMethod(gradeOptional, mathGradeRepository::deleteById);
            }
            case GradeType.history -> {
                Optional<HistoryGrade> gradeOptional = historyGradeRepository.findById(id);
                yield deleteGradeHelperMethod(gradeOptional, historyGradeRepository::deleteById);
            }
            case GradeType.science -> {
                Optional<ScienceGrade> gradeOptional = scienceGradeRepository.findById(id);
                yield deleteGradeHelperMethod(gradeOptional, scienceGradeRepository::deleteById);
            }
        };
    }

    @Override
    public boolean isStudentPresent(int id) {
        return collegeStudentRepository.findById(id).isPresent();
    }

    private int deleteGradeHelperMethod(Optional<? extends Grade> gradeOptional, Consumer<Integer> consumer) {
        if (gradeOptional.isEmpty()) return -1;
        var grade = gradeOptional.get();
        int studentId = grade.getStudentId();
        consumer.accept(grade.getId());
        return studentId;
    }

}
