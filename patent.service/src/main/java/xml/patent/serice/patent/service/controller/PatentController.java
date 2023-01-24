package xml.patent.serice.patent.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.dto.LoginDTO;
import xml.patent.serice.patent.service.dto.StatusDTO;
import xml.patent.serice.patent.service.dto.request.PatentRequestDTO;
import xml.patent.serice.patent.service.exception.NotValidException;
import xml.patent.serice.patent.service.jaxb.LoaderValidation;
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

    @Autowired
    private LoaderValidation loaderValidation;

    @PostMapping(value = "/xml", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> savePatentRequest(@RequestBody String patentRequest){
        try {
            PatentRequest patentRequestOnj = loaderValidation.unmarshalling(patentRequest);
            //if (!authenticationService.authenticate(new LoginDTO(username, password)))
             //   return new ResponseEntity<>("No AUTH", HttpStatus.UNAUTHORIZED);
            System.out.println("----------------------------------------");
            System.out.println(patentRequest);
            String response = patentRequestService.savePatentRequest(patentRequestOnj);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Exception", HttpStatus.OK);
        }
        return new ResponseEntity<String>("radi", HttpStatus.OK);
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StatusDTO> savePatentRequest(@RequestBody PatentRequestDTO patentRequest) {
        System.out.println(patentRequest);
        try {
            this.patentRequestService.savePatentRequest(patentRequest);
            return new ResponseEntity<>(new StatusDTO(true, "", 0), HttpStatus.OK);
        } catch (NotValidException notValidException){
            notValidException.printStackTrace();
            return new ResponseEntity<>(new StatusDTO(false, notValidException.getMessage(), notValidException.getCode()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new StatusDTO(false, "Unexpected exception", 0), HttpStatus.OK);
        }
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
