package xml.authorship.service.authorship.service.fuseki;

import org.apache.commons.text.StringSubstitutor;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.authorship.service.authorship.service.util.AuthenticationUtilities;
import xml.authorship.service.authorship.service.util.SparqlUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static xml.authorship.service.authorship.service.util.SparqlUtil.NTRIPLES;
import static xml.authorship.service.authorship.service.util.SparqlUtil.RDF_XML;

@Component
public class FusekiReader {

    private final String QUERY_FILEPATH = "data/rdf/";

    @Autowired
    private AuthenticationUtilities authManager;

    private static final String SPARQL_NAMED_GRAPH_URI = "/authorship/metadata";//"/patent/sparql/metadata";

    private static final String TEST_NAMED_GRAPH_URI = "/example/test/metadata";
    private static final String PERSON_NAMED_GRAPH_URI = "/example/authorship/metadata";

//    private final String QUERY_FILEPATH = "data/rdf/q1.rq";

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

        query.close();

        System.out.println("[INFO] End.");
    }

    public ArrayList<String> executeQuery(Map<String, String> params) throws IOException {
        // Querying the first named graph with a simple SPARQL query
        System.out.println("[INFO] Selecting the triples from the named graph .");
        String sparqlQueryTemplate = readFile(QUERY_FILEPATH, StandardCharsets.UTF_8);
        System.out.println("Query: " + sparqlQueryTemplate);

        String sparqlQuery = StringSubstitutor.replace(sparqlQueryTemplate, params, "{{", "}}");
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
                if (varName.contains("naziv")) {
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
