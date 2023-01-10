package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Patent", propOrder = {
        "patentId",
        "submissionDate",
        "country"
})
@XmlRootElement(name = "Patent")
public class Patent {
    @XmlElement(name = "Broj_prjave", required = true)
    private String patentId;
    @XmlElement(name = "Datum_podnosenja", required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate submissionDate;
    @XmlElement(name = "Drzava", required = false)
    private String country;

    @XmlAttribute(name = "rel", required = true)
    protected String relation;
    @XmlAttribute(name = "href", required = true)
    protected String href;
}
