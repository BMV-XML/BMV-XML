package xml.main.main.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xml.main.main.service.beans.Checkbox;
import xml.main.main.service.beans.RequestSolution;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SolutionDTO {
    private LocalDate date;
    private String requestId;
    private String officialName;
    private String officialSurname;
    private String rejectionText;
    private boolean approved;

    public SolutionDTO(RequestSolution solution) {
        this.date = solution.getDate();
        this.requestId = solution.getRequestId();
        this.officialSurname = solution.getOfficialSurname();
        this.officialName = solution.getOfficialName();
        this.rejectionText = solution.getRejectionText();
        this.approved = solution.getApproved() == Checkbox.DA;
    }
}
