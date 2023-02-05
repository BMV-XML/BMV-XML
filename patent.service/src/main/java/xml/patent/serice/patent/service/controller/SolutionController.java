package xml.patent.serice.patent.service.controller;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.patent.serice.patent.service.Application;
import xml.patent.serice.patent.service.dto.AddSolutionDTO;
import xml.patent.serice.patent.service.dto.LoginDTO;
import xml.patent.serice.patent.service.dto.PatentDTO;
import xml.patent.serice.patent.service.dto.SolutionDTO;
import xml.patent.serice.patent.service.service.AuthenticationService;
import xml.patent.serice.patent.service.service.OfficialService;
import xml.patent.serice.patent.service.service.SolutionService;

import java.util.List;

@RestController
@RequestMapping(value = "solution")
public class SolutionController {

    @Autowired
    private SolutionService solutionService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private OfficialService officialService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Boolean> addSolution(@RequestBody AddSolutionDTO addSolutionDTO,
                                                       @RequestHeader(name = "username") String username,
                                                       @RequestHeader(name = "password") String password){
        try{
            LoginDTO l = new LoginDTO(username, password);
            l.setService(Application.OFFICIAL);
            if (!authenticationService.authenticate(l))
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            addSolutionDTO.setUsername(username);
            return new ResponseEntity<>(solutionService.addSolution(addSolutionDTO), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get/{patentId}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SolutionDTO> addSolution(@PathVariable String patentId,
                                                   @RequestHeader(name = "username") String username,
                                                   @RequestHeader(name = "password") String password){
        try{
            LoginDTO l = new LoginDTO(username, password);
            l.setService(Application.OFFICIAL);
            if (!authenticationService.authenticate(l))
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(solutionService.getSolution(patentId), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
