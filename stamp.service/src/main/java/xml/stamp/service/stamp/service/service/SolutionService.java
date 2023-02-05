package xml.stamp.service.stamp.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xml.stamp.service.stamp.service.db.ExistManager;
import xml.stamp.service.stamp.service.dto.SuccessDTO;
import xml.stamp.service.stamp.service.dto.solution.AddSolutionDTO;
import xml.stamp.service.stamp.service.dto.solution.SolutionDTO;
import xml.stamp.service.stamp.service.model.RequestForStamp;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class SolutionService {
    @Autowired
    private ExistManager existManager;

    @Value("${main.service.url}")
    private String basicUrl;

    public boolean getIfHasSolution(String requestId) throws URISyntaxException {

        System.out.println("Stigao sam do funkcije");

        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("******************************** res ********************");
        System.out.println(requestId);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<SuccessDTO> result = restTemplate.exchange(uri + "/" + requestId.replace("/", "-"), HttpMethod.GET, entity, SuccessDTO.class);
        System.out.println(result.getBody().isSuccessful());

        return result.getBody().isSuccessful();
    }

    public boolean addSolution(AddSolutionDTO addSolutionDTO) throws Exception {
        RequestForStamp requestForStamp = existManager.retrieve(convertToSafe(addSolutionDTO.getRequestId()));
        addSolutionDTO.setRequestDate(requestForStamp.getStampData().getDateOfApplication().getDate());
        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("******************************** add solution ********************");
        ResponseEntity<SuccessDTO> result = restTemplate.postForEntity(uri + "/save", new HttpEntity<AddSolutionDTO>(addSolutionDTO, headers), SuccessDTO.class);
        System.out.println(result.getBody().isSuccessful());
        return result.getBody().isSuccessful();
    }

    private String convertToSafe(String applicationNumber) {
        return applicationNumber.replace("/", "-");
    }

    public SolutionDTO getSolution(String id) throws URISyntaxException {
        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        //System.out.println(requestId);
        HttpEntity entity = new HttpEntity(headers);
        //ResponseEntity<SuccessDTO> result = restTemplate.exchange(uri+"/"+requestId.replace("/", "-"), HttpMethod.GET, entity, SuccessDTO.class);
        System.out.println("******************************** get solution ********************");
        ResponseEntity<SolutionDTO> result = restTemplate.exchange(uri+"/get/" + id.replace("/","-"), HttpMethod.GET, entity, SolutionDTO.class);
        return result.getBody();
    }
}
