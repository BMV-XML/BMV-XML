package xml.authorship.service.authorship.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authors", propOrder = {
        "name",
        "surname",
        "citizenship",
        "alive",
        "yearOfDeath",
        "pseudonym"
})
@XmlRootElement(name = "authors")
public class AuthorDTO extends AddressDTO implements Serializable {

    private String name;
    private String surname;
    private String citizenship;
    private boolean alive;
    private int yearOfDeath;
    private String pseudonym;


//    name: string | null
//    surname: string | null
//    citizenship: string | null
//    alive: boolean | null
//    yearOfDeath: number | null
}
