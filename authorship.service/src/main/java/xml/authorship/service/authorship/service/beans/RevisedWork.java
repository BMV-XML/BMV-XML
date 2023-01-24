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
@XmlType(name="Preradjeno_djelo", propOrder = {
        "title",
        "authorName",
        "authorSurname"
})
@XmlRootElement(name = "Preradjeno_djelo")
public class RevisedWork {

    @XmlElement(name = "Naslov", required = true)
    private String title;

    @XmlElement(name = "Ime_autora", required = true)
    private String authorName;

    @XmlElement(name = "Prezime_autora", required = true)
    private String authorSurname;
}
