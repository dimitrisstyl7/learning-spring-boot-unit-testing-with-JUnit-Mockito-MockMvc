package dimstyl.testingspringbootrestapi.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, int statusCode, String message) {
}
