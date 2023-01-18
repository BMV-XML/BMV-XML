package xml.main.main.service.beans;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public Users createUsers() {return new Users(); }
    public User createUser(){return new User();}

}
