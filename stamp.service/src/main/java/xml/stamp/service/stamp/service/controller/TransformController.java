package xml.stamp.service.stamp.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xml.stamp.service.stamp.service.transform.PDFTransformer;
import xml.stamp.service.stamp.service.transform.XHTMLTransformer;

@RestController
@RequestMapping(value = "transform")
public class TransformController {
    @Autowired
    private PDFTransformer pdfTransformer;

    @Autowired
    private XHTMLTransformer xhtmlTransformer;


    @GetMapping(value = "/pdf/{stampId}")
    public String transformPDF(@PathVariable String stampId){
        try {
            pdfTransformer.generatePDF(stampId);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }

    @GetMapping(value = "/xhtml/{stampId}")
    public String transformXHTML(@PathVariable String stampId){
        try {
            xhtmlTransformer.generateHTML(stampId);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }
}
