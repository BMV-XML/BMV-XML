package xml.stamp.service.stamp.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "root", propOrder = {
        "applicants",
        "lowyer",
        "commonRepresentative",
        "stamp",
        "recepient",
        "attachmentData",
})
@XmlRootElement(name = "root")
public class RequestForStampDTO implements Serializable {

    private List<EntityDTO> applicants;
    private EntityDTO lowyer;
    private EntityDTO commonRepresentative;
    private StampDTO stamp;
    private RecepientDTO recepient;
    private AttachmentDataDTO attachmentData;

}
