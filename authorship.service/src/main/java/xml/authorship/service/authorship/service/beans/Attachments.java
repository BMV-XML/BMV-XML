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
@XmlType(name="Prilozi", propOrder = {
        "descriptionPath",
        "examplePath"
})
@XmlRootElement(name = "Prilozi")
public class Attachments {

    @XmlElement(name = "Opis_djela")
    private String descriptionPath;

    @XmlElement(name = "Primjer_djela")
    private String examplePath;
}
