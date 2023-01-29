package xml.patent.serice.patent.service.jaxb;

import org.springframework.stereotype.Component;
import xml.patent.serice.patent.service.dto.full.report.FullReportDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;

@Component
public class ReportMarshaller {

    public String marshalling(FullReportDTO patentRequest, OutputStream outputStream) {
        try{

            JAXBContext context = JAXBContext.newInstance("xml.patent.serice.patent.service.dto.full.report");

            Marshaller marshaller = context.createMarshaller();
            //marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.ftn.uns.ac.rs/patent file:/./data/schema/patent2.xsd");

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            System.out.println("[INFO] Marshalled report");

            marshaller.marshal(patentRequest, outputStream);
            return outputStream.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException("Jaxb marshalling exception");
        }
    }
}
