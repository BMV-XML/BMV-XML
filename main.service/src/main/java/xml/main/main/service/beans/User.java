package xml.main.main.service.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
        "username",
        "password",
        "userType",
        "name",
        "surname"
})
@XmlRootElement(name = "user")
public class User {

    @XmlElement(name = "username", required = true)
    private String username;
    @XmlElement(name = "password", required = true)
    private String password;

    @XmlElement(name = "usertype", required = true)
    private ServiceType userType;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "surname")
    private String surname;

    public User(String username, String password, ServiceType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User(String username, String password, ServiceType userType, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.userType = userType;
    }
}
