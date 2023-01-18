package xml.main.main.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.main.main.service.beans.ServiceType;
import xml.main.main.service.beans.User;
import xml.main.main.service.beans.Users;
import xml.main.main.service.db.ExistManager;
import xml.main.main.service.exception.NotAuthentificatedException;

@Service
public class UserService {

    @Autowired
    private ExistManager existManager;

    public boolean hasAuthority(String username, String password, ServiceType serviceType) throws Exception {
        Users users = existManager.retrieve("users.xml");
        return users.hasAuthority(username, password, serviceType);
    }

    public void register(String username, String password, ServiceType serviceType) throws Exception {
        Users users = existManager.retrieve("users.xml");
        if (users == null)
            users = new Users();
        if (users.hasSameUsername(username))
            throw  new NotAuthentificatedException("User: " + username + " is not auth for service: " + serviceType.toString());
        User user = new User(username, password, serviceType);
        users.addUser(user);
        existManager.storeFromPatentRequest(users);
    }

    public String getAuthority(String username, String password) throws Exception {
        Users users = existManager.retrieve("users.xml");
        return users.getAuthority(username, password);
    }
}
