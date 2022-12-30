package xml.stamp.service.stamp.service.fuseki;

import java.io.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.xalan.processor.TransformerFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.stamp.service.stamp.service.model.RequestForStamp;

@Component
public class MetadataExtractor {

    private TransformerFactory transformerFactory = new TransformerFactoryImpl();

    private final String XSLT_FILE = "data/xsl/grddl.xsl";
    private final String RDF_FILE ="data/rdf/rdfOutput.rdf";

 /*   public MetadataExtractor() throws SAXException, IOException {

        // Setup the XSLT transformer factory
        transformerFactory = new TransformerFactoryImpl();
    }*/

    /**
     * Generates RDF/XML based on RDFa metadata from an XML containing
     * input stream by applying GRDDL XSL transformation.
     *
     * @param in XML containing input stream
     */
    //       * @param out  RDF/XML output stream
    public void extractMetadata(String in) throws FileNotFoundException, TransformerException {

        // Create transformation source
        StreamSource transformSource = new StreamSource(new File(XSLT_FILE));
        OutputStream out = new FileOutputStream(new File(RDF_FILE));

        // Initialize GRDDL transformer object
        Transformer grddlTransformer = transformerFactory.newTransformer(transformSource);

        // Set the indentation properties
        grddlTransformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
        grddlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Initialize transformation subject
        StreamSource source = new StreamSource(new StringReader(in));

        // Initialize result stream
        StreamResult result = new StreamResult(out);

        // Trigger the transformation
        grddlTransformer.transform(source, result);
    }


    public void test() throws Exception {

        System.out.println("[INFO] " + MetadataExtractor.class.getSimpleName());

        String filePath = "gen/grddl_metadata.rdf";

        InputStream in = new FileInputStream(new File("data/rdfa/contacts.xml"));

        OutputStream out = new FileOutputStream(filePath);

       // extractMetadata(in, out);

        System.out.println("[INFO] File \"" + filePath + "\" generated successfully.");

        System.out.println("[INFO] End.");

    }
}
