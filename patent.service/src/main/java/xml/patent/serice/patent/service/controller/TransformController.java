package xml.patent.serice.patent.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.patent.serice.patent.service.dto.LoginDTO;
import xml.patent.serice.patent.service.service.AuthenticationService;
import xml.patent.serice.patent.service.transformer.PDFTransformer;
import xml.patent.serice.patent.service.transformer.XHTMLTransformer;

@RestController
@RequestMapping(value = "transform")
public class TransformController {

    @Autowired
    private PDFTransformer pdfTransformer;

    @Autowired
    private XHTMLTransformer xhtmlTransformer;

    @Autowired
    private AuthenticationService authenticationService;

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

}
