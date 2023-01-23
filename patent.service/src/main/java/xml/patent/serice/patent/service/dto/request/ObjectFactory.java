package xml.patent.serice.patent.service.dto.request;

import xml.patent.serice.patent.service.beans.*;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public PatentRequestDTO createRequest() {return new PatentRequestDTO(); }
    public EntityDTO createEntity() {return new EntityDTO(); }
    public AddressDTO createAddress() {return new AddressDTO(); }
    public TitleDTO createTitle() {return new TitleDTO(); }
    public PreviousPatentDTO createPreviousPatent() {return new PreviousPatentDTO(); }
}
