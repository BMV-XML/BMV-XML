package xml.patent.serice.patent.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xml.patent.serice.patent.service.dto.AddSolutionDTO;
import xml.patent.serice.patent.service.dto.LoginDTO;
import xml.patent.serice.patent.service.dto.SolutionDTO;
import xml.patent.serice.patent.service.dto.SuccessDTO;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class SolutionService {

    @Value("${main.service.url}")
    private String basicUrl;

    public boolean getIfHasSolution(String requestId) throws URISyntaxException {

        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        //HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("******************************** res ********************");
        System.out.println(requestId);
        ResponseEntity<SuccessDTO> result = restTemplate.getForEntity(uri+"/"+requestId.replace("/", "-") , SuccessDTO.class);
        System.out.println(result.getBody().isSuccessful());
        return result.getBody().isSuccessful();
    }

    public boolean addSolution(AddSolutionDTO addSolutionDTO) throws URISyntaxException {
        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("******************************** add solution ********************");
        ResponseEntity<SuccessDTO> result = restTemplate.postForEntity(uri+"/save" , addSolutionDTO, SuccessDTO.class);
        System.out.println(result.getBody().isSuccessful());
        return result.getBody().isSuccessful();
    }

    public SolutionDTO getSolution(String patentId) throws URISyntaxException {
        URI uri = new URI(basicUrl + "/solution");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("******************************** get solution ********************");
        ResponseEntity<SolutionDTO> result = restTemplate.getForEntity(uri+"/get/" + patentId.replace("/","-"), SolutionDTO.class);
        return result.getBody();
    }
}
