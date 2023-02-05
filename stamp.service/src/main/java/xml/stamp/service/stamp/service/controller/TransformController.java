package xml.stamp.service.stamp.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.stamp.service.stamp.service.dto.LoginDTO;
import xml.stamp.service.stamp.service.service.AuthenticationService;
import xml.stamp.service.stamp.service.service.StampSearchService;
import xml.stamp.service.stamp.service.transform.PDFTransformer;
import xml.stamp.service.stamp.service.transform.XHTMLTransformer;

import java.io.IOException;

import static xml.stamp.service.stamp.service.util.SparqlUtil.NTRIPLES;
import static xml.stamp.service.stamp.service.util.SparqlUtil.RDF_JSON;

@RestController
@RequestMapping(value = "transform")
public class TransformController {
    @Autowired
    private PDFTransformer pdfTransformer;

    @Autowired
    private XHTMLTransformer xhtmlTransformer;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private StampSearchService stampSearchService;


    @GetMapping(value = "/pdf/{stampId}")
    public ResponseEntity<String> transformPDF(@PathVariable String stampId,
                               @RequestHeader(name = "username") String username,
                               @RequestHeader(name = "password") String password){
        try {
            if (!authenticationService.authenticate(new LoginDTO(username, password)))
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

            pdfTransformer.generatePDF(stampId);
            return new ResponseEntity<String>("http://localhost:8080/resources/pdf/" + stampId + ".pdf", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/xhtml/{stampId}")
    public ResponseEntity<String> transformXHTML(@PathVariable String stampId,
                                 @RequestHeader(name = "username") String username,
                                 @RequestHeader(name = "password") String password){//, @RequestBody LoginDTO loginDTO
         try {
             if (!authenticationService.authenticate(new LoginDTO(username, password)))
                 return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
             xhtmlTransformer.generateHTML(stampId);
             return new ResponseEntity<String>("http://localhost:8080/resources/html/" + stampId + ".html", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
             return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/rdf/{id}")
    public ResponseEntity<String> getRdfMetadataById(@PathVariable String id) {
        System.out.println("RDF reprezentation  ID: " + id);
        String result = null;
        try {
            result = stampSearchService.searchMetadataById(id, NTRIPLES);
            result = "http://localhost:8080/resources/rdf/" + id + ".rdf";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "json/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getJsonMetadataById(@PathVariable String id) {
        try {
            stampSearchService.searchMetadataById(id, RDF_JSON);
            return new ResponseEntity<>("http://localhost:8080/resources/json/" + id + ".json", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("http://localhost:8080/resources/json/" + id + ".json", HttpStatus.NOT_FOUND);
        }
    }
}
