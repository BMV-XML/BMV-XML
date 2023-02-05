package xml.stamp.service.stamp.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;


@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Primalac", propOrder = {"address", "name"})
@XmlRootElement(name = "Primalac", namespace = "http://www.ftn.uns.ac.rs/base-schame")

public class Recepient {

    @XmlElement(name = "Adresa", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private Address address;

    @XmlElement(name = "Naziv", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private String name;

    public boolean contains(String s) {
        if(address.contains(s))
            return true;
        if(name.contains(s))
            return true;
        return false;
    }
}
