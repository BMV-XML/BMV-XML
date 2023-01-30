package xml.patent.serice.patent.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.patent.serice.patent.service.dto.LoginDTO;
import xml.patent.serice.patent.service.service.AuthenticationService;
import xml.patent.serice.patent.service.service.PatentSearchService;
import xml.patent.serice.patent.service.transformer.PDFTransformer;
import xml.patent.serice.patent.service.transformer.XHTMLTransformer;

import javax.websocket.server.PathParam;

import java.io.IOException;

import static xml.patent.serice.patent.service.util.SparqlUtil.NTRIPLES;
import static xml.patent.serice.patent.service.util.SparqlUtil.RDF_JSON;

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
    private PatentSearchService patentSearchService;

    @GetMapping(value = "/pdf/{patentId}", produces = MediaType.APPLICATION_XML_VALUE)//trabalo bi samo službenik da ima pristup
    public ResponseEntity<String> transformPDF(@PathVariable String patentId,
                               @RequestHeader(name = "username") String username,
                               @RequestHeader(name = "password") String password){//, @RequestBody LoginDTO loginDTO
        try {
            if (!authenticationService.authenticate(new LoginDTO(username, password)))
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            pdfTransformer.generatePDF(patentId);
            return new ResponseEntity<String>("http://localhost:8082/resources/pdf/" + patentId + ".pdf", HttpStatus.OK);

            //return "http://localhost:8082/resources/pdf/" + patentId + ".pdf";
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/xhtml/{patentId}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> transformXHTML(@PathVariable String patentId,
                                         @RequestHeader(name = "username") String username,
                                         @RequestHeader(name = "password") String password){//, @RequestBody LoginDTO loginDTO
        try {
            if (!authenticationService.authenticate(new LoginDTO(username, password)))
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            xhtmlTransformer.generateHTML(patentId);
            return new ResponseEntity<String>("http://localhost:8082/resources/html/" + patentId + ".html", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "json/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getJsonMetadataById(@PathVariable String id) {
        try {
            patentSearchService.searchMetadataById(id, RDF_JSON);
            return new ResponseEntity<>("http://localhost:8082/resources/json/" + id + ".json", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("http://localhost:8082/resources/json/" + id + ".json", HttpStatus.NOT_FOUND);
        }
        //HttpHeaders headers = new HttpHeaders();
        //headers.setContentDispositionFormData("attachment", id + ".json");
    }

    @GetMapping(value = "rdf/{id}", produces = "application/xml")
    public ResponseEntity<String> getRdfMetadataById(@PathVariable String id) {
        String result = null;
        try {
            result = patentSearchService.searchMetadataById(id, NTRIPLES);
            result = "http://localhost:8082/resources/rdf/" + id + ".rdf";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
