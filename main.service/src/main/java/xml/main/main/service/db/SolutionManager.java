package xml.main.main.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;
import xml.main.main.service.beans.Solutions;
import xml.main.main.service.jaxb.SolutionLoader;

import javax.xml.transform.OutputKeys;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Service
public class SolutionManager extends ExistManager{
    String collectionId = "/db/xml";
    String documentId = "solutions.xml";//generate patent xml
    @Autowired
    private SolutionLoader solutionLoader;

    public void storeSolutionFromObj(Solutions solutions) throws Exception {

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

            os = solutionLoader.marshalling(solutions, os);

            // link the stream to the XML resource
            res.setContent(os);
            System.out.println("[INFO] Storing the document: " + res.getId());

            col.storeResource(res);
            System.out.println("[INFO] Done.");

        } finally {
            closeConnection(col, res);
        }
    }

    public Solutions retrieve() throws Exception {
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


                return solutionLoader.unmarshalling((String) res.getContent());
            }
        } finally {
            closeConnection(col, res);
        }
    }
}
