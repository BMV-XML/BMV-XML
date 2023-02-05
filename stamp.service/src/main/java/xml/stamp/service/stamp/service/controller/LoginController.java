package xml.stamp.service.stamp.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            AuthTypeDTO authTypeDTO = authenticationService.getAuthType(loginDTO);
            System.out.println(authTypeDTO.getType());
            if (authTypeDTO.isSuccessful() && authTypeDTO.getType().equals("ALL"))
                authTypeDTO.setType("STAMP-" + authTypeDTO.getType());
            return new ResponseEntity<>(authTypeDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new AuthTypeDTO(false, null), HttpStatus.BAD_REQUEST);
        }
    }
}
