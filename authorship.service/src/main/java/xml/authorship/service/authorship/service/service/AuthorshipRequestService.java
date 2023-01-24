package xml.authorship.service.authorship.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.authorship.service.authorship.service.beans.AuthorshipRequest;
import xml.authorship.service.authorship.service.fuseki.FusekiReader;
import xml.authorship.service.authorship.service.fuseki.FusekiWriter;
import xml.authorship.service.authorship.service.fuseki.MetadataExtractor;
import xml.authorship.service.authorship.service.jaxb.LoaderValidation;
import xml.authorship.service.authorship.service.repository.AuthorshipRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static xml.authorship.service.authorship.service.util.SparqlUtil.*;

@Service
public class AuthorshipRequestService {

    @Autowired
    private AuthorshipRepository authorshipRepository;

    @Autowired
    private LoaderValidation loader;

    @Autowired
    private MetadataExtractor metadataExtractor;

    @Autowired
    private FusekiWriter fusekiWriter;

    @Autowired
    private FusekiReader fusekiReader;

    public String saveAuthorshipRequest(AuthorshipRequest authorshipRequest) throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        os = this.loader.marshalling(authorshipRequest, os);
        authorshipRepository.saveRequest(os);
        OutputStream outputStream = new ByteArrayOutputStream();
        outputStream = metadataExtractor.extractMetadata(os.toString(), outputStream);
        fusekiWriter.saveRDF(outputStream);
        return "nestooo";
    }

    public ArrayList<String> searchByMetadata(String name) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("naziv", name);

        ArrayList<String> result = fusekiReader.executeQuery(params);
        return  result;
    }

    public String searchMetadataById(String id, String type) {
        String graphUri = "/authorship/metadata";
        if (type.equals(RDF_JSON)) {
            String sparqlCondition = "VALUES ?subject { <" + "http://www.ftn.uns.ac.rs/rdf/authorship/" + id + "> }" +
                    " ?subject ?predicate ?object .";
//        String sparqlCondition = "<http://www.ftn.uns.ac.rs/rdf/authorship/" + id + "> ?d ?s .";
            return fusekiReader.getMetadataJson(graphUri, sparqlCondition);
        } else if (type.equals(NTRIPLES)) {
            String sparqlCondition = "<http://www.ftn.uns.ac.rs/rdf/authorship/" + id + "> ?predicate ?object .";
            return fusekiReader.getMetadataRdf(graphUri, sparqlCondition);
        }
        return null;
    }
}
