package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Podaci_o_zahtevu", propOrder = {
        "patentId",
        "applicationDate",
        "acknowladgeDateOfSubmission"
})
@XmlRootElement(name = "Podaci_o_zahtevu")
public class PatentData {
    @XmlElement(name = "Broj_prijave", required = true)
    private String patentId;
    @XmlElement(name = "Datum_prijave", required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate applicationDate;//zasto ovaj tip?
    @XmlElement(name = "Priznati_datum_podnosenja", required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate acknowladgeDateOfSubmission;
}
