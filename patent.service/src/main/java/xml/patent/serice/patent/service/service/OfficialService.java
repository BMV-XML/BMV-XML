package xml.patent.serice.patent.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.patent.serice.patent.service.beans.PatentData;
import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.db.ExistManager;
import xml.patent.serice.patent.service.dto.PatentDTO;
import xml.patent.serice.patent.service.fuseki.FusekiReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfficialService {

    @Autowired
    private ExistManager existManager;

    @Autowired
    private FusekiReader fusekiReader;

    @Autowired
    private SolutionService solutionService;

    public List<PatentDTO> getListOfPatent() throws Exception {
        List<PatentRequest> requests = existManager.retrieveCollection();
        List<PatentDTO> results = new ArrayList<>();
        for (PatentRequest pr : requests){
            PatentDTO p = new PatentDTO();
            p.setHasSolution(solutionService.getIfHasSolution(pr.getPatentId()));
            System.out.println("--------- PATENT -----------");
            System.out.println(p.isHasSolution());
            p.setId(pr.getPatentId());
            p.setApplicationDate(pr.getPatentData().getApplicationDate().getDate());
            p.setSubmitter(pr.getSubmitter().getGlobalEntity().getContact().getEmail());
            results.add(p);
        }
        return results;
    }

    public void getListOfPatentRdf() throws IOException {
        fusekiReader.run();
    }
}
