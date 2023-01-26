package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dostavljanje", propOrder = {
        "address",
        "emailNotification",
        "letterNotification"
})
@XmlRootElement(name = "Dostavljanje")
public class DeliveryData {
    @XmlElement(name = "Adresa", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private Address address;
    @XmlElement(name = "Obavesti_me_elektronskim_putem", required = true)
    private Checkbox emailNotification;
    @XmlElement(name = "Obavesti_me_preko_poste", required = true)
    private Checkbox letterNotification;

    public boolean contains(String s) {
        return address.contains(s);
    }
}
