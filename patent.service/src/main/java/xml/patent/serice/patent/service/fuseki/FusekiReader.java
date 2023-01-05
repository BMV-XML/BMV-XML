package xml.patent.serice.patent.service.fuseki;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.patent.serice.patent.service.util.AuthenticationUtilities;
import xml.patent.serice.patent.service.util.FileUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Component
public class FusekiReader {

    @Autowired
    AuthenticationUtilities authManager;

    private static final String SPARQL_NAMED_GRAPH_URI = "/patent/sparql/metadata";

    private final String QUERY_FILEPATH = "data/rdf/query1.rq";

    public ArrayList<String> executeQuery(Map<String, String> params) throws IOException {
        // Querying the first named graph with a simple SPARQL query


        // Querying the named graph with a referenced SPARQL query
        System.out.println("[INFO] Loading SPARQL query from file \"" + QUERY_FILEPATH + "\"");
        String sparqlQuery = String.format(FileUtil.readFile(QUERY_FILEPATH, StandardCharsets.UTF_8),
                authManager.getDataEndpoint() + SPARQL_NAMED_GRAPH_URI);

        System.out.println(sparqlQuery);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(authManager.getFullQueryEndpoint(), sparqlQuery);

        System.out.println(query);
        /*
        System.out.println("[INFO] Selecting the triples from the named graph .");

        String sparqlQueryTemplate = readFile(QUERY_FILEPATH, StandardCharsets.UTF_8);
        System.out.println("Query: " +  sparqlQueryTemplate);

        String sparqlQuery = StringSubstitutor.replace(sparqlQueryTemplate, params, "{{","}}");
        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(authManager.getQueryEndpoint(), sparqlQuery);
*/
        // Query the SPARQL endpoint, iterate over the result set...
        ResultSet results = query.execSelect();
        System.out.println("RESULTS");
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
}



