package xml.authorship.service.authorship.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDTO {
    private LocalDate date;
    private String requestId;
    private String officialName;
    private String officialSurname;
    private String rejectionText;
    private boolean approved;
}
