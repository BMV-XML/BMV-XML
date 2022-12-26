package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

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
