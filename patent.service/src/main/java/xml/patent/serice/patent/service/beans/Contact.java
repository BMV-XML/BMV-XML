package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Kontakt", propOrder = {
        "phoneNumber",
        "faxNumber",
        "email"})
@XmlRootElement(name = "Kontakt",  namespace = "http://www.ftn.uns.ac.rs/base-schame")
public class Contact {

    @XmlElement(name = "Broj_telefona", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private String phoneNumber;

    @XmlElement(name = "Broj_faksa", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private String faxNumber;

    @XmlElement(name = "E_posta", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private String email;

}
