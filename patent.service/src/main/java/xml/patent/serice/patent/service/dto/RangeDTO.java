package xml.patent.serice.patent.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class RangeDTO {
    //dd.MM.yyyy.
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
