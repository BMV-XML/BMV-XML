package xml.stamp.service.stamp.service.model;


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
@XmlType(propOrder = {"attachmentWillBeDeliveredLater", "dateOfDeliveringOfAttachment"})
public class SubsequentAttachment {

    @XmlElement(name="Prilog_ce_se_naknadno_dostaviti")
    private Checkbox attachmentWillBeDeliveredLater;

    @XmlElement(name="Datum")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateOfDeliveringOfAttachment;
}
