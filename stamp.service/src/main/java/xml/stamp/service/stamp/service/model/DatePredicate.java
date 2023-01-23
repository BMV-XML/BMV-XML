package xml.stamp.service.stamp.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class DatePredicate {
    @XmlAttribute(name = "property", required = true)
    protected String property;
    @XmlAttribute(name = "datatype", required = true)
    protected String datatype;
    @XmlValue
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;
}