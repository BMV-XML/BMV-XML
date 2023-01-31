package xml.stamp.service.stamp.service.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentDTO {
    String path;
    String submittedAttachment;

}
