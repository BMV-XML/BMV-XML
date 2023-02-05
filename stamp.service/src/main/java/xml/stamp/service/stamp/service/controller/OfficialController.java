package xml.stamp.service.stamp.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xml.stamp.service.stamp.service.dto.FullStampDTO;
import xml.stamp.service.stamp.service.dto.SearchExpressionDTO;
import xml.stamp.service.stamp.service.dto.SimpleViewStampDTO;
import xml.stamp.service.stamp.service.dto.request.RequestForStampDTO;
import xml.stamp.service.stamp.service.service.OfficialService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OfficialController {

    @Autowired
    private OfficialService officialService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<SimpleViewStampDTO>> getListOfPatents(){
        try {
            System.out.println("************************************** get list *****************");
            return new ResponseEntity<List<SimpleViewStampDTO>>(officialService.getListOfStamp(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping(value = "get/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<FullStampDTO> getPatent(@PathVariable String id){
        try {
            System.out.println("************************************** get patent *****************");
            return new ResponseEntity<FullStampDTO>(officialService.getStamp(getStampId(id)), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    private String getStampId(String id) {
        String[] elements = id.split("-");
        return elements[0]+"-"+elements[1]+"/"+elements[2];
    }



    @PostMapping(value = "search", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<SimpleViewStampDTO>> getListOfPatentsSearched(@RequestBody List<SearchExpressionDTO> expressions){
        try {
            System.out.println("************************************** get list - SEARCH *****************");
            List<String> searchBy = new ArrayList<>();
            for (SearchExpressionDTO e: expressions){
                System.out.println(e.getName());
                searchBy.add(e.getName());
            }
            return new ResponseEntity<List<SimpleViewStampDTO>>(officialService.getListOfPatentSearched(searchBy), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
