package xml.patent.serice.patent.service.jaxb;

import org.springframework.stereotype.Component;
import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.util.MyValidationEventHandler;

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

    //private PatentRequest patentRequest;

    public PatentRequest unmarshalling(String file) {
        try {
            System.out.println("\n[INFO] Example "+ file + ": JAXB Patent XML Schema validation.");

            JAXBContext context = JAXBContext.newInstance("xml.patent.serice.patent.service.beans");

            Unmarshaller unmarshaller = context.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("./data/schema/patent2.xsd"));

            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());

            //patentRequest =
            System.out.println("Unmarshall done");
            return (PatentRequest) unmarshaller.unmarshal(new StreamSource(new StringReader(file)));
        } catch (Exception e) {
            throw new RuntimeException("Jaxb unmarshalling exception");
        }
    }

    public OutputStream marshalling(PatentRequest patentRequest, OutputStream outputStream) {
        try{

        JAXBContext context = JAXBContext.newInstance("xml.patent.serice.patent.service.beans");

        Marshaller marshaller = context.createMarshaller();
        //marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.ftn.uns.ac.rs/patent file:/./data/schema/patent2.xsd");

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        System.out.println("[INFO] Marshalled patent request");

        marshaller.marshal(patentRequest, outputStream);
        return outputStream;
        } catch (JAXBException e) {
            throw new RuntimeException("Jaxb marshalling exception");
        }
    }
/*
    public void changeRecepient() {
        Recepient r = new Recepient();
        r.setAddress(this.patentRequest.getRecepient().getAddress());
        r.setName("PRIMILAC!!!!!!!!");
        this.patentRequest.setRecepient(r);
    }*/
}
