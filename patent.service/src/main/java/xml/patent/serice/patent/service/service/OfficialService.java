package xml.patent.serice.patent.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.patent.serice.patent.service.beans.*;
import xml.patent.serice.patent.service.db.ExistManager;
import xml.patent.serice.patent.service.dto.FilterDTO;
import xml.patent.serice.patent.service.dto.FilterElements;
import xml.patent.serice.patent.service.dto.FullPatentDTO;
import xml.patent.serice.patent.service.dto.PatentDTO;
import xml.patent.serice.patent.service.dto.request.PreviousPatentDTO;
import xml.patent.serice.patent.service.dto.request.TitleDTO;
import xml.patent.serice.patent.service.fuseki.FusekiReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

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
            results.add(convertPatentRequestToPatentDTO(pr));
        }
        return results;
    }

    private PatentDTO convertPatentRequestToPatentDTO(PatentRequest pr) throws URISyntaxException {
        PatentDTO p = new PatentDTO();
        p.setHasSolution(solutionService.getIfHasSolution(pr.getPatentId()));
        System.out.println("--------- PATENT -----------");
        System.out.println(p.isHasSolution());
        p.setId(pr.getPatentId());
        p.setApplicationDate(pr.getPatentData().getApplicationDate().getDate());
        p.setSubmitter(pr.getSubmitter().getGlobalEntity().getContact().getEmail());
        return p;
    }

    public FullPatentDTO getPatent(String patentId) throws Exception {
        System.out.println(patentId);
        PatentRequest request = existManager.retrieve(patentId.replace("/", "-"));
        FullPatentDTO patent = new FullPatentDTO();
        patent.setPatentId(request.getPatentId());
        patent.setApplicationDate(request.getPatentData().getApplicationDate().getDate());
        patent.setAcknowladgedDateOfSubmission(request.getPatentData().getAcknowladgeDateOfSubmission());

        patent.setRecipientName(request.getRecepient().getName());
        patent.setRecipientCity(request.getRecepient().getAddress().getCity());
        patent.setRecipientCountry(request.getRecepient().getAddress().getCountry());
        patent.setRecipientNumber(request.getRecepient().getAddress().getNumber());
        patent.setRecipientPostNumber(request.getRecepient().getAddress().getPostNumber());
        patent.setRecipientStreet(request.getRecepient().getAddress().getStreet());

        for (Title t : request.getTitleList()){
            TitleDTO dto = new TitleDTO();
            dto.setLanguage(t.getLanguage());
            dto.setTitle(t.getTitle());
            patent.addTitle(dto);
        }

        patent.setDeliveryCity(request.getDeliveryData().getAddress().getCity());
        patent.setDeliveryCountry(request.getDeliveryData().getAddress().getCountry());
        patent.setDeliveryNumber(request.getDeliveryData().getAddress().getNumber());
        patent.setDeliveryPostNumber(request.getDeliveryData().getAddress().getPostNumber());
        patent.setDeliveryStreet(request.getDeliveryData().getAddress().getStreet());
        patent.setEmailNotification(request.getDeliveryData().getEmailNotification() == Checkbox.DA);
        patent.setLetterNotification(request.getDeliveryData().getLetterNotification() == Checkbox.DA);

        patent.setAdditionalPatent(request.getAdditionalPatent().getAdditionalPatent() == Checkbox.DA);
        patent.setSeparatedPatent(request.getAdditionalPatent().getSeperatePatent() == Checkbox.DA);
        if (patent.isAdditionalPatent() | patent.isSeparatedPatent()){
            patent.setPreviousPatentId(request.getAdditionalPatent().getPatent().getPatentId());
            patent.setSubmissionDate(request.getAdditionalPatent().getPatent().getSubmissionDate());
            patent.setCountry(request.getAdditionalPatent().getPatent().getCountry());
        }

        if (request.getPriorityRights() != null) {
            for (Patent p : request.getPriorityRights()) {
                PreviousPatentDTO dto = new PreviousPatentDTO();
                dto.setApplicationNumber(p.getPatentId());//TODO: datovi!!!!
                dto.setSubmissionDate(p.getSubmissionDate().toString());
                dto.setCountry(p.getCountry());
                patent.addPriorityPatent(dto);
            }
        }

        patent.setHasSolution(solutionService.getIfHasSolution(patentId));
        if (patent.isHasSolution()){
            patent.setSolution(solutionService.getSolution(patentId));
        }

        patent.setSubmitterData(request.getSubmitter());
        patent.setCommissionerData(request.getCommissioner());
        patent.setInventorData(request.getInventor());

        return patent;
    }

    public List<PatentDTO> getListOfPatentSearched(List<String> searchBy) throws Exception {
        List<PatentRequest> requests = existManager.retrieveCollection();
        List<PatentDTO> result = new ArrayList<>();
        for (PatentRequest request: requests){
            if (request.contains(searchBy)){
                result.add(convertPatentRequestToPatentDTO(request));
            }
        }
        return result;
    }

    public List<PatentDTO> getListOfPatentFiltered(List<FilterDTO> filter) throws Exception {
        HashSet<String> patents = fusekiReader.search(filter);
        ///Stream<String> result = patents.stream().distinct();
        List<PatentDTO> resultDto = new ArrayList<>();
        for (String elem : patents){
            resultDto.add(convertPatentRequestToPatentDTO(existManager.retrieve(elem)));
        }
        return resultDto;
    }

    public void getListOfPatentRdf() throws IOException {
        //fusekiReader.executeQuery(null);
        //fusekiReader.searchByTitle();
        //fusekiReader.searchByApplicationNumber("p-2");
        //fusekiReader.searchByDate("2023-01-24");
        List<FilterElements> elements = new ArrayList<>();
        elements.add(new FilterElements("naslov", "ili", "aaa"));
        elements.add(new FilterElements("pod_ime", "ili", "Mark"));
        //elements.add(new FilterElements("broj_prijave", "i", "P-23"));
        elements.add(new FilterElements("sibling", "i", "P-1234-23"));
        /*
        System.out.println("PODNOSILAC");
        fusekiReader.searchByUser("pod_ime", "Marko Markovic");
        System.out.println("PUNOMOCNIK");
        fusekiReader.searchByUser("pun_ime", "Advokat");
        System.out.println("PRONALAZAC");
        fusekiReader.searchByUser("pro_ime", "Advokat");
        */
        //String graphUri = "/patent/metadata";
        //fusekiReader.search(graphUri);
        //fusekiReader.run();
    }
}
