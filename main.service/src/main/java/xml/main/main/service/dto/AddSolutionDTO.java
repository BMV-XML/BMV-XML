package xml.main.main.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSolutionDTO {

    private String username;
    private String requestId;
    private boolean approved;
    private String rejectionText;
}
