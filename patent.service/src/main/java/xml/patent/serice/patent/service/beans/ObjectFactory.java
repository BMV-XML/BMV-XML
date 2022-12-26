package xml.patent.serice.patent.service.beans;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public PatentRequest createRequestForStamp() {
        return new PatentRequest();
    }

    public Address createAddress(){return new Address(); }

    public Contact createContact() { return new Contact(); }

    public LegalEntity createLegalEntity() { return new LegalEntity(); }

    public Person createPerson() { return new Person(); }

    public Recepient createAttachmentData() { return new Recepient(); }

    public AdditionalPatent createStampData() { return new AdditionalPatent(); }

    public Commissioner createTaxesData() { return new Commissioner(); }

    public DeliveryData createDeliveryData() { return new DeliveryData(); }

    public Inventor createInventor() { return new Inventor(); }

    public Patent createPatent() { return new Patent(); }

    public PatentData createPatentData() { return new PatentData(); }


    public Submitter createSubmitter() { return new Submitter(); }

    public Title createTitle() { return new Title(); }
}
