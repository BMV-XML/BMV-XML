package xml.main.main.service.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoUserFoundException extends Exception{
    private String message;
    public NoUserFoundException(String message) {
        this.message = message;
    }
}
