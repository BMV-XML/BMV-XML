package xml.authorship.service.authorship.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {

    private String type;
    private String operator;
    private String value;
}
