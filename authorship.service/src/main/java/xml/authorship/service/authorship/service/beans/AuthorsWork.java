package xml.authorship.service.authorship.service.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Autorsko_djelo", propOrder = {
        "title",
        "alternateTitle",
        "revisedWork",
        "workType",
        "recordForm",
        "madeInEmployment",
        "wayOfUsage"
})
@XmlRootElement(name = "Autorsko_djelo")
public class AuthorsWork {

    @XmlElement(name = "Naslov", required = true)
    private String title;

    @XmlElement(name = "Alternativni_naslov")
    private String alternateTitle;

    @XmlElement(name = "Preradjeno_djelo")
    private RevisedWork revisedWork;

    @XmlElement(name = "Vrsta", required = true)
    private WorkType workType;

    @XmlElement(name = "Forma_zapisa", required = true)
    private RecordForm recordForm;

    @XmlElement(name = "Stvoreno_u_radnom_odnosu", required = true)
    private Checkbox madeInEmployment;

    @XmlElement(name = "Nacin_koriscenja", required = true)
    private String wayOfUsage;
}
