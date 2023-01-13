package xml.authorship.service.authorship.service.jaxb;

import xml.authorship.service.authorship.service.beans.AuthorsWork;
import xml.authorship.service.authorship.service.beans.AuthorshipRequest;
import xml.authorship.service.authorship.service.util.MyValidationEventHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class LoaderValidation {

    public void unmarshalling() {
        try {
            System.out.println("[INFO] Example 3: JAXB Patent XML Schema validation.\n");

            JAXBContext context = JAXBContext.newInstance("xml.authorship.service.authorship.service.beans");

            Unmarshaller unmarshaller = context.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("./data/zahtjev.xsd"));

            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());

            AuthorshipRequest authorshipRequest = (AuthorshipRequest) unmarshaller.unmarshal(new File("./data/aut.xml"));
            this.changeAuthorsWorkName(authorshipRequest.getAuthorsWork());

            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            System.out.println("[INFO] Marshalled patent request:");

            marshaller.marshal(authorshipRequest, new File("./data/aut-out.xml"));
            System.out.println("[INFO] Marshalling ended");
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeAuthorsWorkName(AuthorsWork work) {
        work.setTitle("Novi naslovvv");
    }

}
