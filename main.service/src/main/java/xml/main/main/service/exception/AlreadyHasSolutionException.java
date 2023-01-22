package xml.main.main.service.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlreadyHasSolutionException extends Exception{

    private String message;
    public AlreadyHasSolutionException(String message) {
        this.message = message;
    }
}
