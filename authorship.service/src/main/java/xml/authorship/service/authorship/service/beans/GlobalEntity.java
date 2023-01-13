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

    @XmlElement(name = "Adresa", required = true)
    protected Address address;

    @XmlElement(name = "Kontakt", required = true)
    protected Contact contact;

}
