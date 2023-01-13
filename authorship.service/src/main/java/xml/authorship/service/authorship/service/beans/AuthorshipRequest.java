package xml.authorship.service.authorship.service.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Zahtjev", propOrder = {
        "recipient",
        "submitter",
        "commissioner",
        "authorsWork",
        "author",
        "id",
        "submissionDate",
        "attachments"
})
@XmlRootElement(name = "Zahtjev")
public class AuthorshipRequest {

    @XmlElement(name = "Primalac", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private Recipient recipient;

    @XmlElement(name = "Podnosilac", required = true)
    private Submitter submitter;

    @XmlElement(name = "Punomocnik")
    private Commissioner commissioner;

    @XmlElement(name = "Autorsko_djelo", required = true)
    private AuthorsWork authorsWork;

    @XmlElement(name = "Autor")
    private Author author;

    @XmlElement(name = "ID", required = true)
    private String id;

    @XmlElement(name = "Datum_podnosenja", required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate submissionDate;

    @XmlElement(name = "Prilozi")
    private Attachments attachments;

}
