package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Naslov", propOrder = {
        "language",
        "title"
})
@XmlRootElement(name = "Naslov")
public class Title {
    @XmlAttribute(name = "jezik", required = true)
    private String language;
    @XmlValue
    private String title;
    @XmlAttribute(name = "property", required = true)
    protected String property;
    @XmlAttribute(name = "datatype", required = true)
    protected String datatype;
}
