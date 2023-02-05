package xml.authorship.service.authorship.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xml.authorship.service.authorship.service.dto.AuthTypeDTO;
import xml.authorship.service.authorship.service.dto.LoginDTO;
import xml.authorship.service.authorship.service.service.AuthenticationService;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<AuthTypeDTO> login(@RequestBody LoginDTO loginDTO){
        System.out.println("LOGIN");
        try {
            AuthTypeDTO authTypeDTO = authenticationService.getAuthType(loginDTO);
            if (authTypeDTO.isSuccessful() && authTypeDTO.getType().equals("ALL"))
                authTypeDTO.setType("AUTHORSHIP-" + authTypeDTO.getType());
            return new ResponseEntity<>(authTypeDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new AuthTypeDTO(false, null), HttpStatus.BAD_REQUEST);
        }
    }


}
