package xml.authorship.service.authorship.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewSolutionDTO {

    private String requestId;
    private String rejectionText;
    private boolean approved;
    private String username;
    private String requestDate;

    public void setRequestDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        requestDate = date.format(formatter);
    }
}
