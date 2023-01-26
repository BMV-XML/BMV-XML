package xml.patent.serice.patent.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.patent.serice.patent.service.fuseki.FusekiReader;

import java.io.*;
import java.nio.charset.Charset;

import static xml.patent.serice.patent.service.util.SparqlUtil.NTRIPLES;
import static xml.patent.serice.patent.service.util.SparqlUtil.RDF_JSON;

@Service
public class PatentSearchService {
    @Autowired
    private FusekiReader fusekiReader;

    public String searchMetadataById(String id, String type) throws IOException {
        String graphUri = "/patent/metadata";
        if (type.equals(RDF_JSON)) {
            String sparqlCondition = "VALUES ?subject { <" + "http://www.ftn.uns.ac.rs/rdf/patent/" + id + "> }" +
                    " ?subject ?predicate ?object .";
//        String sparqlCondition = "<http://www.ftn.uns.ac.rs/rdf/authorship/" + id + "> ?d ?s .";
            saveFile("src/main/resources/static/json/" + id + ".json", fusekiReader.getMetadataJson(graphUri, sparqlCondition));
        } else if (type.equals(NTRIPLES)) {
            String sparqlCondition = "<http://www.ftn.uns.ac.rs/rdf/patent/" + id + "> ?predicate ?object .";
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
