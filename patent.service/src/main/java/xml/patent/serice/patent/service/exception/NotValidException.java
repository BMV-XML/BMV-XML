package xml.patent.serice.patent.service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotValidException extends Exception {
    private int code = 7;
    private String message;
    public NotValidException(String message) {
        this.message = message;
    }
}
