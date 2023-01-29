package xml.stamp.service.stamp.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import xml.stamp.service.stamp.service.dto.AuthTypeDTO;
import xml.stamp.service.stamp.service.dto.LoginDTO;
import xml.stamp.service.stamp.service.service.AuthenticationService;

@RestController
public class LoginController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<AuthTypeDTO> login(@RequestBody LoginDTO loginDTO){
        System.out.println("LOGIN");
        try {
            return new ResponseEntity<>(authenticationService.getAuthType(loginDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new AuthTypeDTO(false, null), HttpStatus.BAD_REQUEST);
        }
    }
}
