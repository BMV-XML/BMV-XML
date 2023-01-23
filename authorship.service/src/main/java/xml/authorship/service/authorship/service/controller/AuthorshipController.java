package xml.authorship.service.authorship.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.authorship.service.authorship.service.beans.AuthorshipRequest;
import xml.authorship.service.authorship.service.service.AuthorshipRequestService;

import java.io.IOException;
import java.util.ArrayList;

import static xml.authorship.service.authorship.service.util.SparqlUtil.*;

@RestController
@RequestMapping("authorship")
public class AuthorshipController {

    @Autowired
    private AuthorshipRequestService authorshipRequestService;


    @PostMapping(value = "/xml", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> saveAuthorshipRequest(@RequestBody AuthorshipRequest authorshipRequest) {
        try {
            String response = authorshipRequestService.saveAuthorshipRequest(authorshipRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Exception", HttpStatus.OK);
        }
        return new ResponseEntity<>("radi", HttpStatus.OK);
    }

    @GetMapping(value = "fusekiSearch/{name}")
    public ResponseEntity<String> searchFromRDF(@PathVariable("name") String name) throws IOException {
        ArrayList<String> result = authorshipRequestService.searchByMetadata(name);
        StringBuilder output = new StringBuilder();
        for (String s : result) {
            output.append("\n").append(s);
        }
        return new ResponseEntity<>(output.toString(), HttpStatus.OK);
    }

    @GetMapping(value = "getJsonMetadata", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJsonMetadataById(@RequestParam(name = "id") String id) {
        String result = authorshipRequestService.searchMetadataById(id, RDF_JSON);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", id + ".json");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    @GetMapping(value = "getRdfMetadata", produces = "application/rdf+xml")
    public ResponseEntity<String> getRdfMetadataById(@RequestParam(name = "id") String id) {
        String result = authorshipRequestService.searchMetadataById(id, NTRIPLES);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", id + ".rdf");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }
}
