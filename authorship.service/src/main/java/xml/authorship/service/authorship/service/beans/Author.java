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
        "person",
        "pseudonym",
        "yearOfDeath",
        "anonymousAuthor"
})
@XmlRootElement(name = "Autor")
public class Author {

    @XmlElement(name = "Osoba")
    private Person person;

    @XmlElement(name = "Pseudonim")
    private String pseudonym;

    @XmlElement(name = "Godina_smrti")
    private int yearOfDeath;

    @XmlElement(name = "Anonimni_autor", required = true)
    private Checkbox anonymousAuthor;

    public boolean contains(String s) {
        if (person.contains(s))
            return true;
        if (pseudonym.toLowerCase().contains(s))
            return true;
        if (String.valueOf(yearOfDeath).contains(s))
            return true;
        return false;
    }
}
