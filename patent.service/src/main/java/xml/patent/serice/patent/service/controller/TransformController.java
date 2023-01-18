package xml.patent.serice.patent.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value = "/pdf/{patentId}")
    public String transformPDF(@PathVariable String patentId, @RequestBody LoginDTO loginDTO){
        try {
            if (!authenticationService.authenticate(loginDTO))
                return "NOT AUTH";
            pdfTransformer.generatePDF(patentId);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }

    @PostMapping(value = "/xhtml/{patentId}")
    public String transformXHTML(@PathVariable String patentId, @RequestBody LoginDTO loginDTO){
        try {
            if (!authenticationService.authenticate(loginDTO))
                return "NOT AUTH";
            xhtmlTransformer.generateHTML(patentId);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }

}
