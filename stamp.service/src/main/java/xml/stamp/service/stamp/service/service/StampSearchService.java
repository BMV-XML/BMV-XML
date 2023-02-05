package xml.stamp.service.stamp.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.stamp.service.stamp.service.fuseki.FusekiReader;

import java.io.*;
import java.nio.charset.Charset;

import static xml.stamp.service.stamp.service.util.SparqlUtil.NTRIPLES;
import static xml.stamp.service.stamp.service.util.SparqlUtil.RDF_JSON;

@Service
public class StampSearchService {

    @Autowired
    private FusekiReader fusekiReader;

    public String searchMetadataById(String id, String type) throws IOException {
        System.out.println("search by metadata: ID: " + id);

        String graphUri = "/stamp/metadata";
        if (type.equals(RDF_JSON)) {
            String sparqlCondition = "VALUES ?subject { <" + "http://www.ftn.uns.ac.rs/rdf/stamp/" + id + "> }" +
                    " ?subject ?predicate ?object .";
            saveFile("src/main/resources/static/json/" + id + ".json", fusekiReader.getMetadataJson(graphUri, sparqlCondition));
        } else if (type.equals(NTRIPLES)) {
            String sparqlCondition = "<http://www.ftn.uns.ac.rs/rdf/stamp/" + id + "> ?predicate ?object .";
            saveFile("src/main/resources/static/rdf/" + id + ".rdf", fusekiReader.getMetadataRdf(graphUri, sparqlCondition));
        }
        return null;
    }

    public void saveFile(String output_path, String text) throws IOException {
        File pdfFile = new File((output_path));
        if (!pdfFile.getParentFile().exists()) {
            System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
            pdfFile.getParentFile().mkdir();
        }

        OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
        out.write(text.getBytes(Charset.forName("UTF-8")));

        System.out.println("[INFO] File \"" + pdfFile.getCanonicalPath() + "\" generated successfully.");
        out.close();
    }
}
