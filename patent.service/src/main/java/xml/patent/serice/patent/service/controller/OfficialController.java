package xml.patent.serice.patent.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xml.patent.serice.patent.service.dto.PatentDTO;
import xml.patent.serice.patent.service.service.OfficialService;

import java.util.List;

@RestController
public class OfficialController {

    @Autowired
    private OfficialService officialService;

    @GetMapping(value = "list", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<PatentDTO>> getListOfPatents(){
        try {
            System.out.println("************************************** get list *****************");
            return new ResponseEntity<List<PatentDTO>>(officialService.getListOfPatent(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping(value = "list/rdf")
    String getListOfPatentRdf(){
        try {
            officialService.getListOfPatentRdf();
            return "Success";
        }catch (Exception e){
            e.printStackTrace();
            return "Fail";
        }
    }
}
