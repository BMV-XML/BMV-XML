package xml.patent.serice.patent.service.fuseki;

import org.apache.commons.text.StringSubstitutor;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.patent.serice.patent.service.dto.FilterDTO;
import xml.patent.serice.patent.service.dto.FilterElements;
import xml.patent.serice.patent.service.util.AuthenticationUtilities;
import xml.patent.serice.patent.service.util.FileUtil;
import xml.patent.serice.patent.service.util.SparqlUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static xml.patent.serice.patent.service.util.SparqlUtil.NTRIPLES;

@Component
public class FusekiReader {

    @Autowired
    AuthenticationUtilities authManager;

    private static final String SPARQL_NAMED_GRAPH_URI = "/patent/metadata";

    private final String QUERY_FILEPATH = "data/rdf/filterTitle.rq";

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
        String sparqlQuery = FileUtil.readFile(QUERY_FILEPATH, StandardCharsets.UTF_8);
        //String sparqlQuery = String.format(queryTemplate,
        //        authManager.getDataEndpoint() + SPARQL_NAMED_GRAPH_URI);

        System.out.println(sparqlQuery);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(authManager.getFullQueryEndpoint()+"/patent/metadata", sparqlQuery);

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

    private static final String GRAPH_URI = "/patent/metadata";

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

    public HashSet<String> search(List<FilterDTO> elements) {
        StringBuilder conditionBuilder = new StringBuilder();
        List<String> contains = new ArrayList<>();
        for (FilterDTO elem : elements){
            boolean alreadyContains = false;
            for (String e : contains)
                if (elem.getType().equals(e)) {alreadyContains = true; break;}
            if (!alreadyContains) {
                contains.add(elem.getType());
                conditionBuilder.append("?patent <http://www.ftn.uns.ac.rs/rdf/patent/predicate/" + elem.getType() + "> ?" + elem.getType() + ". ");
            }
        }
        conditionBuilder.append("filter (");
        int index = 0;
        String lastOperator = "";
        /*
                "?patent <http://www.ftn.uns.ac.rs/rdf/patent/predicate/sibling> ?sibling ." +
                "filter (?sibling = <http://www.ftn.uns.ac.rs/rdf/patent/P-32123344-23>)"
         */
        for (FilterDTO elem : elements) {
            conditionBuilder.append(lastOperator);
            elem.setValue(elem.getValue().replace("/", "-"));
            switch (elem.getOperator()){
                case "i": {
                    //lastOperator = "&&";
                    if (index != 0) conditionBuilder.append(" && ");
                    if (elem.getType().equals("sibling") || elem.getType().equals("child") || elem.getType().equals("additional"))
                        addHrefSearch(conditionBuilder, elem);
                    else
                        conditionBuilder.append("CONTAINS(UCASE(str(?" + elem.getType() + ")), UCASE('" + elem.getValue() + "'))");
                    break;
                }
                case "ili": {
                    //lastOperator = "||";
                    if (index != 0) conditionBuilder.append(" || ");
                    if (elem.getType().equals("sibling") || elem.getType().equals("child") || elem.getType().equals("additional"))
                        addHrefSearch(conditionBuilder, elem);
                    else
                        conditionBuilder.append("CONTAINS(UCASE(str(?" + elem.getType() + ")), UCASE('" + elem.getValue() + "'))");
                    break;
                }case "ne": {
                    ///lastOperator = "&&";
                    if (index != 0) conditionBuilder.append(" && ");
                    if (elem.getType().equals("sibling") || elem.getType().equals("child") || elem.getType().equals("additional"))
                        addHrefSearch(conditionBuilder, elem);
                    else
                        conditionBuilder.append("!CONTAINS(UCASE(str(?" + elem.getType() + ")), UCASE('" + elem.getValue() + "'))");
                    break;
                }
            }
            index++;
        }
        conditionBuilder.append(")");
        //String condition = "?patent <http://www.ftn.uns.ac.rs/rdf/patent/predicate/datum_prijave> ?date . " +
        //"filter ( ?date  \"" + "2023-01-24" + "\"^^xs:date )";
        //    "filter CONTAINS(UCASE(str(?date)), UCASE(\"" + searchBy + "\"))";
        String sparqlQuery = SparqlUtil.selectData(authManager.getFullDataEndpoint() + GRAPH_URI,
                conditionBuilder.toString());

        QueryExecution query = QueryExecutionFactory.sparqlService(authManager.getFullQueryEndpoint(), sparqlQuery);

        ResultSet results = query.execSelect();
        HashSet<String> foundPatents = new HashSet<>();
        while (results.hasNext()) {
            QuerySolution querySolution = results.next();
            String[] patentUrl = querySolution.getResource("patent").toString().split("/");
            foundPatents.add(patentUrl[patentUrl.length-1]);
        }
        System.out.println("***********************************");
        for (String founds : foundPatents){
            System.out.println(founds);
        }
        query.close();
        return foundPatents;
    }

