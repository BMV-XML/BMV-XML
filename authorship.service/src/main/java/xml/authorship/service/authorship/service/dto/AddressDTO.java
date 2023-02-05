package xml.authorship.service.authorship.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    protected String street;
    protected String number;
    protected String postalNumber;
    protected String city;
    protected String country;
}
