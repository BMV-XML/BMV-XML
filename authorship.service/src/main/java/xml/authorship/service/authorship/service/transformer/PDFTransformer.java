package xml.authorship.service.authorship.service.transformer;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import xml.authorship.service.authorship.service.db.ExistManager;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;

@Component
public class PDFTransformer {

    private FopFactory fopFactory;

    private TransformerFactory transformerFactory;

    private String xsl_file = "data/xsl-fo/authorship.xsl";

    private String output_path = "src/main/resources/static/pdf/";

    @Autowired
    private ExistManager existManager;

    public PDFTransformer() throws IOException, SAXException {
        fopFactory = FopFactory.newInstance(new File("src/fop.xconf"));
        transformerFactory = new TransformerFactoryImpl();
    }

    public File generatePDF(String documentId) throws Exception {
        String retrieved = existManager.retrieve(documentId);

        System.out.println("[INFO] " + PDFTransformer.class.getSimpleName());

        StreamSource transformSource = new StreamSource(new File(xsl_file));
        StreamSource source = new StreamSource(new StringReader(retrieved));

        FOUserAgent userAgent = fopFactory.newFOUserAgent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outputStream);

        Result res = new SAXResult(fop.getDefaultHandler());
        xslFoTransformer.transform(source, res);

        File pdfFile = new File((output_path + documentId + ".pdf"));
        if (!pdfFile.getParentFile().exists()) {
            System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
            pdfFile.getParentFile().mkdir();
        }

        OutputStream out = new BufferedOutputStream(Files.newOutputStream(pdfFile.toPath()));
        out.write(outputStream.toByteArray());

        System.out.println("[INFO] File \"" + pdfFile.getCanonicalPath() + "\" generated successfully.");
        out.close();

        System.out.println("[INFO] End.");
        return pdfFile;
    }
}
