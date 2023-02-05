package xml.authorship.service.authorship.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@Getter
@Setter
@NoArgsConstructor
@XmlTransient
@XmlSeeAlso({Person.class, LegalEntity.class})
public abstract class GlobalEntity {

    @XmlElement(name = "Adresa")
    protected Address address;

    @XmlElement(name = "Kontakt")
    protected Contact contact;

    abstract boolean contains(String s);

    protected boolean basicContains(String s) {
        if (address != null && address.contains(s))
            return true;
        if (contact != null && contact.contains(s))
            return true;
        return false;
    }
}
