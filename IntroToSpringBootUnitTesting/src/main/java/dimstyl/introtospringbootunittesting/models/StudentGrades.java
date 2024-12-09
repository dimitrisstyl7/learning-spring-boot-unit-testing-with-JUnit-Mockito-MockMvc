package dimstyl.introtospringbootunittesting.models;

import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Setter
@Component
public class StudentGrades {

    private List<Double> grades;

    public StudentGrades(List<Double> grades) {
        this.grades = grades;
    }

    public double calculateGradesSum() {
        return grades.stream().mapToDouble(grade -> grade).sum();
    }

    public double findGradePointAverage() {
        int lengthOfGrades = grades.size();
        double sum = calculateGradesSum();
        double result = sum / lengthOfGrades;

        // add a round function
        BigDecimal resultRound = BigDecimal.valueOf(result);
        resultRound = resultRound.setScale(2, RoundingMode.HALF_UP);
        return resultRound.doubleValue();

    }

    static public Boolean isGradeGreater(double gradeOne, double gradeTwo) {
        return gradeOne > gradeTwo;
    }

    @Override
    public String toString() {
        return "StudentGrades{" +
               "grades=" + grades +
               '}';
    }

}
