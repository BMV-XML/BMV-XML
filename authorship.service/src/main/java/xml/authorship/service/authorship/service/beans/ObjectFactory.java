package xml.authorship.service.authorship.service.beans;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public Address createAddress() {
        return new Address();
    }

    public Attachments createAttachments() {
        return new Attachments();
    }

    public Author createAuthor() {
        return new Author();
    }

    public AuthorshipRequest createAuthorshipRequest() {
        return new AuthorshipRequest();
    }

    public AuthorsWork createAuthorsWork() {
        return new AuthorsWork();
    }

    public Commissioner createCommissioner() {
        return new Commissioner();
    }

    public Contact createContact() {
        return new Contact();
    }

    public LegalEntity createLegalEntity() {
        return new LegalEntity();
    }

    public Person createPerson() {
        return new Person();
    }

    public Recipient createRecipient() {
        return new Recipient();
    }

    public RevisedWork createRevisedWork() {
        return new RevisedWork();
    }

    public Submitter createSubmitter() {
        return new Submitter();
    }

}
