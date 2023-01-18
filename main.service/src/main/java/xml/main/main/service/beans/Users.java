package xml.main.main.service.beans;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "users", propOrder = {
        "users"
})
@XmlRootElement(name = "users")
public class Users {

    @XmlElement(name = "user", required = false)
    private List<User> users;

    public boolean hasSameUsername(String username) {
        if (users == null)
            return false;
        for (User u : users) {
            if (u.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public boolean hasAuthority(String username, String password, ServiceType serviceType) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password) &&
                    (u.getUserType() == serviceType || u.getUserType() == ServiceType.ALL))
                return true;
        }
        return false;
    }

    public void addUser(User user) {
        if (users == null)
            users = new ArrayList<>();
        users.add(user);
    }

    public String getAuthority(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return u.getUserType().toString();
        }
        return null;
    }
}
