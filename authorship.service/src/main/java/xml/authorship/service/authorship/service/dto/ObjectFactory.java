package xml.authorship.service.authorship.service.dto;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public AuthorshipRequestDTO createRequest() {return new AuthorshipRequestDTO(); }
    public EntityDTO createEntity() {return new EntityDTO(); }
    public AddressDTO createAddress() {return new AddressDTO(); }
    public AuthorDTO createAuthor() {return new AuthorDTO(); }
    public AuthorsWorkDTO createAuthorsWork() {return new AuthorsWorkDTO(); }
}
