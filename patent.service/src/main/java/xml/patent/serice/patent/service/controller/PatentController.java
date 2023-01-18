package xml.patent.serice.patent.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.dto.LoginDTO;
import xml.patent.serice.patent.service.service.AuthenticationService;
import xml.patent.serice.patent.service.service.PatentRequestService;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "patent")
public class PatentController {

    @Autowired
    private PatentRequestService patentRequestService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/xml/{username}/{password}", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> saveRequestForStamp(@RequestBody PatentRequest patentRequest,
                                                      @PathVariable String username,
                                                      @PathVariable String password){
        try {
            if (!authenticationService.authenticate(new LoginDTO(username, password)))
                return new ResponseEntity<>("No AUTH", HttpStatus.UNAUTHORIZED);
            String response = patentRequestService.savePatentRequest(patentRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Exception", HttpStatus.OK);
        }
        return new ResponseEntity<String>("radi", HttpStatus.OK);
    }














    //ne radi :)
    @GetMapping(value = "fusekiSearch/{name}")
    public ResponseEntity<String> searchFromRDF(@PathVariable("name") String name) {
        ArrayList<String> result = null;
        try {
            result = patentRequestService.searchByMetadata(name);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Exception", HttpStatus.OK);
        }
        String output="";
        for(String s: result){
            output +="\n" + s;
        }
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
