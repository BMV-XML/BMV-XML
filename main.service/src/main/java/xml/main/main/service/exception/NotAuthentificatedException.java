package xml.main.main.service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotAuthentificatedException extends RuntimeException {
    private String message;
    public NotAuthentificatedException(String message) {
        this.message = message;
    }
}
