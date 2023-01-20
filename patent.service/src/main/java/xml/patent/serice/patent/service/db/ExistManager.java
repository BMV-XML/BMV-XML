package xml.patent.serice.patent.service.db;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.jaxb.LoaderValidation;
import xml.patent.serice.patent.service.repository.PatentRepository;
import xml.patent.serice.patent.service.util.AuthenticationUtilities;

import javax.xml.transform.OutputKeys;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExistManager {
    @Autowired
    private AuthenticationUtilities authManager;
    @Autowired
    private LoaderValidation loader;

    private String collectionId = "/db/request/patent";

    public void storeFromPatentRequest(PatentRequest patentRequest) throws Exception {
        String collectionId = "/db/request/patent";
        String documentId = "112.xml";//generate patent xml

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

            //   RequestForStamp patentRequest = this.loader.unmarshalling(xmlString);

            os = loader.marshalling(patentRequest, os);

            // link the stream to the XML resource
            res.setContent(os);
            System.out.println("[INFO] Storing the document: " + res.getId());

            col.storeResource(res);
            System.out.println("[INFO] Done.");

        } finally {
            closeConnection(col, res);
        }
    }

    public void storeInExist(String documentId, OutputStream outputStream) throws Exception {

        //documentId = "patent.xml";//generate patent xml

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

    public PatentRequest retrieve(String documentId) throws Exception {
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
                return null;
            } else {

                System.out.println("[INFO] Showing the document as XML resource: ");
                System.out.println(res.getContent());

                PatentRequest request = loader.unmarshalling((String) res.getContent());
                return request;
            }
        } finally {
            closeConnection(col, res);
        }
    }

    public List<PatentRequest> retrieveCollection() throws Exception {
        createConnection();
        Collection col = null;
        XMLResource res = null;

        List<PatentRequest> result = new ArrayList<>();
        try {
            // get the collection
            System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = DatabaseManager.getCollection(authManager.getUri() + collectionId);
            col.setProperty(OutputKeys.INDENT, "yes");

            System.out.println("[INFO] ************** RETRIVE collection");
            System.out.println(col.getResourceCount());
            for (String s : col.listResources())
                result.add(retrieve(s));
            return result;
        } finally {
            closeConnection(col, res);
        }
    }

    public String retrieveString(String documentId) throws Exception {
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
                return null;
            } else {

                System.out.println("[INFO] Showing the document as XML resource: ");
                System.out.println(res.getContent());

                return (String) res.getContent();
            }
        } finally {
            closeConnection(col, res);
        }
    }

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
