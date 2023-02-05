package xml.authorship.service.authorship.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xml.authorship.service.authorship.service.beans.AuthorshipRequest;
import xml.authorship.service.authorship.service.db.ExistManager;

import java.io.OutputStream;
import java.time.LocalDateTime;

@Repository
public class AuthorshipRepository {
    private final String collectionId = "/db/request/authorship";

    @Autowired
    private ExistManager existManager;


    public void saveRequest(OutputStream outputStream, String id) throws Exception {
        existManager.storeInExist(id, outputStream);
    }

    public void saveAuthorshipRequest(AuthorshipRequest authorshipRequest) throws Exception {
        existManager.storeFromAuthorshipRequest(authorshipRequest);
    }

//    public String getAuthorshipRequestById(String documentId) throws Exception {
//        return existManager.retrieve(documentId);              /*"113.xml"*/
//    }


}
