package xml.patent.serice.patent.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSolutionDTO {
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
