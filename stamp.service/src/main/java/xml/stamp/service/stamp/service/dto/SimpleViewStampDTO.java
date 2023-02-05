package xml.stamp.service.stamp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "root", propOrder = {
        "id",
        "applicationDate",
        "applicant",
        "hasSolution"
})
@XmlRootElement(name = "root")
public class SimpleViewStampDTO {
    private String id;
    private LocalDate applicationDate;
    private String applicant;
    private boolean hasSolution;
}
