package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;


@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name="Ime")
public class Predicate {

    @XmlAttribute(name = "property", required = true)
    protected String property;
    @XmlAttribute(name = "datatype", required = true)
    protected String datatype;
    @XmlValue()
    private String text;
}
