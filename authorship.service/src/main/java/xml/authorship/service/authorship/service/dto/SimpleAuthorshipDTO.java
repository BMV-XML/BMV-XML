package xml.authorship.service.authorship.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xml.authorship.service.authorship.service.beans.LocalDateAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@XmlRootElement
public class SimpleAuthorshipDTO {

    private String id;

    @XmlValue
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate applicationDate;

    private String submitter;

    private boolean hasSolution;

}
