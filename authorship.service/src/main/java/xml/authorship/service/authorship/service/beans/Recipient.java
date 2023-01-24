package xml.authorship.service.authorship.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Primalac", propOrder = {
        "address",
        "name"})
@XmlRootElement(name = "Primalac", namespace = "http://www.ftn.uns.ac.rs/base-schame")
public class Recipient {

    @XmlElement(name = "Adresa", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private Address address;

    @XmlElement(name = "Naziv", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private String name;
}
