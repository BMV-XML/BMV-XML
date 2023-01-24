package xml.authorship.service.authorship.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xml.authorship.service.authorship.service.transformer.PDFTransformer;
import xml.authorship.service.authorship.service.transformer.XHTMLTransformer;

@RestController
@RequestMapping("transform")
public class TransformController {

    @Autowired
    private PDFTransformer pdfTransformer;

    @Autowired
    private XHTMLTransformer xhtmlTransformer;


    @GetMapping(value = "/pdf/{authorshipId}")
    public String transformPDF(@PathVariable String authorshipId){
        try {
            pdfTransformer.generatePDF(authorshipId);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }

    @GetMapping(value = "/xhtml/{authorshipId}")
    public String transformXHTML(@PathVariable String authorshipId){
        try {
            xhtmlTransformer.generateHTML(authorshipId);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }
}
