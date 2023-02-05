package xml.stamp.service.stamp.service.service;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import xml.stamp.service.stamp.service.db.ExistManager;
import xml.stamp.service.stamp.service.dto.report.FullReportDTO;
import xml.stamp.service.stamp.service.dto.RangeDTO;
import xml.stamp.service.stamp.service.dto.ReportDTO;
import xml.stamp.service.stamp.service.jaxb.ReportMarshaller;
import xml.stamp.service.stamp.service.model.RequestForStamp;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ExistManager existManager;

    @Value("${main.service.url}")
    private String basicUrl;

    public ReportService() throws IOException, SAXException {
    }

    public ReportDTO getReportForRange(RangeDTO rangeDTO) throws URISyntaxException {

        URI uri = new URI(basicUrl + "/report/stamp");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity entity = new HttpEntity(rangeDTO, headers);
        ResponseEntity<ReportDTO> result = restTemplate.exchange(
                uri, HttpMethod.POST, entity, ReportDTO.class);
        //SuccessDTO result = restTemplate.getForObject(uri+"/"+requestId.replace("/", "-"), SuccessDTO.class);
        // ResponseEntity<SuccessDTO> result = restTemplate.getForEntity(uri+"/"+requestId.replace("/", "-"), SuccessDTO.class);
        //System.out.println(result.getBody().isSuccessful());

        return result.getBody();
        //return result.isSuccessful();
    }

    public int getNumberOfReportsForRange(RangeDTO rangeDTO) throws Exception {
        List<RequestForStamp> requestList = existManager.retrieveCollection();
        int numberOfPatents = 0;
        for (RequestForStamp stampRequest : requestList){
            if (stampRequest.getStampData().getDateOfApplication().getDate().isBefore(rangeDTO.getStartDateAsDate()))
                continue;
            if (stampRequest.getStampData().getDateOfApplication().getDate().isAfter(rangeDTO.getEndDateAsDate()))
                continue;
            numberOfPatents++;
        }
        return numberOfPatents;
    }


    private FopFactory fopFactory= FopFactory.newInstance(new File("src/main/resources/fop.xconf"));

    private TransformerFactory transformerFactory = new TransformerFactoryImpl();

    private String xsl_file = "data/xsl-fo/report.xsl";

    private String output_path = "src/main/resources/static/report/";

    @Autowired
    private ReportMarshaller reportMarshaller;


    public String generateReportPDF(RangeDTO rangeDTO) throws Exception {
        String report = ""; //TODO: implement
        String documentName = generateReportDocumentName(rangeDTO);

        ReportDTO reportDTO = getReportForRange(rangeDTO);
        int result = getNumberOfReportsForRange(rangeDTO);
        FullReportDTO fullReportDTO = new FullReportDTO(rangeDTO.getStartDateAsDate(), rangeDTO.getEndDateAsDate(), result, reportDTO.getApproved(), reportDTO.getDeclined());

        OutputStream os = new ByteArrayOutputStream();
        report = reportMarshaller.marshalling(fullReportDTO,os);
        System.out.println("****************************************** REPORT ************************");
        System.out.println(report);
        ///*
        StreamSource transformSource = new StreamSource(new File(xsl_file));
        StreamSource source = new StreamSource(new StringReader(report));

        FOUserAgent userAgent = fopFactory.newFOUserAgent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outputStream);

        Result res = new SAXResult(fop.getDefaultHandler());
        xslFoTransformer.transform(source, res);

        File pdfFile = new File((output_path + documentName + ".pdf"));
        if (!pdfFile.getParentFile().exists()) {
            ///System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
            pdfFile.getParentFile().mkdir();
        }

        OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
        out.write(outputStream.toByteArray());

        //System.out.println("[INFO] File \"" + pdfFile.getCanonicalPath() + "\" generated successfully.");
        out.close();

        //System.out.println("[INFO] End.");

        // */
        return "http://localhost:8080/resources/report/" + documentName + ".pdf";
    }

    private String generateReportDocumentName(RangeDTO rangeDTO) {
        StringBuilder docName = new StringBuilder();
        docName.append("report-");
        docName.append(rangeDTO.getStartDateForDocumentName());
        docName.append("-");
        docName.append(rangeDTO.getEndDateForDocumentName());
        return docName.toString();
    }
}
