package xml.stamp.service.stamp.service.jaxb;

import org.springframework.stereotype.Component;
import xml.stamp.service.stamp.service.dto.report.FullReportDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;

@Component
public class ReportMarshaller {
    public String marshalling(FullReportDTO stampRequest, OutputStream outputStream) {
        try{

            JAXBContext context = JAXBContext.newInstance("xml.stamp.service.stamp.service.dto.report");

            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            System.out.println("[INFO] Marshalled report");

            marshaller.marshal(stampRequest, outputStream);
            return outputStream.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException("Jaxb marshalling exception");
        }
    }
}
