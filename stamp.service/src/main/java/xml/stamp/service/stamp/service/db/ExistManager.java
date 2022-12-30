package xml.stamp.service.stamp.service.db;

import org.exist.xmldb.EXistResource;
import org.exist.xupdate.XUpdateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;


import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;
import xml.stamp.service.stamp.service.jaxb.JaxLoader;
import xml.stamp.service.stamp.service.model.RequestForStamp;
import xml.stamp.service.stamp.service.util.AuthenticationUtilities;

import javax.xml.transform.OutputKeys;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Service
public class ExistManager {
    @Autowired
    private AuthenticationUtilities authManager;
    @Autowired
    private JaxLoader loader;

    private final static String TARGET_NAMESPACE = "http://www.ftn.uns.ac.rs/stamp";

    public static final String UPDATE = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
            + "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:update select=\"%1$s\">%2$s</xu:update>"
            + "</xu:modifications>";

    public void createConnection() throws Exception {
        Class<?> cl = Class.forName(authManager.getDriver());
        // encapsulation of the database driver functionality
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");

        // entry point for the API which enables you to get the Collection reference
        DatabaseManager.registerDatabase(database);
    }

    public void closeConnection(Collection col, XMLResource res) throws XMLDBException {
        if (col != null) {
            col.close();
        }
        if (res != null) {
            ((EXistResource) res).freeResources();
        }
    }


    public void storeFromRequestForStamp(RequestForStamp requestForStamp) throws Exception {
        String collectionId = "/db/sample/library";
        String documentId = "112.xml";

        System.out.println("\t- collection ID: " + collectionId);
        System.out.println("\t- document ID: " + documentId);

        // a collection of Resources stored within an XML database
        createConnection();
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();
        try {
            col = getOrCreateCollection(collectionId);
            System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = getOrCreateCollection(collectionId);


            System.out.println("[INFO] Inserting the document: " + documentId);
            res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);

         //   RequestForStamp requestForStamp = this.loader.unmarshalling(xmlString);

            os = loader.marshalling(requestForStamp, os);

            // link the stream to the XML resource
            res.setContent(os);
            System.out.println("[INFO] Storing the document: " + res.getId());

            col.storeResource(res);
            System.out.println("[INFO] Done.");

        } finally {
            closeConnection(col, res);
        }
    }


    public void storeFromText(String collectionId, String documentId, OutputStream outputStream) throws Exception {
        collectionId = "/db/sample/library";
        documentId = "115.xml";

        System.out.println("\t- collection ID: " + collectionId);
        System.out.println("\t- document ID: " + documentId);

        // a collection of Resources stored within an XML database
        createConnection();
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();

        try {
            col = getOrCreateCollection(collectionId);
            System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = getOrCreateCollection(collectionId);

            /*
             *  create new XMLResource with a given id
             *  an id is assigned to the new resource if left empty (null)
             */
            System.out.println("[INFO] Inserting the document: " + documentId);
            res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);

            // link the stream to the XML resource
            res.setContent(outputStream);
            System.out.println("[INFO] Storing the document: " + res.getId());

            col.storeResource(res);
            System.out.println("[INFO] Done.");

        } finally {
            closeConnection(col, res);
        }
    }

    public String retrieve(String collectionId, String documentId) throws Exception {
        collectionId = "/db/sample/library";
        documentId = "111.xml";
        createConnection();
        Collection col = null;
        XMLResource res = null;

        try {
            // get the collection
            System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = DatabaseManager.getCollection(authManager.getUri() + collectionId);
            col.setProperty(OutputKeys.INDENT, "yes");

            System.out.println("[INFO] Retrieving the document: " + documentId);
            res = (XMLResource) col.getResource(documentId);

            if (res == null) {
                System.out.println("[WARNING] Document '" + documentId + "' can not be found!");
                return "";
            } else {

                System.out.println("[INFO] Showing the document as XML resource: ");
                System.out.println(res.getContent());

                RequestForStamp requestForStamp = loader.unmarshalling((String) res.getContent());
                return (String) res.getContent();
            }
        } finally {
            closeConnection(col, res);
        }
    }

    public String filter() throws Exception {
        String collectionId = "/db/sample/library";
        String xpathExp = "//Zahtev_za_zig/Podaci_o_zigu";
        createConnection();
        Collection col = null;
        Resource res = null;
        try {
            System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = DatabaseManager.getCollection(authManager.getUri() + collectionId);

            // get an instance of xpath query service
            XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpathService.setProperty("indent", "yes");

            // make the service aware of namespaces, using the default one
            xpathService.setNamespace("", TARGET_NAMESPACE);
            ResourceSet result = xpathService.query(xpathExp);

            // handle the results
            System.out.println("[INFO] Handling the results... ");

            ResourceIterator i = result.getIterator();
            res = null;

            while(i.hasMoreResources()) {

                try {
                    res = i.nextResource();
                    System.out.println(res.getContent());
                    return res.getContent().toString();

                } finally {
                    // don't forget to cleanup resources
                    try {
                        ((EXistResource)res).freeResources();
                    } catch (XMLDBException xe) {
                        xe.printStackTrace();
                    }
                }
            }
            return "";

        } finally {
            closeConnection(col, (XMLResource) res);
        }
    }

    public void update(String document, String contextXPath, String patch)
            throws Exception  {
        createConnection();
        String collectionId = "/db/sample/library";
        Collection col = null;
        String chosenTemplate = UPDATE;
        try {
            col = DatabaseManager.getCollection(authManager.getUri() + collectionId, authManager.getUser(),
                    authManager.getPassword());
            XUpdateQueryService service = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            service.setProperty("indent", "yes");
            service.updateResource(document, String.format(chosenTemplate, contextXPath, patch));
        } finally {
            if (col != null) {
                col.close();
            }
        }
    }


    private Collection getOrCreateCollection(String collectionUri) throws XMLDBException {
        return getOrCreateCollection(collectionUri, 0);
    }

    private Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {

        Collection col = DatabaseManager.getCollection(authManager.getUri() + collectionUri, authManager.getUser(), authManager.getPassword());

        // create the collection if it does not exist
        if(col == null) {

            if(collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }

            String pathSegments[] = collectionUri.split("/");

            if(pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();

                for(int i = 0; i <= pathSegmentOffset; i++) {
                    path.append("/" + pathSegments[i]);
                }

                Collection startCol = DatabaseManager.getCollection(authManager.getUri() + path, authManager.getUser(), authManager.getPassword());

                if (startCol == null) {

                    // child collection does not exist

                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection(authManager.getUri() + parentPath, authManager.getUser(), authManager.getPassword());

                    CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");

                    System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);

                    col.close();
                    parentCol.close();

                } else {
                    startCol.close();
                }
            }
            return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
        } else {
            return col;
        }
    }

}
