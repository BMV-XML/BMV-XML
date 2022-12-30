package xml.stamp.service.stamp.service.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Zahtev_za_zig", propOrder = {"applicants", "lawyer", "commonRepresentative", "recepient", "stampData", "taxesData", "attachmentData"})
@XmlRootElement(name = "Zahtev_za_zig")
public class RequestForStamp {

    @XmlAttribute(name="about", required = true)
    @XmlSchemaType(name="anyURI")
    private String about;

    @XmlElement(name = "Podnosioci_prijave", required = true)
    protected List<GlobalEntity> applicants;

    @XmlElement(name = "Punomocnik", required = true)
    protected GlobalEntity lawyer;

    @XmlElement(name = "Zajednicki_predstavnik", required = false)
    protected GlobalEntity commonRepresentative;

    @XmlElement(name="Primalac", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    protected Recepient recepient;

    @XmlElement(name = "Podaci_o_zigu", required = true)
    protected StampData stampData;

    @XmlElement(name = "Podaci_o_taksama", required = true)
    protected TaxesData taxesData;

    @XmlElement(name = "Podaci_o_prilozima", required = true)
    protected AttachmentData attachmentData;

    @Override
    public String toString() {
        return "RequestForStamp{" +
                "applicants=" + applicants +
                ", lawyer=" + lawyer +
                ", commonRepresentative=" + commonRepresentative +
                ", stampData=" + stampData +
                ", taxesData=" + taxesData +
                ", attachmentData=" + attachmentData +
                '}';
    }
}
