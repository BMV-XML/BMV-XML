package xml.patent.serice.patent.service.jaxb;

import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.beans.Recepient;
import xml.patent.serice.patent.service.util.MyValidationEventHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class LoaderValidation {

    private PatentRequest patentRequest;

    public void unmarshalling(String file) {
        try {
            System.out.println("\n[INFO] Example "+ file + ": JAXB Patent XML Schema validation.");

            JAXBContext context = JAXBContext.newInstance("xml.patent.serice.patent.service.beans");

            Unmarshaller unmarshaller = context.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("./data/schema/patent2.xsd"));

            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());

            patentRequest = (PatentRequest) unmarshaller.unmarshal(new File(file));
            System.out.println("Unmarshall done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void marshalling(String outfile) {
        try{

        JAXBContext context = JAXBContext.newInstance("xml.patent.serice.patent.service.beans");

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.ftn.uns.ac.rs/patent file:/./data/schema/patent2.xsd");

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        System.out.println("[INFO] Marshalled patent request");

        marshaller.marshal(patentRequest, new File(outfile));
        System.out.println("[INFO] Marshalling ended: " + outfile + "\n\n");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void changeRecepient() {
        Recepient r = new Recepient();
        r.setAddress(this.patentRequest.getRecepient().getAddress());
        r.setName("PRIMILAC!!!!!!!!");
        this.patentRequest.setRecepient(r);
    }
}
