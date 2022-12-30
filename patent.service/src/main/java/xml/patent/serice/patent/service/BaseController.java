package xml.patent.serice.patent.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xml.patent.serice.patent.service.transformer.XHTMLTransformer;

@RestController
@RequestMapping(value = "base")
public class BaseController {

    @GetMapping
    public String transform(){
        try {
            //PDFTransformer PDFTransformer = new PDFTransformer();
            //PDFTransformer.generatePDF();
            XHTMLTransformer xhtmlTransformer = new XHTMLTransformer();
            xhtmlTransformer.generateMYHTML();
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }
}
