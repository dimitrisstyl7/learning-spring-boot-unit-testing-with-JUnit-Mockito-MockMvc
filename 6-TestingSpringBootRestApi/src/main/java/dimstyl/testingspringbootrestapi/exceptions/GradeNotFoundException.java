package dimstyl.testingspringbootrestapi.exceptions;

public class GradeNotFoundException extends RuntimeException {

    public GradeNotFoundException(String message) {
        super(message);
    }

}
