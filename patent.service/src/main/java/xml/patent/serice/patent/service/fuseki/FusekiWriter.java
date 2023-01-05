package xml.patent.serice.patent.service.fuseki;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.patent.serice.patent.service.util.AuthenticationUtilities;
import xml.patent.serice.patent.service.util.SparqlUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;


@Component
public class FusekiWriter {

    @Autowired
    private AuthenticationUtilities authManager;


    private static final String GRAPH_URI = "/patent/metadata";

    public void saveRDF(OutputStream outputStream) {

        System.out.println("[INFO] Loading triples from an RDF/XML to a model...");

        InputStream inputStream = new ByteArrayInputStream(outputStream.toString().getBytes(Charset.forName("UTF-8")));
        // Creates a default model
        Model model = ModelFactory.createDefaultModel();
        model.read(inputStream, "");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        model.write(out, SparqlUtil.NTRIPLES);
        model.write(System.out, SparqlUtil.NTRIPLES);

        System.out.println("[INFO] Rendering model as RDF/XML...");
        //   model.write(System.out, SparqlUtil.RDF_XML);


        // Creating the first named graph and updating it with RDF data
        System.out.println("[INFO] Writing the triples to a named graph \"" + GRAPH_URI + "\".");
        String sparqlUpdate = SparqlUtil.insertData(authManager.getFullDataEndpoint()+ GRAPH_URI, new String(out.toByteArray()));
        System.out.println(sparqlUpdate);

        // UpdateRequest represents a unit of execution
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);
        /*
         * Create UpdateProcessor, an instance of execution of an UpdateRequest.
         * UpdateProcessor sends update request to a remote SPARQL update service.
         */
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, authManager.getFulUpdateEndpoint());
        System.out.println("Context**********");
        System.out.println(processor.getContext());
        processor.execute();
    }
}
