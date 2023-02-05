package xml.authorship.service.authorship.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityDTO extends AddressDTO{

    private String phone;
    private String fax;
    private String email;
    private String name;
    private String surname;
    private String citizenship;
    private String businessName;
    private boolean person;
}
