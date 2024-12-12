package dimstyl.testingspringbootrestapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@AllArgsConstructor
public class Gradebook {

    private List<GradebookCollegeStudent> gradebookCollegeStudents;

     public Gradebook() {
         this.gradebookCollegeStudents = new ArrayList<>();
     }

    public void addGradebookCollegeStudent(GradebookCollegeStudent student) {
        gradebookCollegeStudents.add(student);
    }

}
