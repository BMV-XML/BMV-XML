package xml.stamp.service.stamp.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.stamp.service.stamp.service.dto.LoginDTO;
import xml.stamp.service.stamp.service.dto.solution.AddSolutionDTO;
import xml.stamp.service.stamp.service.dto.solution.SolutionDTO;
import xml.stamp.service.stamp.service.service.AuthenticationService;
import xml.stamp.service.stamp.service.service.OfficialService;
import xml.stamp.service.stamp.service.service.SolutionService;

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
            if (!authenticationService.authenticate(new LoginDTO(username, password)))
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            addSolutionDTO.setUsername(username);
            return new ResponseEntity<>(solutionService.addSolution(addSolutionDTO), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SolutionDTO> addSolution(@PathVariable String id,
                                                   @RequestHeader(name = "username") String username,
                                                   @RequestHeader(name = "password") String password){
        try{
            if (!authenticationService.authenticate(new LoginDTO(username, password))) //auth na all ne na patent!!!!
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(solutionService.getSolution(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
