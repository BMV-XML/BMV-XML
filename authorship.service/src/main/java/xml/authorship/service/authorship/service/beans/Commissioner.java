package xml.authorship.service.authorship.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Punomocnik", propOrder = {
        "name",
        "surname",
        "address"
})
@XmlRootElement(name = "Punomocnik")
public class Commissioner {

    @XmlElement(name = "Ime", required = true)
    private String name;

    @XmlElement(name = "Prezime", required = true)
    private String surname;

    @XmlElement(name = "Adresa", required = true)
    private Address address;
}
