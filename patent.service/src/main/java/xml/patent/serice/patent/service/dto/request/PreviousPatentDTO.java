package xml.patent.serice.patent.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
@NoArgsConstructor
public class PreviousPatentDTO {
    private String applicationNumber;
    private String submissionDate;
    private String country;

    @Override
    public String toString() {
        return "PreviousPatentDTO{" +
                "applicationNumber='" + applicationNumber + '\'' +
                ", submissionDate='" + submissionDate + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public LocalDate getSubmissionDateLocalDate() {//2023-01-02
        if (submissionDate == null)
            return null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
            return LocalDate.parse(submissionDate, formatter);
        }catch (DateTimeParseException e){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(submissionDate, formatter);
        }
    }
}