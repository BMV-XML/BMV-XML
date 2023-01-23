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
@XmlType(name = "Podaci_o_prilozima", propOrder = {"exampleStamp", "listOfGoodsAndServices",
        "authority", "generalAuthorityAddedBefore", "authorityWillBeAddedAfter",
        "generalActOnCollectiveStampOrGuaranteeStamp", "proofOfRightOfPriority",
        "proofOfTaxPayment"})
@XmlRootElement(name = "Podaci_o_prilozima")
public class AttachmentData {

    @XmlElement(name = "Primer_znaka", required = true)
    protected Attachment exampleStamp;

    @XmlElement(name = "Spisak_robe_i_usluga", required = true)
    protected Attachment listOfGoodsAndServices;

    @XmlElement(name = "Punomocje", required = true)
    protected Attachment authority;

    @XmlElement(name = "Generalno_punomocje_ranije_prilozeno", required = true)
    protected Attachment generalAuthorityAddedBefore;

    @XmlElement(name = "Punomocje_ce_biti_naknadno_dostavljeno", required = true)
    protected SubsequentAttachment authorityWillBeAddedAfter;

    @XmlElement(name = "Opsti_akt_o_kolektivnom_zigu_ili_zigu_garancije", required = true)
    protected Attachment generalActOnCollectiveStampOrGuaranteeStamp;

    @XmlElement(name = "Dokaz_o_pravu_prvenstva", required = true)
    protected Attachment proofOfRightOfPriority;

    @XmlElement(name = "Dokaz_o_uplati_takse", required = true)
    protected Attachment proofOfTaxPayment;



    @Override
    public String toString() {
        return "AttachmentData{" +
                "exampleStamp='" + exampleStamp + '\'' +
                ", listOfGoodsAndServices='" + listOfGoodsAndServices + '\'' +
                ", authority='" + authority + '\'' +
                ", generalAuthorityAddedBefore='" + generalAuthorityAddedBefore + '\'' +
                ", authorityWillBeAddedAfter='" + authorityWillBeAddedAfter + '\'' +
                ", generalActOnCollectiveStampOrGuaranteeStamp='" + generalActOnCollectiveStampOrGuaranteeStamp + '\'' +
                ", proofOfRightOfPriority='" + proofOfRightOfPriority + '\'' +
                ", proofOfTaxPayment='" + proofOfTaxPayment + '\'' +
                "}\n";
    }
}
