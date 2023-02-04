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


    @XmlAttribute(name="about", required = true)
    @XmlSchemaType(name="anyURI")
    private String about;

    @XmlAttribute(name="vocab", required = true)
    @XmlSchemaType(name="anyURI")
    private String vocab;

    @Override
    public String toString() {
        return "PatentRequest{" +
                "patentData=" + patentData +
                ", recepient=" + recepient +
                ", titleList=" + titleList +
                ", submitter=" + submitter +
                ", commissioner=" + commissioner +
                ", inventor=" + inventor +
                ", deliveryData=" + deliveryData +
                ", additionalPatent=" + additionalPatent +
                ", priorityRights=" + priorityRights +
                ", about='" + about + '\'' +
                ", vocab='" + vocab + '\'' +
                '}';
    }


    public String getPatentId() {
        return patentData.getPatentId().getText();
    }

    public boolean contains(List<String> searchBy) {
        for (String s : searchBy){
            if (!contains(s.toLowerCase()))
                return false;
        }
        return true;
    }

    private boolean contains(String s){
        if (patentData.contains(s))
            return true;
        for (Title t: titleList){
            if (t.contains(s))
                return true;
        }
        if (submitter.getGlobalEntity().contains(s))
            return true;
        if (commissioner.getGlobalEntity().contains(s))
            return true;
        if (inventor != null && inventor.getGlobalEntity().contains(s))
            return true;
        if (deliveryData.contains(s))
            return true;
        if (additionalPatent.contains(s))
            return true;
        if (priorityRights != null)
            for (Patent p : priorityRights){
                if (p.contains(s))
                    return true;
            }
        return false;
    }
}
