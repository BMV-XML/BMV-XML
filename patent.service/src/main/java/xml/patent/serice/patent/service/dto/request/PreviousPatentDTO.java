package xml.patent.serice.patent.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
        //TODO: FORMAT
        return LocalDate.now();
    }
}
/*

  applicationNumber: string
  submissionDate: Date | null
  country: string

  completed: boolean
  id: number
 */