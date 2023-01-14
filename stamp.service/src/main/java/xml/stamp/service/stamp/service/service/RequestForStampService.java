package xml.stamp.service.stamp.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.stamp.service.stamp.service.dto.RequestForStampDTO;
import xml.stamp.service.stamp.service.fuseki.FusekiReader;
import xml.stamp.service.stamp.service.fuseki.FusekiWriter;
import xml.stamp.service.stamp.service.fuseki.MetadataExtractor;
import xml.stamp.service.stamp.service.jaxb.JaxLoader;
import xml.stamp.service.stamp.service.model.RequestForStamp;
import xml.stamp.service.stamp.service.repository.RequestForStampRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class RequestForStampService {
    @Autowired
    private RequestForStampRepository requestForStampRepository;
    @Autowired
    private MetadataExtractor metadataExtractor;
    @Autowired
    private FusekiWriter fusekiWriter;
    @Autowired
    private FusekiReader fusekiReader;

    @Autowired
    private JaxLoader loader;

    public String saveRequestForStamp(RequestForStamp requestForStamp) throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        os = this.loader.marshalling(requestForStamp, os);
        requestForStampRepository.saveRequest(os);
        OutputStream outputStream = new ByteArrayOutputStream();
        outputStream = metadataExtractor.extractMetadata(os.toString(), outputStream);
        fusekiWriter.saveRDF(outputStream);
        return "nestooo";
    }

    public String getRequestForStampById(String id) throws Exception {
        return requestForStampRepository.getRequestForStampById(id);
    }

    public String filter() throws Exception {
        return  requestForStampRepository.filter();
    }

    public void saveFileFromString(String text) throws Exception {
        RequestForStamp requestForStamp = loader.unmarshalling(text);
        OutputStream outputStream = loader.marshalling(requestForStamp, new ByteArrayOutputStream());
        requestForStampRepository.saveRequest(outputStream);
        OutputStream out = new ByteArrayOutputStream();
        out = metadataExtractor.extractMetadata(text, out);
        fusekiWriter.saveRDF(out);
    }

    public ArrayList<String> searchByMetadata(String name) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("naziv", name);

        ArrayList<String> result = fusekiReader.executeQuery(params);
        return  result;
    }
}
