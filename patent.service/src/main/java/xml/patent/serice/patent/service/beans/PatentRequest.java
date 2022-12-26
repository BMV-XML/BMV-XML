package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Zahtev_za_priznavanje_patenta", propOrder = {
        "patentData",
        "recepient",
        "titleList",
        "submitter",
        "commissioner",
        "inventor",
        "deliveryData",
        "additionalPatent",
        "priorityRights"
})
@XmlRootElement(name = "Zahtev_za_priznavanje_patenta")
public class PatentRequest {

    @XmlElement(name = "Podaci_o_zahtevu", required = true)
    private PatentData patentData;
    @XmlElement(name = "Primalac", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private Recepient recepient;
    @XmlElement(name = "Naslov", required = true)
    private List<Title> titleList;
    @XmlElement(name = "Podnosilac", required = true)
    private Submitter submitter;
    @XmlElement(name = "Punomocnik", required = false)
    private Commissioner commissioner;
    @XmlElement(name = "Pronalazac", required = false)
    private Inventor inventor;
    @XmlElement(name = "Dostavljanje", required = true)
    private DeliveryData deliveryData;
    @XmlElement(name = "Dodatna_prijava", required = true)
    private AdditionalPatent additionalPatent;
    @XmlElement(name = "Prava_prvenstva", required = false)
    private List<Patent> priorityRights;

}
