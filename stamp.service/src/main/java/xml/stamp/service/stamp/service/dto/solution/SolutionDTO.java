package xml.stamp.service.stamp.service.dto.solution;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}

