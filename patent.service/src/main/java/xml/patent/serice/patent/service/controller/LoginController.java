package xml.patent.serice.patent.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xml.patent.serice.patent.service.dto.AuthTypeDTO;
import xml.patent.serice.patent.service.dto.LoginDTO;
import xml.patent.serice.patent.service.dto.RegisterDTO;
import xml.patent.serice.patent.service.dto.SuccessDTO;
import xml.patent.serice.patent.service.service.AuthenticationService;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<AuthTypeDTO> login(@RequestBody LoginDTO loginDTO){
        System.out.println("LOGIN");
        try {
            AuthTypeDTO authTypeDTO = authenticationService.getAuthType(loginDTO);
            if (authTypeDTO.isSuccessful() && authTypeDTO.getType().equals("ALL"))
                authTypeDTO.setType("PATENT-" + authTypeDTO.getType());
            return new ResponseEntity<>(authTypeDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new AuthTypeDTO(false, null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SuccessDTO> register(@RequestBody RegisterDTO registerDTO){
        System.out.println("REGISTER");
        try {
            return new ResponseEntity<>(authenticationService.register(registerDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new SuccessDTO(false), HttpStatus.BAD_REQUEST);
        }
    }

}
