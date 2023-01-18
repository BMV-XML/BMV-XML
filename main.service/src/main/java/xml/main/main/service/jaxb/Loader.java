package xml.main.main.service.jaxb;

import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import xml.main.main.service.beans.Users;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.OutputStream;
import java.io.StringReader;

@Component
public class Loader {

    //private PatentRequest patentRequest;

    public Users unmarshalling(String text) {
        try {
            System.out.println("\n[INFO] Example "+ text + ": JAXB Patent XML Schema validation.");

            JAXBContext context = JAXBContext.newInstance("xml.main.main.service.beans");

            Unmarshaller unmarshaller = context.createUnmarshaller();
            //SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            //Schema schema = schemaFactory.newSchema(new File("./data/schema/patent2.xsd"));

            //unmarshaller.setSchema(schema);
            //unmarshaller.setEventHandler(new MyValidationEventHandler());

            System.out.println("Unmarshall done");
            return (Users) unmarshaller.unmarshal(new InputSource(new StringReader(text)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Jaxb unmarshalling exception");
        }
    }

    public OutputStream marshalling(Users patentRequest, OutputStream outputStream) {
        try{

        JAXBContext context = JAXBContext.newInstance("xml.main.main.service.beans");

        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        System.out.println("[INFO] Marshalled patent request");

        marshaller.marshal(patentRequest, outputStream);
        return outputStream;
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException("Jaxb marshalling exception");
        }
    }

}
