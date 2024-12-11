package dimstyl.introtomockito.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ApplicationDao {

    public double calculateGradesSum(List<Double> grades) {
        return grades.stream().mapToDouble(grade -> grade).sum();
    }

    public double findGradePointAverage(List<Double> grades) {
        int lengthOfGrades = grades.size();
        double sum = calculateGradesSum(grades);
        double result = sum / lengthOfGrades;

        // add a round function
        BigDecimal resultRound = BigDecimal.valueOf(result);
        resultRound = resultRound.setScale(2, RoundingMode.HALF_UP);
        return resultRound.doubleValue();
    }

    public Object checkNull(Object obj) {
        return obj;
    }

}
