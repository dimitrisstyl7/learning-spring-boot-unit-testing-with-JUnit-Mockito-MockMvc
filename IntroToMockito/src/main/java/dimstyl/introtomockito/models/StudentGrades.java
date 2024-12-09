package dimstyl.introtomockito.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Component
public class StudentGrades {

    private List<Double> grades;

    @Override
    public String toString() {
        return "StudentGrades{" +
               "grades=" + grades +
               '}';
    }

}
