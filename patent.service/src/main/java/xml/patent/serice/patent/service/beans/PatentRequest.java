package xml.patent.serice.patent.service.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

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

    public PatentRequest() {
    }

    public PatentData getPatentData() {
        return patentData;
    }

    public void setPatentData(PatentData patentData) {
        this.patentData = patentData;
    }

    public Recepient getRecepient() {
        return recepient;
    }

    public void setRecepient(Recepient recepient) {
        this.recepient = recepient;
    }

    public List<Title> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<Title> titleList) {
        this.titleList = titleList;
    }

    public Submitter getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }

    public Commissioner getCommissioner() {
        return commissioner;
    }

    public void setCommissioner(Commissioner commissioner) {
        this.commissioner = commissioner;
    }

    public Inventor getInventor() {
        return inventor;
    }

    public void setInventor(Inventor inventor) {
        this.inventor = inventor;
    }

    public DeliveryData getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(DeliveryData deliveryData) {
        this.deliveryData = deliveryData;
    }

    public AdditionalPatent getAdditionalPatent() {
        return additionalPatent;
    }

    public void setAdditionalPatent(AdditionalPatent additionalPatent) {
        this.additionalPatent = additionalPatent;
    }

    public List<Patent> getPriorityRights() {
        return priorityRights;
    }

    public void setPriorityRights(List<Patent> priorityRights) {
        this.priorityRights = priorityRights;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getVocab() {
        return vocab;
    }

    public void setVocab(String vocab) {
        this.vocab = vocab;
    }
}
