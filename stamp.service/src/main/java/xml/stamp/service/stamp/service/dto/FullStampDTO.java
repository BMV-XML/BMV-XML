package xml.stamp.service.stamp.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xml.stamp.service.stamp.service.dto.request.AttachmentDataDTO;
import xml.stamp.service.stamp.service.dto.request.EntityDTO;
import xml.stamp.service.stamp.service.dto.request.RecepientDTO;
import xml.stamp.service.stamp.service.dto.request.StampDTO;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.time.LocalDate;
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
public class FullStampDTO implements Serializable {

    private List<EntityDTO> applicants;
    private EntityDTO lowyer;
    private EntityDTO commonRepresentative;
    private StampDTO stamp;
    private RecepientDTO recepient;
    private AttachmentDataDTO attachmentData;
    private double baseTax;
    private double classTax;
    private double graphicSolutionTax;
    private double totalTax;
    private LocalDate applicationDate;
    private String id;
    private boolean hasSolution;
}
