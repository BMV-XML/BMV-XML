package xml.patent.serice.patent.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xml.patent.serice.patent.service.transformer.PDFTransformer;
import xml.patent.serice.patent.service.transformer.XHTMLTransformer;

@RestController
@RequestMapping(value = "base")
public class BaseController {

    @GetMapping(value = "pdf")
    public String transformPDF(){
        try {
            PDFTransformer PDFTransformer = new PDFTransformer();
            PDFTransformer.generatePDF();
            //XHTMLTransformer xhtmlTransformer = new XHTMLTransformer();
            //xhtmlTransformer.generateMYHTML();
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }

    @GetMapping(value = "xhtml")
    public String transformXHTML(){
        try {
            XHTMLTransformer xhtmlTransformer = new XHTMLTransformer();
            xhtmlTransformer.generateMYHTML();
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }
}
