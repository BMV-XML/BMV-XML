package xml.patent.serice.patent.service.util;


public class SparqlUtil {

    /* The following operation causes all of the triples in all of the graphs to be deleted */
    private static final String DROP_ALL = "DROP ALL";

    /* Removes all of the triples from a named graphed */
    private static final String DROP_GRAPH_TEMPLATE = "DROP GRAPH <%s>";

    /**
     * A template for creating SPARUL (SPARQL Update) query can be found here:
     * https://www.w3.org/TR/sparql11-update/
     */
    /* Insert RDF data into the default graph */
    private static final String UPDATE_TEMPLATE = "INSERT DATA { %s }";

    /* Insert RDF data to an arbitrary named graph */
    private static final String UPDATE_TEMPLATE_NAMED_GRAPH = "INSERT DATA { GRAPH <%1$s> { %2$s } }";


    /* Simple SPARQL query on a named graph */
    private static final String SELECT_NAMED_GRAPH_TEMPLATE =
            "PREFIX xs:     <http://www.w3.org/2001/XMLSchema#>\n" +
            "SELECT * FROM <%1$s> WHERE { %2$s }";

    public static final String CONSTRUCT_NAMED_GRAPH_TEMPLATE = "CONSTRUCT FROM <%1$s> WHERE { %2$s }";

   // private static final String SELECT_NAMED_GRAPH_TEMPLATE_COMP
    //        = "SELECT * FROM <%1$s> WHERE { ?naslov <http://www.ftn.uns.ac.rs/rdf/patent/predicate/naslov> ?naslov . }";
    /* Plain text RDF serialization format */
    public static final String NTRIPLES = "N-TRIPLES";
    public static final String RDF_XML = "RDF/XML";

    /* An JSON serialization format for RDF data */
    public static final String RDF_JSON = "RDF/JSON";


    public static String dropAll() {
        return DROP_ALL;
    }

    public static String dropGraph(String graphURI) {
        return String.format(DROP_GRAPH_TEMPLATE, graphURI);
    }

    /* Inserts data to the default graph */
    public static String insertData(String ntriples) {
        return String.format(UPDATE_TEMPLATE, ntriples);
    }

    public static String insertData(String graphURI, String ntriples) {
        return String.format(UPDATE_TEMPLATE_NAMED_GRAPH, graphURI, ntriples);
    }

    public static String selectData(String graphURI, String sparqlCondition) {
        System.out.println(SELECT_NAMED_GRAPH_TEMPLATE);
        System.out.println(graphURI);
        System.out.println(sparqlCondition  );
        return String.format(SELECT_NAMED_GRAPH_TEMPLATE, graphURI, sparqlCondition);
    }

    public static String constructData(String graphURI, String sparqlCondition) {
        return String.format(CONSTRUCT_NAMED_GRAPH_TEMPLATE, graphURI, sparqlCondition);
    }
}
