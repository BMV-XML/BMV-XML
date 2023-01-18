package xml.main.main.service.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
        "username",
        "password",
        "userType"
})
@XmlRootElement(name = "Patent")
public class User {

    @XmlElement(name = "username", required = true)
    private String username;
    @XmlElement(name = "password", required = true)
    private String password;

    @XmlElement(name = "usertype", required = true)
    private ServiceType userType;
}
