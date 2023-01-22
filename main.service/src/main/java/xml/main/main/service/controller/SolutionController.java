package xml.main.main.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.main.main.service.dto.AddSolutionDTO;
import xml.main.main.service.dto.SolutionDTO;
import xml.main.main.service.dto.SuccessDTO;
import xml.main.main.service.service.SolutionService;

@RestController
@RequestMapping(value = "solution")
public class SolutionController {

    @Autowired
    private SolutionService solutionService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SuccessDTO> addSolution(@RequestBody AddSolutionDTO addSolutionDTO){
        System.out.println("************************** save add solution *****************");
        try {
            solutionService.addSolution(addSolutionDTO);
            return new ResponseEntity<>(new SuccessDTO(true), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new SuccessDTO(false), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{requestId}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SuccessDTO> checkIfRequestHasSolution(@PathVariable String requestId){
        try {
            return new ResponseEntity<>(new SuccessDTO(solutionService.hasSolution(requestId)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new SuccessDTO(false), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get/{requestId}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SolutionDTO> getSolution(@PathVariable String requestId){
        try {
            return new ResponseEntity<>(solutionService.getSolution(requestId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
