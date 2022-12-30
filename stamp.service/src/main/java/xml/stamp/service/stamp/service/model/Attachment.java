package xml.stamp.service.stamp.service.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"submittedAttachment", "path"})

public class Attachment {

    @XmlElement(name="Predat_prilog")
    private Checkbox submittedAttachment;

    @XmlElement(name="Putanja_do_priloga")
    private String path;

}
