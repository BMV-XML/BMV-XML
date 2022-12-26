package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pronalazac", propOrder = {
        "globalEntity",
        "wantToBeListed"
})
@XmlRootElement(name = "Pronalazac")
public class Inventor {
    @XmlElement(name = "Osoba", required = true)
    private GlobalEntity globalEntity;
    @XmlElement(name = "Hoce_da_bude_naveden", required = true)
    private Checkbox wantToBeListed;
}
