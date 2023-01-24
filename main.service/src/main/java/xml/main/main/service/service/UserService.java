package xml.main.main.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.main.main.service.beans.ServiceType;
import xml.main.main.service.beans.User;
import xml.main.main.service.beans.Users;
import xml.main.main.service.db.ExistManager;
import xml.main.main.service.db.UsersManager;
import xml.main.main.service.exception.NotAuthentificatedException;

@Service
public class UserService extends ExistManager{

    @Autowired
    private UsersManager usersManager;

    public boolean hasAuthority(String username, String password, ServiceType serviceType) throws Exception {
        Users users = usersManager.retrieve();
        return users.hasAuthority(username, password, serviceType);
    }

    public void register(String username, String password, ServiceType serviceType, String name, String surname) throws Exception {
        Users users;
        try {
            users = usersManager.retrieve();
        }catch (NullPointerException e){
            users = null;
        }
        if (users == null)
            users = new Users();
        if (users.hasSameUsername(username))
            throw  new NotAuthentificatedException("User: " + username + " is not auth for service: " + serviceType.toString());
        User user = new User(username, password, serviceType, name, surname);
        users.addUser(user);
        usersManager.storeUserFromObj(users);
    }

    public String getAuthority(String username, String password) throws Exception {
        Users users = usersManager.retrieve();
        return users.getAuthority(username, password);
    }

    public User getUserByUsername(String username) throws Exception {
        Users users = usersManager.retrieve();
        return users.getUser(username);
    }
}
