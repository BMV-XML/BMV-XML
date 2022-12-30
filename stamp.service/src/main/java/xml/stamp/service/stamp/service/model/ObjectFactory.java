package xml.stamp.service.stamp.service.model;

import org.checkerframework.checker.units.qual.A;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public RequestForStamp createRequestForStamp() {
        return new RequestForStamp();
    }

    public Address createAddress(){return new Address(); }

    public Contact createContact() { return new Contact(); }

    public LegalEntity createLegalEntity() { return new LegalEntity(); }

    public Person createPerson() { return new Person(); }

    public AttachmentData createAttachmentData() { return new AttachmentData(); }

    public StampData createStampData() { return new StampData(); }

    public TaxesData createTaxesData() { return new TaxesData(); }

    public Attachment creAttachment() { return new Attachment(); }

    public SubsequentAttachment createSubsequentAttachment() { return new SubsequentAttachment(); }

    public Recepient createRecepient() { return new Recepient(); }

}
