package xml.main.main.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.main.main.service.beans.ServiceType;
import xml.main.main.service.dto.AuthTypeDTO;
import xml.main.main.service.dto.LoginDTO;
import xml.main.main.service.dto.RegisterDTO;
import xml.main.main.service.dto.SuccessDTO;
import xml.main.main.service.service.UserService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping()
public class LoginController {


    @Autowired
    private UserService userService;

    @PostMapping(value = "auth", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<AuthTypeDTO> login(@RequestBody LoginDTO loginDTO) throws Exception {
        System.out.println("************************************ AUTH *************************************");
        System.out.println(loginDTO.getUsername());
        System.out.println(loginDTO.getPassword());
        System.out.println(loginDTO.getService());
        boolean auth = userService.hasAuthority(loginDTO.getUsername(), loginDTO.getPassword(), ServiceType.valueOf(loginDTO.getService()));
        String type = userService.getAuthority(loginDTO.getUsername(), loginDTO.getPassword());
        return ResponseEntity.ok(new AuthTypeDTO(auth, type));
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SuccessDTO> hasAuthority(@RequestBody LoginDTO loginDTO) throws Exception {
        System.out.println("************************************ Login *************************************");
        return ResponseEntity.ok(new SuccessDTO(userService.hasAuthority(loginDTO.getUsername(), loginDTO.getPassword(), ServiceType.valueOf(loginDTO.getService()))));
    }

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SuccessDTO> register(@RequestBody RegisterDTO registerDTO) throws Exception {
        System.out.println("************************************ REGISTER *************************************");
        System.out.println(registerDTO.getService());
        try{
            userService.register(registerDTO.getUsername(), registerDTO.getPassword(), ServiceType.valueOf(registerDTO.getService()), registerDTO.getName(), registerDTO.getSurname());
            return new ResponseEntity(new SuccessDTO(true), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(new SuccessDTO(false), HttpStatus.BAD_REQUEST);

        }
    }
}
