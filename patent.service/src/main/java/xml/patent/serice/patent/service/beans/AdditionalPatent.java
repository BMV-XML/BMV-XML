package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dodatna_prijava", propOrder = {
        "additionalPatent",
        "seperatePatent",
        "patent"
})
@XmlRootElement(name = "Dodatna_prijava")
public class AdditionalPatent {
    @XmlElement(name = "Dopunska_prijava", required = true)
    private Checkbox additionalPatent;
    @XmlElement(name = "Izdvojena_prijava", required = true)
    private Checkbox seperatePatent;
    @XmlElement(name = "Patent", required = false)
    private Patent patent;
}
