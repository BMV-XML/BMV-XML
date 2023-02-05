package xml.authorship.service.authorship.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xml.authorship.service.authorship.service.dto.AuthTypeDTO;
import xml.authorship.service.authorship.service.dto.LoginDTO;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class AuthenticationService {

    @Value("${main.service.url}")
    private String basicUrl;


    public boolean authenticate(LoginDTO loginDTO) throws URISyntaxException {

        URI uri = new URI(basicUrl + "/login");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity entity = new HttpEntity(loginDTO, headers);
        ResponseEntity<Boolean> result = restTemplate.exchange(uri, HttpMethod.POST, entity, Boolean.class);
        return Boolean.TRUE.equals(result.getBody());
    }

    public AuthTypeDTO getAuthType(LoginDTO loginDTO) throws JsonProcessingException, URISyntaxException {

        URI uri = new URI(basicUrl + "/auth");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("******************************** res ********************");
        HttpEntity entity = new HttpEntity(loginDTO, headers);
        ResponseEntity<AuthTypeDTO> result = restTemplate.exchange(uri, HttpMethod.POST, entity, AuthTypeDTO.class);
        //ResponseEntity<AuthTypeDTO> result = restTemplate.postForEntity(uri, loginDTO, AuthTypeDTO.class);
        //System.out.println(result.getBody().isSuccessful());
        return result.getBody();
    }
}
