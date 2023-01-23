package xml.patent.serice.patent.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {
    protected String street;
    protected String number;
    protected String postalNumber;
    protected String city;
    protected String country;

    @Override
    public String toString() {
        return "AddressDTO{" +
                "street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", postalNumber=" + postalNumber +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

/*

  street: string | null
  number: string | null
  postalNumber: string | null
  city: string | null
  country: string | null
 */