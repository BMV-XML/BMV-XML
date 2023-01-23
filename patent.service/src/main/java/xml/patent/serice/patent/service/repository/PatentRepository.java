package xml.patent.serice.patent.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.db.ExistManager;

import java.io.OutputStream;
import java.time.LocalDateTime;

@Repository
public class PatentRepository {
    private String collectionId = "/db/request/patent";
    @Autowired
    private ExistManager existManager;

    public void saveRequest(OutputStream outputStream, String withName) throws Exception {
        existManager.storeInExist(withName, outputStream);
        //"1.xml" -- documentId
    }

    private String generateDocumentId() {
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append("P-");
        /*sb.append(now.getDayOfMonth());
        sb.append(now.getMonth());*/
        sb.append(now.getDayOfYear());
        sb.append(now.getHour());
        sb.append(now.getMinute());
        sb.append(now.getSecond());
        sb.append("-");
        String year = String.valueOf(now.getYear()).substring(2,4);
        sb.append(year);
        return sb.toString();
    }
}
