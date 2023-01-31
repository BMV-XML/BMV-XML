package xml.authorship.service.authorship.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xml.authorship.service.authorship.service.transformer.PDFTransformer;
import xml.authorship.service.authorship.service.transformer.XHTMLTransformer;

import java.io.File;

@RestController
@RequestMapping("transform")
public class TransformController {

    @Autowired
    private PDFTransformer pdfTransformer;

    @Autowired
    private XHTMLTransformer xhtmlTransformer;


    @GetMapping(value = "/pdf/{authorshipId}")
    public ResponseEntity<Resource> transformPDF(@PathVariable String authorshipId){
        try {
            File file = pdfTransformer.generatePDF(authorshipId);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+authorshipId+".pdf");
            return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/xhtml/{authorshipId}")
    public ResponseEntity<Resource> transformXHTML(@PathVariable String authorshipId){
        try {
            File file = xhtmlTransformer.generateHTML(authorshipId);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+authorshipId+".html");
            return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
