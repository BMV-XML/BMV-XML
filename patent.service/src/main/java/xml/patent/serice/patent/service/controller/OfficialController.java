package xml.patent.serice.patent.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.patent.serice.patent.service.beans.PatentRequest;
import xml.patent.serice.patent.service.dto.FilterDTO;
import xml.patent.serice.patent.service.dto.FullPatentDTO;
import xml.patent.serice.patent.service.dto.PatentDTO;
import xml.patent.serice.patent.service.dto.SearchExpression;
import xml.patent.serice.patent.service.service.OfficialService;

import javax.xml.transform.sax.SAXResult;
import java.util.ArrayList;
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

    @GetMapping(value = "get/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<FullPatentDTO> getPatent(@PathVariable String id){
        try {
            System.out.println("************************************** get patent *****************");
            return new ResponseEntity<FullPatentDTO>(officialService.getPatent(getPatentId(id)), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }


    @PostMapping(value = "search", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<PatentDTO>> getListOfPatentsSearched(@RequestBody List<SearchExpression> expressions){
        try {
            System.out.println("************************************** get list - SEARCH *****************");
            List<String> searchBy = new ArrayList<>();
            for (SearchExpression e: expressions){
                System.out.println(e.getName());
                searchBy.add(e.getName());
            }
            return new ResponseEntity<List<PatentDTO>>(officialService.getListOfPatentSearched(searchBy), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping(value = "filter", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<PatentDTO>> getListOfPatentsFiltered(@RequestBody List<FilterDTO> filter){
        try {
            for(FilterDTO f : filter){
                System.out.println("*****");
                System.out.println(f.getOperator());
                System.out.println(f.getType());
                System.out.println(f.getValue());
            }
            return new ResponseEntity<List<PatentDTO>>(officialService.getListOfPatentFiltered(filter), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    private String getPatentId(String id) {
        String[] elements = id.split("-");
        return elements[0]+"-"+elements[1]+"/"+elements[2];
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
