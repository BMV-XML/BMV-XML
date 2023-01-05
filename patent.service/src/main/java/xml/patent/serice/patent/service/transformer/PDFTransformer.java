package xml.patent.serice.patent.service.transformer;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.xml.sax.SAXException;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class PDFTransformer {

    private FopFactory fopFactory;

    private TransformerFactory transformerFactory;

    private String input_file = "data/res/new-patent.xml";//!!!!!!!!!!!!1

    private String xsl_file = "data/xsl-fo/patent.xsl";

    private String output_file = "data/res/patent.pdf";

    public PDFTransformer() throws IOException, SAXException {
        fopFactory = FopFactory.newInstance(new File("src/fop.xconf"));
        transformerFactory = new TransformerFactoryImpl();
    }

    public void generatePDF() throws Exception {
        System.out.println("[INFO] " + PDFTransformer.class.getSimpleName());

        StreamSource transformSource = new StreamSource(new File(xsl_file));
        StreamSource source = new StreamSource(new File(input_file));

        FOUserAgent userAgent = fopFactory.newFOUserAgent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outputStream);

        Result res = new SAXResult(fop.getDefaultHandler());
        xslFoTransformer.transform(source, res);

        File pdfFile = new File(output_file);
        if (!pdfFile.getParentFile().exists()) {
            System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
            pdfFile.getParentFile().mkdir();
        }

        OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
        out.write(outputStream.toByteArray());

        System.out.println("[INFO] File \"" + pdfFile.getCanonicalPath() + "\" generated successfully.");
        out.close();

        System.out.println("[INFO] End.");
    }
}
