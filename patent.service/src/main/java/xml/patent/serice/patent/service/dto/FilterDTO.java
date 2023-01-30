package xml.patent.serice.patent.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {

    private String type;
    private String operator;
    private String value;

    /*
    private String numberOperator;
    private String number;
    private String titleOperator;
    private String title;
    private String datumOperator;
    private String datum;
    private String submitterOperator;
    private String submitter;
    private String inventorOperator;
    private String inventor;
    private String commissionerOperator;
    private String commissioner;

     */
}
