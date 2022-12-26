package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Punomocnik", propOrder = {
        "globalEntity",
        "commissionerForRepresentation",
        "commissionerForReceivingLetters",
        "commonRepresentative"
})
@XmlRootElement(name = "Punomocnik")
public class Commissioner {
    @XmlElement(name = "Osoba", required = true)
    private GlobalEntity globalEntity;
    @XmlElement(name = "Punomocnik_za_zastupanje", required = true)
    private Checkbox commissionerForRepresentation;
    @XmlElement(name = "Punomocnik_za_prijem_pismena", required = true)
    private Checkbox commissionerForReceivingLetters;
    @XmlElement(name = "Zajednicki_predstavnik", required = true)
    private Checkbox commonRepresentative;
}
