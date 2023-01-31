package xml.stamp.service.stamp.service.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntityDTO extends AddressDTO {
    private int id;
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

