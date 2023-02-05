package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Podnosilac", propOrder = {
        "globalEntity",
        "submitterTheInventor"
})
@XmlRootElement(name = "Podnosilac")
public class Submitter {
    @XmlElement(name = "Osoba", required = true)
    private GlobalEntity globalEntity;
    @XmlElement(name = "Podnosilac_je_i_pronalazac", required = true)
    private Checkbox submitterTheInventor;

    public String getFullname() {
        return globalEntity.getFullname();
    }
}
