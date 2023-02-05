package xml.stamp.service.stamp.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlTransient
@XmlSeeAlso({Person.class, LegalEntity.class})
@XmlRootElement(namespace = "http://www.ftn.uns.ac.rs/base-schame")
public abstract class GlobalEntity {

    @XmlElement(name="Adresa", required = true)
    protected Address address;

    @XmlElement(name="Kontakt", required = true)
    protected Contact contact;

    @Override
    public String toString() {
        return "GlobalEntity{" +
                "address=" + address +
                ", contact=" + contact +
                '}';
    }

    abstract boolean contains(String s);
    protected boolean basicContains(String s){
        if (address.contains(s))
            return true;
        if (contact.contains(s))
            return true;
        return false;
    }
}
