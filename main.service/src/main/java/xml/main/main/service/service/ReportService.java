package xml.main.main.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.main.main.service.beans.Checkbox;
import xml.main.main.service.beans.RequestSolution;
import xml.main.main.service.beans.Solutions;
import xml.main.main.service.db.SolutionManager;
import xml.main.main.service.dto.RangeDTO;
import xml.main.main.service.dto.ReportDTO;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {


    @Autowired
    private SolutionManager solutionManager;

    public ReportDTO getPatentReportForRange(RangeDTO rangeDTO) throws Exception {
        Solutions solutionList = solutionManager.retrieve();
        List<RequestSolution> patents = solutionList.getPatentList();
        return getApprovedAndDeclinedRequest(patents, rangeDTO.getStartDateAsDate(), rangeDTO.getEndDateAsDate());
    }

    private ReportDTO getApprovedAndDeclinedRequest(List<RequestSolution> solutions, LocalDate start, LocalDate end){
        int approved = 0;
        int declined = 0;
        for (RequestSolution s: solutions){
            if (s.getRequestDate().isBefore(start) || s.getRequestDate().isAfter(end))
                continue;
            if (s.getApproved() == Checkbox.DA)
                approved++;
            else
                declined++;
        }
        return new ReportDTO(approved, declined);
    }
}
