package xml.stamp.service.stamp.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xml.stamp.service.stamp.service.dto.AuthTypeDTO;
import xml.stamp.service.stamp.service.dto.LoginDTO;
import xml.stamp.service.stamp.service.dto.SuccessDTO;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class AuthenticationService {

    @Value("${main.service.url}")
    private String basicUrl;

    public boolean authenticate(LoginDTO loginDTO) throws JsonProcessingException, URISyntaxException {

        URI uri = new URI(basicUrl + "/login");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("******************************** res ********************");
        ResponseEntity<SuccessDTO> result = restTemplate.postForEntity(uri, loginDTO, SuccessDTO.class);
        System.out.println(result.getBody().isSuccessful());
        return result.getBody().isSuccessful();
    }

    public AuthTypeDTO getAuthType(LoginDTO loginDTO) throws JsonProcessingException, URISyntaxException {

        URI uri = new URI(basicUrl + "/auth");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        System.out.println("******************************** res ********************");
        ResponseEntity<AuthTypeDTO> result = restTemplate.postForEntity(uri, loginDTO, AuthTypeDTO.class);
        //System.out.println(result.getBody().isSuccessful());
        return result.getBody();
    }

}
