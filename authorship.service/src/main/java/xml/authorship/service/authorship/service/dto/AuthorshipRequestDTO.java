package xml.authorship.service.authorship.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xml.authorship.service.authorship.service.beans.Attachments;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "root", propOrder = {
        "submitter",
        "commissioner",
        "authors",
        "submitterIsAuthor",
        "anonymusAuthor",
        "authorsWork",
})
@XmlRootElement(name = "root")
public class AuthorshipRequestDTO implements Serializable {

    private EntityDTO submitter;
    private EntityDTO commissioner;
    private List<AuthorDTO> authors;
    private boolean submitterIsAuthor;
    private boolean anonymusAuthor;
    private AuthorsWorkDTO authorsWork;
    private AttachmentsDTO attachments;


    /*
      submitter: EntityDto | null
      commissioner: EntityDto | null
      authors: AuthorDto[]
      submitterIsAuthor: boolean
      anonymusAuthor: boolean
      authorsWork: AuthorsWorkDto | null
     */
}
