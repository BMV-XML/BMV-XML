package xml.stamp.service.stamp.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attachmentData", propOrder = {
        "exampleStamp",
        "listOfGoodsAndServices",
        "authority",
        "generalAuthorityAddedBefore",
        "authorityWillBeAddedAfter",
        "generalActOnCollectiveStampOrGuaranteeStamp",
        "proofOfRightOfPriority",
        "proofOfTaxPayment"
})
@XmlRootElement(name = "attachmentData")
public class AttachmentDataDTO {
    AttachmentDTO exampleStamp;
    AttachmentDTO listOfGoodsAndServices;
    AttachmentDTO authority;
    AttachmentDTO generalAuthorityAddedBefore;
    AttachmentDTO authorityWillBeAddedAfter;
    AttachmentDTO generalActOnCollectiveStampOrGuaranteeStamp;
    AttachmentDTO proofOfRightOfPriority;
    AttachmentDTO proofOfTaxPayment;
}
