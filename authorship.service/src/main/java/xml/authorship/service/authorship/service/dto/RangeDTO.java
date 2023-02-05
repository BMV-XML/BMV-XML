package xml.authorship.service.authorship.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RangeDTO {

    private String startDate;
    private String endDate;


    public LocalDate getStartDateAsDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        return LocalDate.parse(startDate, formatter);
    }

    public LocalDate getEndDateAsDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        return LocalDate.parse(endDate, formatter);
    }


    public String getStartDateForDocumentName(){
        LocalDate date = getStartDateAsDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
        //return LocalDate.parse(startDate, formatter);
    }

    public String getEndDateForDocumentName(){
        LocalDate date = getStartDateAsDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
        //return LocalDate.parse(startDate, formatter);
    }
}
