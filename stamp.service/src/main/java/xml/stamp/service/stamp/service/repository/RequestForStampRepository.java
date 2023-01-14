package xml.stamp.service.stamp.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xml.stamp.service.stamp.service.db.ExistManager;
import xml.stamp.service.stamp.service.model.RequestForStamp;

import java.io.OutputStream;
import java.time.LocalDateTime;

@Repository
public class RequestForStampRepository {

    private String collectionId = "/db/request/stamps";
    @Autowired
    private ExistManager existManager;


    public void saveRequest(OutputStream outputStream) throws Exception {
        existManager.storeFromText(collectionId, generateDocumentId(), outputStream);
    }

    public void saveRequestFromRequest(RequestForStamp requestForStamp) throws Exception {
        existManager.storeFromRequestForStamp(requestForStamp);
    }

    public String getRequestForStampById(String documentId) throws Exception {
        return existManager.retrieve(documentId);              /*"111.xml"*/
    }

    public String filter() throws Exception {
        return existManager.filter();
    }

    private String generateDocumentId() {
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append("Ž-");
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
