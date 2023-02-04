package xml.authorship.service.authorship.service.beans;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlType(name = "TPoslovno_lice", propOrder = {
        "address",
        "contact",
        "businessName"}, namespace = "http://www.ftn.uns.ac.rs/base-schame")
@XmlRootElement(name = "TPoslovno_lice", namespace = "http://www.ftn.uns.ac.rs/base-schame")
@XmlAccessorType(XmlAccessType.FIELD)
public class LegalEntity extends GlobalEntity {

    @XmlElement(name = "Poslovno_ime", required = true)
    protected Predicate businessName;

    @Override
    public String toString() {
        return "LegalEntity{" +
                "businessName='" + businessName + '\'' +
                ", address=" + address +
                ", contact=" + contact +
                "}\n";
    }

    @Override
    boolean contains(String s) {
        if (basicContains(s))
            return true;
        if (businessName.getText().toLowerCase().contains(s)){
            return true;
        }
        return false;
    }

}
