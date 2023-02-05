package xml.authorship.service.authorship.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    private String username;
    private String password;
    private String service = "AUTHORSHIP";

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