    private void addHrefSearch(StringBuilder conditionBuilder, FilterDTO elem) {
        /*
                "?patent <http://www.ftn.uns.ac.rs/rdf/patent/predicate/sibling> ?sibling ." +
                "filter (?sibling = <http://www.ftn.uns.ac.rs/rdf/patent/P-32123344-23>)"
         */
        conditionBuilder.append("?" + elem.getType() + " = <http://www.ftn.uns.ac.rs/rdf/patent/"+ elem.getValue() + ">");
    }

    public List<String> searchByUser(String type, String searchBy){
        //Map<String, String> params = new HashMap<>();
        //params.put("moj_naslov", "Marko Markovic");
        //String name = "Marko Markovic";
        List<String> appendConstrains = new ArrayList<>();
        for (String n : searchBy.split(" ")){
            appendConstrains.add("CONTAINS(UCASE(str(?podnosilac)), UCASE('" + n +"'))");
        }
        StringBuilder sb = new StringBuilder();
        String condition = "?patent <http://www.ftn.uns.ac.rs/rdf/patent/predicate/"+ type + "> ?podnosilac ." +
                "filter (";// CONTAINS(UCASE(str(?podnosilac)), UCASE('{{moj_naslov}}'))";
        sb.append(condition);
        int index = 0;
        for (String app : appendConstrains){
            sb.append(app);
            if ( appendConstrains.size() > 1 && index != appendConstrains.size() - 1)
                sb.append(" && ");
            if (index == appendConstrains.size() - 1)
                sb.append(")");
            index++;
        }
        condition = sb.toString();
        //condition = StringSubstitutor.replace(condition, params, "{{", "}}");
        String sparqlQuery = SparqlUtil.selectData(authManager.getFullDataEndpoint() + GRAPH_URI,
                condition);

        QueryExecution query = QueryExecutionFactory.sparqlService(authManager.getFullQueryEndpoint(), sparqlQuery);

        ResultSet results = query.execSelect();
        List<String> foundPatents = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution querySolution = results.next();
            String[] patentUrl = querySolution.getResource("patent").toString().split("/");
            foundPatents.add(patentUrl[patentUrl.length-1]);
        }
        System.out.println("***********************************");
        for (String founds : foundPatents){
            System.out.println(founds);
        }
        query.close();
        return foundPatents;
    }

    public void filter() {
        StringBuilder sb = new StringBuilder();
        String condition =
                "?patent <http://www.ftn.uns.ac.rs/rdf/patent/predicate/sibling> ?sibling ." +
                "filter (?sibling = <http://www.ftn.uns.ac.rs/rdf/patent/P-32123344-23>)";
        sb.append(condition);

        condition = sb.toString();
        //condition = StringSubstitutor.replace(condition, params, "{{", "}}");
        String sparqlQuery = SparqlUtil.selectData(authManager.getFullDataEndpoint() + GRAPH_URI,
                condition);

        QueryExecution query = QueryExecutionFactory.sparqlService(authManager.getFullQueryEndpoint(), sparqlQuery);

        ResultSet results = query.execSelect();
        List<String> foundPatents = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution querySolution = results.next();
            String[] patentUrl = querySolution.getResource("patent").toString().split("/");
            foundPatents.add(patentUrl[patentUrl.length-1]);
        }
        System.out.println("***********************************");
        for (String founds : foundPatents){
            System.out.println(founds);
        }
        query.close();
        //return foundPatents;
    }
}



