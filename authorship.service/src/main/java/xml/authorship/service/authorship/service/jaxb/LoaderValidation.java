package xml.authorship.service.authorship.service.jaxb;

import org.springframework.stereotype.Component;
import xml.authorship.service.authorship.service.beans.AuthorsWork;
import xml.authorship.service.authorship.service.beans.AuthorshipRequest;
import xml.authorship.service.authorship.service.util.MyValidationEventHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.OutputStream;
import java.io.StringReader;

@Component
public class LoaderValidation {

    public AuthorshipRequest unmarshalling(String file) {
        try {
            System.out.println("\n[INFO] Example "+ file + ": JAXB Patent XML Schema validation.");

            JAXBContext context = JAXBContext.newInstance("xml.authorship.service.authorship.service.beans");

            Unmarshaller unmarshaller = context.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("./data/zahtjev.xsd"));

            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());

            System.out.println("Unmarshall done");
            return (AuthorshipRequest) unmarshaller.unmarshal(new StreamSource(new StringReader(file)));
        } catch (Exception e) {
            throw new RuntimeException("Jaxb unmarshalling exception");
        }
    }

    public OutputStream marshalling(AuthorshipRequest authorshipRequest, OutputStream outputStream) {
        try{

            JAXBContext context = JAXBContext.newInstance(AuthorshipRequest.class);
            // "xml.authorship.service.authorship.service.beans.AuthorshipRequest"
            Marshaller marshaller = context.createMarshaller();
            //marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.ftn.uns.ac.rs/patent file:/./data/zahtjev.xsd");

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            System.out.println("[INFO] Marshalled authorship request");

            marshaller.marshal(authorshipRequest, outputStream);
            return outputStream;
        } catch (JAXBException e) {
            throw new RuntimeException("Jaxb marshalling exception");
        }
    }

}
