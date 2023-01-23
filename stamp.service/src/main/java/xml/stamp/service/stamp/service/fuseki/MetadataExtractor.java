package xml.stamp.service.stamp.service.fuseki;

import java.io.*;



import org.springframework.stereotype.Component;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;


@Component
public class MetadataExtractor {

    private TransformerFactory transformerFactory = new TransformerFactoryImpl();

    private final String XSLT_FILE = "data/xsl/grddl.xsl";


    /**
     * Generates RDF/XML based on RDFa metadata from an XML containing
     * input stream by applying GRDDL XSL transformation.
     *
     * @param in XML containing input stream
     */
    //       * @param out  RDF/XML output stream
    public OutputStream extractMetadata(String in, OutputStream out) throws FileNotFoundException, TransformerException {

        // Create transformation source
        StreamSource transformSource = new StreamSource(new File(XSLT_FILE));

        // Initialize GRDDL transformer object
        Transformer grddlTransformer = transformerFactory.newTransformer(transformSource);

        // Set the indentation properties
        grddlTransformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
        grddlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

        InputStream inputStream = new ByteArrayInputStream(in.getBytes());

        // Initialize transformation subject
        StreamSource source = new StreamSource(inputStream);

        // Initialize result stream
        StreamResult result = new StreamResult(out);

        // Trigger the transformation
        grddlTransformer.transform(source, result);

        System.out.println(" extracted ");
        System.out.println(out.toString());
        return out;
    }
}
