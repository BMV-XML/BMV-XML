package xml.patent.serice.patent.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public LocalDate getSubmissionDateLocalDate() {
        if (submissionDate == null)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        return LocalDate.parse(submissionDate, formatter);
    }
}