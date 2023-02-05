package xml.stamp.service.stamp.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xml.stamp.service.stamp.service.Application;
import xml.stamp.service.stamp.service.dto.*;
import xml.stamp.service.stamp.service.dto.request.RequestForStampDTO;
import xml.stamp.service.stamp.service.service.AuthenticationService;
import xml.stamp.service.stamp.service.service.OfficialService;
import xml.stamp.service.stamp.service.service.ReportService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OfficialController {

    @Autowired
    private OfficialService officialService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ReportService reportService;

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

    @PostMapping(value="report", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getReport(@RequestBody RangeDTO rangeDTO,
                                            @RequestHeader(name = "username") String username,
                                            @RequestHeader(name = "password") String password) throws Exception {
        System.out.println("********************************* report **************");
        LoginDTO l = new LoginDTO(username, password);
        l.setService(Application.OFFICIAL);
        System.out.println("  REPORT DATES");
        System.out.println(rangeDTO.getStartDate());
        System.out.println(rangeDTO.getEndDate());
        if (!authenticationService.authenticate(l))
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(reportService.generateReportPDF(rangeDTO), HttpStatus.OK);
    }
}
