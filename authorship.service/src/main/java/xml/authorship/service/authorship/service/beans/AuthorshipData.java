package xml.authorship.service.authorship.service.beans;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Podaci_o_zahtjevu", propOrder = {
        "ID",
        "applicationDate"
})
@XmlRootElement(name = "Podaci_o_zahtjevu")
public class AuthorshipData {

    @XmlElement(name = "Broj_prijave", required = true)
    private Predicate ID;

    @XmlElement(name = "Datum_prijave", required = true)
    private DatePredicate applicationDate;
}
