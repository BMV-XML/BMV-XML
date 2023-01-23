package xml.stamp.service.stamp.service.jaxb;

import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;
import xml.stamp.service.stamp.service.model.*;
import xml.stamp.service.stamp.service.util.MyValidationEventHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

@Component
public class JaxLoader {
    public RequestForStamp unmarshalling(String text) {
        try {
            System.out.println("[INFO] Loader za zigove\n");

            JAXBContext context = JAXBContext.newInstance("xml.stamp.service.stamp.service.model");

            Unmarshaller unmarshaller = context.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("./data/zig.xsd"));

            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());

            RequestForStamp requestForStamp = (RequestForStamp) unmarshaller.unmarshal(new StringReader(text));

            // Ispis sadržaja objekta
            System.out.println("[INFO] Unmarshalled content:");
            //   System.out.println(requestForStamp);
            return requestForStamp;

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public OutputStream marshalling(RequestForStamp requestForStamp,OutputStream outputStream) {
        try {
            JAXBContext context = JAXBContext.newInstance("xml.stamp.service.stamp.service.model");

            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            System.out.println("[INFO] Marshalling stump:");

            // Umesto System.out-a, može se koristiti FileOutputStream
            marshaller.marshal(requestForStamp, outputStream);

            return outputStream;

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

/*
    public void changeColors(){
        ArrayList<String> newColors = new ArrayList<String>();
        newColors.add("Ljubicasta");
        newColors.add("Narndzasta");
        StampData sd = this.currentRequest.getStampData();
        sd.setColors(newColors);
    }

    */

}
