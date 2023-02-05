package xml.stamp.service.stamp.service.fuseki;

import org.apache.commons.text.StringSubstitutor;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.stamp.service.stamp.service.util.AuthenticationUtilities;
import xml.stamp.service.stamp.service.util.SparqlUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static xml.stamp.service.stamp.service.util.SparqlUtil.NTRIPLES;

@Component
public class FusekiReader {

    @Autowired
    AuthenticationUtilities authManager;

    private final String QUERY_FILEPATH = "data/rdf/";

    public ArrayList<String> executeQuery(Map<String, String> params) throws IOException {
        // Querying the first named graph with a simple SPARQL query
        System.out.println("[INFO] Selecting the triples from the named graph .");
        String sparqlQueryTemplate = readFile(QUERY_FILEPATH, StandardCharsets.UTF_8);
        System.out.println("Query: " +  sparqlQueryTemplate);

        String sparqlQuery = StringSubstitutor.replace(sparqlQueryTemplate, params, "{{","}}");
        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(authManager.getQueryEndpoint(), sparqlQuery);

        // Query the SPARQL endpoint, iterate over the result set...
        ResultSet results = query.execSelect();

        String varName;
        RDFNode varValue;


        ArrayList<String> found = new ArrayList<>();  // ovo ce zavisiti od upita
        while (results.hasNext()) {

            // A single answer from a SELECT query
            QuerySolution querySolution = results.next();
            Iterator<String> variableBindings = querySolution.varNames();

            // Retrieve variable bindings
            while (variableBindings.hasNext()) {

                varName = variableBindings.next();
                varValue = querySolution.get(varName);

                System.out.println(varName + ": " + varValue);
                if(varName.contains("naziv")){
                    String value = varValue.toString();
                    found.add(value);
                }
            }
            System.out.println();
        }
        ResultSetFormatter.outputAsXML(System.out, results);
        query.close();
        return found;
    }

    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public String getMetadataJson(String graphUri, String sparqlQueryCondition) {
        System.out.println("[INFO] Selecting the triples from the named graph \"" + graphUri + "\".");
        String sparqlQuery = SparqlUtil.selectData(authManager.getFullDataEndpoint() + graphUri, sparqlQueryCondition);
        System.out.println(sparqlQuery);
        QueryExecution query = QueryExecutionFactory.sparqlService(authManager.getFullQueryEndpoint(), sparqlQuery);
        ResultSet results = query.execSelect();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(out, results);
        String json = new String(out.toByteArray(), StandardCharsets.UTF_8);
        int indexOfSubStr = json.indexOf("bindings");
        String file = String.format("{\n  %s", json.substring(indexOfSubStr - 1));
        query.close();
        return file;
    }

    public String getMetadataRdf(String graphUri, String sparqlQueryCondition) {
        String sparqlQuery = SparqlUtil.constructData(authManager.getFullDataEndpoint() + graphUri, sparqlQueryCondition);
        System.out.println(sparqlQuery);
        QueryExecution query = QueryExecutionFactory.sparqlService(authManager.getFullQueryEndpoint(), sparqlQuery);
        Model model = query.execConstruct();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        model.write(out, NTRIPLES);

        return out.toString();
    }
}



