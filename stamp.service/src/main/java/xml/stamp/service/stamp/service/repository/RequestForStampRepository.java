package xml.stamp.service.stamp.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xml.stamp.service.stamp.service.db.ExistManager;
import xml.stamp.service.stamp.service.model.RequestForStamp;

import java.io.OutputStream;

@Repository
public class RequestForStampRepository {

    private String collectionId = "/db/request/stamps";
    @Autowired
    private ExistManager existManager;

    // ID generator

    public void saveRequest(OutputStream outputStream) throws Exception {
        existManager.storeFromText("","1.xml" , outputStream);
    }

    public void saveRequestFromRequest(RequestForStamp requestForStamp) throws Exception {
        existManager.storeFromRequestForStamp(requestForStamp);
    }

    public String getRequestForStampById(String id) throws Exception {
        return existManager.retrieve("","111.xml");
    }

    public String filter() throws Exception {
        return existManager.filter();
    }
}
