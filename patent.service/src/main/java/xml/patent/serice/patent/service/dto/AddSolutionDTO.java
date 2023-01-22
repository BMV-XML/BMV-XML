package xml.patent.serice.patent.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSolutionDTO {
    private String requestId;
    private String rejectionText;
    private boolean approved;
    private String username;
}
