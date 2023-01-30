package xml.stamp.service.stamp.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Prvenstvo", propOrder = {"checkbox", "reason"})
@XmlRootElement(name = "Prvenstvo")
public class Priority {
    @XmlElement(name = "Postoji_prvenstvo", required = false)
    private Checkbox checkbox;
    @XmlElement(name = "Osnov", required = false)
    private String reason;
}
