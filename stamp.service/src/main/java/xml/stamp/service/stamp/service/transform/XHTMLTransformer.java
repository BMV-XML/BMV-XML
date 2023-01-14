package xml.stamp.service.stamp.service.transform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import xml.stamp.service.stamp.service.db.ExistManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringReader;

@Service
public class XHTMLTransformer {
    private static DocumentBuilderFactory documentFactory;

    private static TransformerFactory transformerFactory;

    private String xslFile = "data/xhtml/stamp.xsl";

    private String output_file = "src/main/resources/static/html/";

    @Autowired
    private ExistManager existManager;

    static {

        /* Inicijalizacija DOM fabrike */
        documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        documentFactory.setIgnoringComments(true);
        documentFactory.setIgnoringElementContentWhitespace(true);

        /* Inicijalizacija Transformer fabrike */
        transformerFactory = TransformerFactory.newInstance();

    }

    public org.w3c.dom.Document buildDocument(String filePath) {

        org.w3c.dom.Document document = null;
        try {

            DocumentBuilder builder = documentFactory.newDocumentBuilder();
            document = builder.parse(new File(filePath));

            if (document != null)
                System.out.println("[INFO] File parsed with no errors.");
            else
                System.out.println("[WARN] Document is null.");

        } catch (Exception e) {
            return null;
        }

        return document;
    }

    public void generateHTML(String documentId) {

        try {
            String retrieved = existManager.retrieve(documentId);

            // Initialize Transformer instance
            StreamSource transformSource = new StreamSource(new File(xslFile));
            Transformer transformer = transformerFactory.newTransformer(transformSource);
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Generate XHTML
            transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

            // Transform DOM to HTML
            // Transform DOM to HTML
            DocumentBuilder builder = documentFactory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(new InputSource(new StringReader(retrieved)));
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(output_file + documentId + ".html"));
            transformer.transform(source, result);
            System.out.println("[INFO] Done");

        } catch (Exception e) {
            System.out.println("EX");
            e.printStackTrace();
        }
    }
}
