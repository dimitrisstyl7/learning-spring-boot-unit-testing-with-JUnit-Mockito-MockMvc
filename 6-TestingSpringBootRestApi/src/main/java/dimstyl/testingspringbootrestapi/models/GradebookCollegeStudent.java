package dimstyl.testingspringbootrestapi.models;

import lombok.Getter;

@Getter
public class GradebookCollegeStudent extends CollegeStudent {

    private int id;
    private StudentGrades studentGrades;

    public GradebookCollegeStudent(int id, String firstName, String lastName,
                                   String emailAddress, StudentGrades studentGrades) {
        super(firstName, lastName, emailAddress);
        this.id = id;
        this.studentGrades = studentGrades;
    }

    public GradebookCollegeStudent(String firstName, String lastName, String emailAddress) {
        super(firstName, lastName, emailAddress);
    }

}
