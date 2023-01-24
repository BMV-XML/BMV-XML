package xml.authorship.service.authorship.service.beans;

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
        "submitterTheAuthor"
})
@XmlRootElement(name = "Podnosilac")
public class Submitter {

    @XmlElement(name = "Osoba", required = true)
    private GlobalEntity globalEntity;

    @XmlElement(name = "Podnosilac_je_autor", required = true)
    private Checkbox submitterTheAuthor;
}
