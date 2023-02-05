package xml.authorship.service.authorship.service.dto.report;

import lombok.*;
import xml.authorship.service.authorship.service.beans.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Data
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
