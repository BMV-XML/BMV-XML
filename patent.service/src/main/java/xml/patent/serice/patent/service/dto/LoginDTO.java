package xml.patent.serice.patent.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {

    private String username;
    private String password;
    private String service = "PATENT";

    public LoginDTO(String username, String password){
        this.username = username;
        this.password = password;
    }
}
