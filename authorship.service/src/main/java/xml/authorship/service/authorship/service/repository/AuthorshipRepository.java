package xml.authorship.service.authorship.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xml.authorship.service.authorship.service.beans.AuthorshipRequest;
import xml.authorship.service.authorship.service.db.ExistManager;

import java.io.OutputStream;
import java.time.LocalDateTime;

@Repository
public class AuthorshipRepository {
    private final String collectionId = "/db/request/stamps";

    @Autowired
    private ExistManager existManager;


    public void saveRequest(OutputStream outputStream) throws Exception {
        existManager.storeInExist(generateDocumentId(), outputStream);
    }

    public void saveAuthorshipRequest(AuthorshipRequest authorshipRequest) throws Exception {
        existManager.storeFromAuthorshipRequest(authorshipRequest);
    }

    public String getAuthorshipRequestById(String documentId) throws Exception {
        return existManager.retrieve(documentId);              /*"113.xml"*/
    }


    private String generateDocumentId() {
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append("A-");
        /*sb.append(now.getDayOfMonth());
        sb.append(now.getMonth());*/
        sb.append(now.getDayOfYear());
        sb.append(now.getHour());
        sb.append(now.getMinute());
        sb.append(now.getSecond());
        sb.append("-");
        String year = String.valueOf(now.getYear()).substring(2, 4);
        sb.append(year);
        return sb.toString();
    }
}
