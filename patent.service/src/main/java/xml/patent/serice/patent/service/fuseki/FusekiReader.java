package xml.patent.serice.patent.service.fuseki;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.patent.serice.patent.service.util.AuthenticationUtilities;
import xml.patent.serice.patent.service.util.FileUtil;
import xml.patent.serice.patent.service.util.SparqlUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class FusekiReader {

    @Autowired
    AuthenticationUtilities authManager;

    private static final String SPARQL_NAMED_GRAPH_URI = "/patent/metadata";//"/patent/sparql/metadata";

    private static final String TEST_NAMED_GRAPH_URI = "/example/test/metadata";
    private static final String PERSON_NAMED_GRAPH_URI = "/example/patent/metadata";

    private final String QUERY_FILEPATH = "data/rdf/q1.rq";

    public void run() throws IOException {

        // Querying the first named graph with a simple SPARQL query
        System.out.println("[INFO] Selecting the triples from the named graph \"" + SPARQL_NAMED_GRAPH_URI + "\".");
        String sparqlQuery = SparqlUtil.selectData(authManager.getFullDataEndpoint(), "?s ?p ?o");
        System.out.println(sparqlQuery);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(authManager.getFullQueryEndpoint(), sparqlQuery);
        System.out.println(query);
        // Query the SPARQL endpoint, iterate over the result set...
        ResultSet results = query.execSelect();

        String varName;
        RDFNode varValue;

        while(results.hasNext()) {

            // A single answer from a SELECT query
            QuerySolution querySolution = results.next() ;
            Iterator<String> variableBindings = querySolution.varNames();

            // Retrieve variable bindings
            while (variableBindings.hasNext()) {

                varName = variableBindings.next();
                varValue = querySolution.get(varName);

                System.out.println(varName + ": " + varValue);
            }
            System.out.println();
        }

        // Querying the other named graph
        System.out.println("[INFO] Selecting the triples from the named graph \"" + SPARQL_NAMED_GRAPH_URI + "\".");
        sparqlQuery = SparqlUtil.selectData(authManager.getFullDataEndpoint() + SPARQL_NAMED_GRAPH_URI, "?s ?p ?o");

        // Create a QueryExecution that will access a SPARQL service over HTTP
        query = QueryExecutionFactory.sparqlService(authManager.getFullQueryEndpoint(), sparqlQuery);


        // Query the collection, dump output response as XML
        results = query.execSelect();

        //ResultSetFormatter.outputAsXML(System.out, results);
        //List<QuerySolution> res = ResultSetFormatter.toList(results);
        System.out.println("**************************************************");
        for (ResultSet it = results; it.hasNext(); ) {
            QuerySolution qs = it.next();
            System.out.println(qs);
        }

        query.close() ;

        System.out.println("[INFO] End.");
    }

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



