package xml.authorship.service.authorship.service.beans;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Autor", propOrder = {
        "name",
        "surname",
        "pseudonym",
        "address",
        "citizenship",
        "yearOfDeath",
        "anonymousAuthor"
})
@XmlRootElement(name = "Autor")
public class Author {

    @XmlElement(name = "Ime")
    private String name;

    @XmlElement(name = "Prezime")
    private String surname;

    @XmlElement(name = "Pseudonim")
    private String pseudonym;

    @XmlElement(name = "Adresa")
    private Address address;

    @XmlElement(name = "Drzavljanstvo")
    private String citizenship;

    @XmlElement(name = "Godina smrti")
    private int yearOfDeath;

    @XmlElement(name = "Anonimni_autor", required = true)
    private Checkbox anonymousAuthor;

}
