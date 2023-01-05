package xml.patent.serice.patent.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.db.ExistManager;

import java.io.OutputStream;

@Repository
public class PatentRepository {
    private String collectionId = "/db/request/patent";
    @Autowired
    private ExistManager existManager;

    public void saveRequest(OutputStream outputStream) throws Exception {
        existManager.storeInExist("","1.xml" , outputStream);
    }
}
