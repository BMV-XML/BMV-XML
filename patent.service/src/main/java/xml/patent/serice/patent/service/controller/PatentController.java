package xml.patent.serice.patent.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.patent.serice.patent.service.Application;
import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.dto.*;
import xml.patent.serice.patent.service.dto.request.PatentRequestDTO;
import xml.patent.serice.patent.service.exception.NotValidException;
import xml.patent.serice.patent.service.jaxb.LoaderValidation;
import xml.patent.serice.patent.service.service.AuthenticationService;
import xml.patent.serice.patent.service.service.OfficialService;
import xml.patent.serice.patent.service.service.PatentRequestService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "patent")
public class PatentController {

    @Autowired
    private PatentRequestService patentRequestService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LoaderValidation loaderValidation;

    @Autowired
    private OfficialService officialService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StatusDTO> savePatentRequest(@RequestBody PatentRequestDTO patentRequest,
                                                       @RequestHeader(name = "username") String username,
                                                       @RequestHeader(name = "password") String password) {
        System.out.println(patentRequest);
        try {
            LoginDTO l = new LoginDTO(username, password);
            l.setService(Application.PATENT);
            if (!authenticationService.authenticate(l))
               return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
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

    @GetMapping(value = "list/user", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<PatentDTO>> getListOfPatents(
            @RequestHeader(name = "username") String username,
            @RequestHeader(name = "password") String password){
        try {
            LoginDTO l = new LoginDTO(username, password);
            l.setService(Application.PATENT);
            if (!authenticationService.authenticate(l))
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            System.out.println("************************************** get list  -  user *****************");
            return new ResponseEntity<List<PatentDTO>>(officialService.getListOfPatentUser(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping(value = "search/user", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<PatentDTO>> getListOfPatentsSearched(@RequestBody List<SearchExpression> expressions,
                                                                    @RequestHeader(name = "username") String username,
                                                                    @RequestHeader(name = "password") String password){
        try {
            LoginDTO l = new LoginDTO(username, password);
            l.setService(Application.PATENT);
            if (!authenticationService.authenticate(l))
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            System.out.println("************************************** get list - SEARCH *****************");
            List<String> searchBy = new ArrayList<>();
            for (SearchExpression e: expressions){
                System.out.println(e.getName());
                searchBy.add(e.getName());
            }
            return new ResponseEntity<List<PatentDTO>>(officialService.getListOfPatentSearchedUser(searchBy), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping(value = "filter/user", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<PatentDTO>> getListOfPatentsFiltered(@RequestBody List<FilterDTO> filter,
                                                                    @RequestHeader(name = "username") String username,
                                                                    @RequestHeader(name = "password") String password){
        try {
            LoginDTO l = new LoginDTO(username, password);
            l.setService(Application.PATENT);
            if (!authenticationService.authenticate(l))
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<List<PatentDTO>>(officialService.getListOfPatentFilteredUser(filter), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
