package dimstyl.testingspringbootmvcwebapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@AllArgsConstructor
@Component
public class StudentGrades {

    private List<MathGrade> mathGradeResults;

    private List<ScienceGrade> scienceGradeResults;

    private List<HistoryGrade> historyGradeResults;

}
