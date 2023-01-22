package xml.main.main.service.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solution", propOrder = {
        "date",
        "requestId",
        "officialName",
        "officialSurname",
        "rejectionText",
        "approved"
})
@XmlRootElement(name = "solution")
public class RequestSolution {
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;

    @XmlElement(name = "id", required = true)
    private String requestId;

    @XmlElement(name = "official_name", required = true)
    private String officialName;

    @XmlElement(name = "official_surname", required = true)
    private String officialSurname;

    @XmlElement(name = "rejection_text", required = true)
    private String rejectionText;

    @XmlElement(name = "approved", required = true)
    private Checkbox approved;

    public RequestSolution(LocalDate date, String requestId, String officialName, String officialSurname, String rejectionText) {
        this.date = date;
        this.requestId = requestId;
        this.officialName = officialName;
        this.officialSurname = officialSurname;
        this.rejectionText = rejectionText;
    }
}
