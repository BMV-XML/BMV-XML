package xml.main.main.service.jaxb;

import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import xml.main.main.service.beans.Solutions;
import xml.main.main.service.beans.Users;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.OutputStream;
import java.io.StringReader;

@Component
public class SolutionLoader {

    public Solutions unmarshalling(String text) {
        try {

            JAXBContext context = JAXBContext.newInstance("xml.main.main.service.beans");

            Unmarshaller unmarshaller = context.createUnmarshaller();

            System.out.println("Unmarshall done");
            return (Solutions) unmarshaller.unmarshal(new InputSource(new StringReader(text)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Jaxb unmarshalling exception");
        }
    }

    public OutputStream marshalling(Solutions patentRequest, OutputStream outputStream) {
        try{

            JAXBContext context = JAXBContext.newInstance("xml.main.main.service.beans");

            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(patentRequest, outputStream);
            return outputStream;
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException("Jaxb marshalling exception");
        }
    }
}
