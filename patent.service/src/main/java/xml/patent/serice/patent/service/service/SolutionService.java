package xml.patent.serice.patent.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.db.ExistManager;
import xml.patent.serice.patent.service.dto.AddSolutionDTO;
import xml.patent.serice.patent.service.dto.LoginDTO;
import xml.patent.serice.patent.service.dto.SolutionDTO;
import xml.patent.serice.patent.service.dto.SuccessDTO;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class SolutionService {

    @Autowired
    private ExistManager existManager;

    @Value("${main.service.url}")
    private String basicUrl;

    public boolean getIfHasSolution(String requestId) throws URISyntaxException {

        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("******************************** res ********************");
        System.out.println(requestId);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<SuccessDTO> result = restTemplate.exchange(uri+"/"+requestId.replace("/", "-"), HttpMethod.GET, entity, SuccessDTO.class);
        System.out.println(result.getBody().isSuccessful());

        return result.getBody().isSuccessful();
    }

    public boolean addSolution(AddSolutionDTO addSolutionDTO) throws Exception {
        PatentRequest pr = existManager.retrieve(convertToSafe(addSolutionDTO.getRequestId()));
        addSolutionDTO.setRequestDate(pr.getPatentData().getApplicationDate().getDate());
        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("******************************** add solution ********************");
        ResponseEntity<SuccessDTO> result = restTemplate.postForEntity(uri+"/save" , new HttpEntity<AddSolutionDTO>(addSolutionDTO, headers), SuccessDTO.class);
        System.out.println(result.getBody().isSuccessful());
        return result.getBody().isSuccessful();
    }

    public SolutionDTO getSolution(String patentId) throws URISyntaxException {
        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity entity = new HttpEntity(headers);
        System.out.println("******************************** get solution ********************");
        ResponseEntity<SolutionDTO> result = restTemplate.exchange(uri+"/get/" + patentId.replace("/","-"), HttpMethod.GET, entity, SolutionDTO.class);
        return result.getBody();
    }

    private String convertToSafe(String applicationNumber) {
        return applicationNumber.replace("/", "-");
    }
}
