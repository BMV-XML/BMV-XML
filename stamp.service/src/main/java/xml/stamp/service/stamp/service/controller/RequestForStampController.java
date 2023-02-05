package xml.stamp.service.stamp.service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import xml.stamp.service.stamp.service.dto.SimpleViewStampDTO;
import xml.stamp.service.stamp.service.dto.request.RequestForStampDTO;
import xml.stamp.service.stamp.service.dto.StatusDTO;
import xml.stamp.service.stamp.service.exceptions.NotValidException;
import xml.stamp.service.stamp.service.model.RequestForStamp;
import xml.stamp.service.stamp.service.service.OfficialService;
import xml.stamp.service.stamp.service.service.RequestForStampService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "stamp")
@CrossOrigin
public class RequestForStampController {

    @Autowired
    private RequestForStampService service;

    @Autowired
    private OfficialService officialService;


    @PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StatusDTO> savePatentRequest(@RequestBody RequestForStampDTO stampRequest) {
        System.out.println(stampRequest);
        try {
            this.service.saveStampRequest(stampRequest);
            return new ResponseEntity<>(new StatusDTO(true, "", 0), HttpStatus.OK);
        } catch (NotValidException notValidException){
            notValidException.printStackTrace();
            return new ResponseEntity<>(new StatusDTO(false, notValidException.getMessage(), notValidException.getCode()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new StatusDTO(false, "Unexpected exception", 0), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/list/soluted", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<SimpleViewStampDTO>> getListOfPatents(){
        try {
            System.out.println("************************************** get list *****************");
            return new ResponseEntity<List<SimpleViewStampDTO>>(officialService.getListSolutedOfStamp(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }






















// KONTROLNA TACKA

    @PostMapping(value = "/xml", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> saveRequestForStamp(@RequestBody RequestForStamp requestForStamp) throws Exception{
        String response = service.saveRequestForStamp(requestForStamp);
        return new ResponseEntity<String>("radi", HttpStatus.OK);
    }

    /*
    @GetMapping(value = "get/document/{id}", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getRequestForStampById(@PathVariable String id) throws Exception {
        System.out.println("id: " + id);
        String response = service.getRequestForStampById(id);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
     */










    @GetMapping(value = "filter", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> filter() throws Exception {
        String response = service.filter();
        return new ResponseEntity<String>(response, HttpStatus.OK);

    }

    @GetMapping(value = "fusekiSearch/{name}")
    public ResponseEntity<String> searchFromRDF(@PathVariable("name") String name) throws IOException {
        ArrayList<String> result = service.searchByMetadata(name);
        String output="";
        for(String s: result){
            output +="\n" + s;
        }
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
