package xml.patent.serice.patent.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.fuseki.FusekiReader;
import xml.patent.serice.patent.service.fuseki.FusekiWriter;
import xml.patent.serice.patent.service.fuseki.MetadataExtractor;
import xml.patent.serice.patent.service.jaxb.LoaderValidation;
import xml.patent.serice.patent.service.repository.PatentRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class PatentRequestService {

    @Autowired
    private PatentRepository patentRepository;

    @Autowired
    private LoaderValidation loader;

    @Autowired
    private MetadataExtractor metadataExtractor;

    @Autowired
    private FusekiWriter fusekiWriter;

    @Autowired
    private FusekiReader fusekiReader;

    public String savePatentRequest(PatentRequest patentRequest) throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        os = this.loader.marshalling(patentRequest, os);
        patentRepository.saveRequest(os);
        //metadataExtractor.extractMetadata(os.toString());
        //fusekiWriter.saveRDF();


        //os = this.loader.marshalling(requestForStamp, os);
        //requestForStampRepository.saveRequest(os);
        OutputStream outputStream = new ByteArrayOutputStream();
        outputStream = metadataExtractor.extractMetadata(os.toString(), outputStream);
        fusekiWriter.saveRDF(outputStream);
        return "nestooo";
    }

    public ArrayList<String> searchByMetadata(String name) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("naziv", name);

        ArrayList<String> result = fusekiReader.executeQuery(params);
        return  result;
    }
}
