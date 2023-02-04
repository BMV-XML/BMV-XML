package xml.authorship.service.authorship.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xml.authorship.service.authorship.service.beans.AuthorshipRequest;
import xml.authorship.service.authorship.service.dto.*;
import xml.authorship.service.authorship.service.service.AuthenticationService;
import xml.authorship.service.authorship.service.service.AuthorshipRequestService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static xml.authorship.service.authorship.service.util.SparqlUtil.*;

@RestController
@RequestMapping("authorship")
@CrossOrigin
public class AuthorshipController {

    @Autowired
    private AuthorshipRequestService authorshipRequestService;

    @Autowired
    private AuthenticationService authenticationService;


    /**
     * @deprecated Do not use this method. Use the one with DTO parameter.
     * */
    @PostMapping(value = "/xml", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> saveAuthorshipRequest(@RequestBody AuthorshipRequest authorshipRequest) {
        try {
            System.out.println(authorshipRequest.toString());
            String response = authorshipRequestService.saveAuthorshipRequest(authorshipRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Exception", HttpStatus.OK);
        }
        return new ResponseEntity<>("radi", HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StatusResponseDTO> saveAuthorshipRequest(@RequestBody AuthorshipRequestDTO authorshipRequest) {
        System.out.println(authorshipRequest);
        try {
            this.authorshipRequestService.saveAuthorshipRequest(authorshipRequest);
            return new ResponseEntity<>(new StatusResponseDTO(true, "Passed", 0), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new StatusResponseDTO(false, "Unexpected exception", 0), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/example/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<AttachmentsDTO> setExampleFile(@RequestPart("files") MultipartFile[] multiPartFiles) throws IOException {
        AttachmentsDTO dto = authorshipRequestService.setExampleFile(multiPartFiles);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/description/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {"application/xml"})
    public ResponseEntity<AttachmentsDTO> setDescriptionFile(@RequestPart("files") MultipartFile[] multiPartFiles) throws IOException {
        AttachmentsDTO dto = authorshipRequestService.setDescriptionFile(multiPartFiles);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

//    @GetMapping(value = "fusekiSearch/{name}")  // TODO: nezz da li radi
//    public ResponseEntity<String> searchFromRDF(@PathVariable("name") String name) throws IOException {
//        ArrayList<String> result = authorshipRequestService.searchByMetadata(name);
//        StringBuilder output = new StringBuilder();
//        for (String s : result) {
//            output.append("\n").append(s);
//        }
//        return new ResponseEntity<>(output.toString(), HttpStatus.OK);
//    }

    @GetMapping(value = "getJsonMetadata", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJsonMetadataById(@RequestParam(name = "id") String id) {
        String result = authorshipRequestService.searchMetadataById(id, RDF_JSON);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "file.json");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    @GetMapping(value = "getRdfMetadata", produces = "application/rdf+xml")
    public ResponseEntity<String> getRdfMetadataById(@RequestParam(name = "id") String id) {
        String result = authorshipRequestService.searchMetadataById(id, NTRIPLES);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "file.rdf");
        return new ResponseEntity<>(result,headers, HttpStatus.OK);
    }

    @GetMapping(value = "get/all", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<SimpleAuthorshipDTO>> getAllAuthorshipRequests() {
        try {
            return new ResponseEntity<>(authorshipRequestService.getAllAuthorshipRequests(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping(value = "get/{id}", produces = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<SimpleAuthorshipDTO> getAuthorshipRequest(@PathVariable String id) {
        try {
            return new ResponseEntity<>(authorshipRequestService.getAuthorshipRequest(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping(value = "search", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<SimpleAuthorshipDTO>> getListOfAuthorshipSearched(@RequestBody List<SearchDTO> expressions) {
        try {
            List<String> searchBy = new ArrayList<>();
            for (SearchDTO e : expressions) {
                System.out.println(e.getExpression());
                searchBy.add(e.getExpression());
            }
            return new ResponseEntity<>(authorshipRequestService.getListOfAuthorshipSearched(searchBy), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "filter", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<SimpleAuthorshipDTO>> getListOfPatentsFiltered(@RequestBody List<FilterDTO> filter) {
        try {
            for (FilterDTO f : filter) {
                System.out.println(f.getOperator());
                System.out.println(f.getType());
                System.out.println(f.getValue());
            }
            return new ResponseEntity<>(authorshipRequestService.getListOfAuthorshipFiltered(filter), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // TODO: NE RADI
    @PostMapping(value = "addSolution", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addSolution(@RequestBody NewSolutionDTO dto,
                                               @RequestHeader(name = "username") String username,
                                               @RequestHeader(name = "password") String password) {
        try {
//            if (!authenticationService.authenticate(new LoginDTO(username, password)))
//                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            dto.setUsername(username);
            return new ResponseEntity<>(authorshipRequestService.addSolution(dto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping(value="report", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity<String> getReport(@RequestBody RangeDTO rangeDTO) throws Exception {
//        return new ResponseEntity<>(reportService.generateReportPDF(rangeDTO), HttpStatus.OK);
//    }


}
