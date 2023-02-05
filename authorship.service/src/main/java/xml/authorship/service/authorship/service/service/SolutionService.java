package xml.authorship.service.authorship.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xml.authorship.service.authorship.service.beans.AuthorshipRequest;
import xml.authorship.service.authorship.service.db.ExistManager;
import xml.authorship.service.authorship.service.dto.NewSolutionDTO;
import xml.authorship.service.authorship.service.dto.SolutionDTO;
import xml.authorship.service.authorship.service.dto.SuccessDTO;

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
        ResponseEntity<SuccessDTO> result = restTemplate.exchange(uri + "/" + requestId, HttpMethod.GET, entity, SuccessDTO.class);
        System.out.println(result.getBody().isSuccessful());

        return result.getBody().isSuccessful();
    }

    public boolean addSolution(NewSolutionDTO dto) throws Exception {
        AuthorshipRequest request = existManager.retrieve(dto.getRequestId());
        dto.setRequestDate(request.getAuthorshipData().getApplicationDate().getDate());
        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("******************************** add solution ********************");
        ResponseEntity<SuccessDTO> result = restTemplate.postForEntity(uri + "/save", new HttpEntity<>(dto, headers), SuccessDTO.class);
        System.out.println(result.getBody().isSuccessful());
        return result.getBody().isSuccessful();
    }

    public SolutionDTO getSolution(String requestId) throws URISyntaxException {
        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity entity = new HttpEntity(headers);
        System.out.println("******************************** get solution ********************");
        ResponseEntity<SolutionDTO> result = restTemplate.exchange(uri + "/get/" + requestId, HttpMethod.GET, entity, SolutionDTO.class);
        return result.getBody();
    }

}
