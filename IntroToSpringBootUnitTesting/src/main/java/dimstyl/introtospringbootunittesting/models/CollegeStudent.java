package dimstyl.introtospringbootunittesting.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CollegeStudent implements Student {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private StudentGrades studentGrades;

    @Override
    public String toString() {
        return "CollegeStudent{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", emailAddress='" + emailAddress + '\'' +
               ", studentGrades=" + studentGrades +
               '}';
    }

    @Override
    public String studentInformation() {
        return getFullName() + " " + getEmailAddress();
    }

    @Override
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

}
