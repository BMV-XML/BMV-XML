package xml.stamp.service.stamp.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Kontakt", propOrder = {"phoneNumber", "faxNumber", "email"})
@XmlRootElement(name = "Kontakt", namespace = "http://www.ftn.uns.ac.rs/base-schame")
public class Contact {

    @XmlElement(name = "Broj_telefona", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    protected String phoneNumber;

    @XmlElement(name = "Broj_faksa", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    protected String faxNumber;

    @XmlElement(name = "E_posta", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    protected String email;


    @Override
    public String toString() {
        return "Contact{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", faxNumber='" + faxNumber + '\'' +
                ", email='" + email + '\'' +
                "}\n";
    }
}
