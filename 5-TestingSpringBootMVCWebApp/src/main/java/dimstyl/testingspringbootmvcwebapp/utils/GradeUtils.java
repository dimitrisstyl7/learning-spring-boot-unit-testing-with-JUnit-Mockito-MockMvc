package dimstyl.testingspringbootmvcwebapp.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@UtilityClass
public final class GradeUtils {

    private static double calculateGradesSum(List<Double> grades) {
        return grades.stream().mapToDouble(grade -> grade).sum();
    }

    public static double findGradePointAverage(List<Double> grades) {
        int lengthOfGrades = grades.size();
        double sum = calculateGradesSum(grades);
        double result = sum / lengthOfGrades;

        // add a round function
        BigDecimal resultRound = BigDecimal.valueOf(result);
        resultRound = resultRound.setScale(2, RoundingMode.HALF_UP);
        return resultRound.doubleValue();
    }

}
