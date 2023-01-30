package xml.main.main.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xml.main.main.service.beans.LocalDateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSolutionDTO {

    private String username;
    private String requestId;
    private boolean approved;
    private String rejectionText;

    private String requestDate;

    public LocalDate getRequestDateAsDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        return LocalDate.parse(requestDate, formatter);
    }

}
