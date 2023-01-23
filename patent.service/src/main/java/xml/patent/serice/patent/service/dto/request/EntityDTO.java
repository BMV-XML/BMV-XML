package xml.patent.serice.patent.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntityDTO extends AddressDTO {
    private String phone;
    private String fax;
    private String email;
    private String name;
    private String surname;
    private String citizenship;
    private String businessName;
    private boolean person;

    @Override
    public String toString() {
        return "EntityDTO{" +
                "phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", businessName='" + businessName + '\'' +
                ", person=" + person +
                '}';
    }
}


/*
  phone: string | null
  fax: string | null
  email: string | null
  name: string | null
  surname: string | null
  citizenship: string | null
  businessName: string | null
  completed: boolean
  person: boolean
 */