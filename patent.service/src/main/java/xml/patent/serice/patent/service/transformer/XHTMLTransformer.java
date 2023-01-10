package xml.patent.serice.patent.service.transformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import xml.patent.serice.patent.service.db.ExistManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;

@Service
public class XHTMLTransformer {

    private static DocumentBuilderFactory documentFactory;

    private static TransformerFactory transformerFactory;

    private String xslFile = "data/xhtml/patent.xsl";

    private String output_path = "src/main/resources/static/html/";

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

    public void generateHTML(String documentId) {

        try {
            String retrieved = existManager.retrieveString(documentId);


            // Initialize Transformer instance
            StreamSource transformSource = new StreamSource(new File(xslFile));
            Transformer transformer = transformerFactory.newTransformer(transformSource);
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Generate XHTML
            transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

            // Transform DOM to HTML
            DocumentBuilder builder = documentFactory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(new InputSource(new StringReader(retrieved)));
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(output_path + documentId + ".html"));
            transformer.transform(source, result);
            System.out.println("[INFO] Done");
        } catch (Exception e) {
            System.out.println("EX");
            e.printStackTrace();
        }

    }

}
