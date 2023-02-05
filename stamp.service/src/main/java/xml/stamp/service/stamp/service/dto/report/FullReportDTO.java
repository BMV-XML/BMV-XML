package xml.stamp.service.stamp.service.dto.report;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xml.stamp.service.stamp.service.model.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "report", propOrder = {
        "startDate",
        "endDate",
        "number",
        "approved",
        "declined"
})
@XmlRootElement(name = "report")
public class FullReportDTO {
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
    @XmlElement
    private int number;
    @XmlElement
    private int approved;
    @XmlElement
    private int declined;
}
