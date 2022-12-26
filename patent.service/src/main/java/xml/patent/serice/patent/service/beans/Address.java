package xml.patent.serice.patent.service.beans;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Adresa", propOrder = {
        "street",
        "number",
        "postNumber",
        "city",
        "country"})
@XmlRootElement(name = "Adresa", namespace = "http://www.ftn.uns.ac.rs/base-schame")
public class Address {
    @XmlElement(name = "Ulica", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private String street;

    @XmlElement(name = "Broj", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private String number;

    @XmlElement(name = "Postanski_broj", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private String postNumber;

    @XmlElement(name = "Grad", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private String city;

    @XmlElement(name = "Drzava", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private String country;

}
