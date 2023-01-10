package xml.patent.serice.patent.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xml.patent.serice.patent.service.transformer.PDFTransformer;
import xml.patent.serice.patent.service.transformer.XHTMLTransformer;

@RestController
@RequestMapping(value = "transform")
public class TransformController {

    @Autowired
    private PDFTransformer pdfTransformer;

    @Autowired
    private XHTMLTransformer xhtmlTransformer;

    @GetMapping(value = "/pdf/{patentId}")
    public String transformPDF(@PathVariable String patentId){
        try {
            pdfTransformer.generatePDF(patentId);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }

    @GetMapping(value = "/xhtml/{patentId}")
    public String transformXHTML(@PathVariable String patentId){
        try {
            xhtmlTransformer.generateHTML(patentId);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }
}
