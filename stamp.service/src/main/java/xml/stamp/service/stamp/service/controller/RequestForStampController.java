package xml.stamp.service.stamp.service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import xml.stamp.service.stamp.service.model.RequestForStamp;
import xml.stamp.service.stamp.service.service.RequestForStampService;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;


@RestController
@RequestMapping(value = "api/xml", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
@CrossOrigin
public class RequestForStampController {

    @Autowired
    private RequestForStampService service;


    @PostMapping(value = "/jaxB", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> saveRequestForStamp(@RequestBody RequestForStamp requestForStamp) throws Exception{
        String response = service.saveRequestForStamp(requestForStamp);
        return new ResponseEntity<String>("radi", HttpStatus.OK);
    }

    @GetMapping(value = "get/document/{id}", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getRequestForStampById(@PathVariable String id) throws Exception {
        System.out.println("id: " + id);
        String response = service.getRequestForStampById(id);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping(value = "filter", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> filter() throws Exception {
        String response = service.filter();
        return new ResponseEntity<String>(response, HttpStatus.OK);

    }

    @GetMapping(value = "fusekiSearch/{naziv}/{godina}")
    public ResponseEntity<String> searchFromRDF(@PathVariable("naziv") String naziv, @PathParam("godina") String godina) throws IOException {
        ArrayList<String> result = service.searchByMetadata(naziv, godina);
        String output="";
        for(String s: result){
            output +="\n" + s;
        }
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
