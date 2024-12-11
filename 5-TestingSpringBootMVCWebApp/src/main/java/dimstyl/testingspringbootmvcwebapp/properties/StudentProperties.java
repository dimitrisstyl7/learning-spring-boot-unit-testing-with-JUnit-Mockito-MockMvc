package dimstyl.testingspringbootmvcwebapp.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "define.student")
public class StudentProperties {

    @Setter
    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;

    public void setFirstName(String firstName) {
        this.firstName = firstName.substring(1, firstName.length() - 1);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.substring(1, lastName.length() - 1);
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress.substring(1, emailAddress.length() - 1);
    }

}
