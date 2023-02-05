package xml.main.main.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xml.main.main.service.dto.RangeDTO;
import xml.main.main.service.dto.ReportDTO;
import xml.main.main.service.service.ReportService;

@RestController
@RequestMapping(value = "report", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(value = "patent", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ReportDTO> getPatentReport(@RequestBody RangeDTO rangeDTO) throws Exception {
        return new ResponseEntity<>(reportService.getPatentReportForRange(rangeDTO), HttpStatus.OK);
    }

    @PostMapping(value = "stamp", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ReportDTO> getStampreport(@RequestBody RangeDTO rangeDTO) throws Exception {
        return new ResponseEntity<>(reportService.getStampReportForRange(rangeDTO), HttpStatus.OK);
    }
}
