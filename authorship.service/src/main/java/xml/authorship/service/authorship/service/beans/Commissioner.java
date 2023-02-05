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
        "person"
})
@XmlRootElement(name = "Punomocnik")
public class Commissioner {

    @XmlElement(name = "Osoba", required = true)
    private Person person;

}
