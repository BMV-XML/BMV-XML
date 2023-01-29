package xml.stamp.service.stamp.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {
    private String username;
    private String password;
    private String service = "STAMP";

    public LoginDTO(String username, String password){
        this.username = username;
        this.password = password;
    }
}
