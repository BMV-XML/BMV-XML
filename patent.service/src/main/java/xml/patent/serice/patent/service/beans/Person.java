package xml.patent.serice.patent.service.beans;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlType(name = "TFizicko_lice", propOrder = {
        "address",
        "contact",
        "name",
        "surname",
        "citizenship"}, namespace = "http://www.ftn.uns.ac.rs/base-schame")
@XmlRootElement(name = "TFizicko_lice", namespace = "http://www.ftn.uns.ac.rs/base-schame")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person extends GlobalEntity {

    @XmlElement(name = "Ime", required = true)
    private String name;

    @XmlElement(name = "Prezime", required = true)
    private String surname;

    @XmlElement(name = "Drzavljanstvo", required = true)
    private String citizenship;

}
