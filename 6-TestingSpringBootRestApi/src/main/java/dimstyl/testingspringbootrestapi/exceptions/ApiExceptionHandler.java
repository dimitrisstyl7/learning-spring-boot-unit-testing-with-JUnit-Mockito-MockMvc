package dimstyl.testingspringbootrestapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({StudentNotFoundException.class, GradeNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorResponse handleUserInfoNotFoundException(Exception ex) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(GradeAdditionFailedException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    protected ErrorResponse handleGradeAdditionFailedException(Exception ex) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
    }

}
