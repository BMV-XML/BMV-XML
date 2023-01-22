package xml.main.main.service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoSolutionException extends Exception{
    private String message;
    public NoSolutionException(String message) {
        this.message = message;
    }
}
