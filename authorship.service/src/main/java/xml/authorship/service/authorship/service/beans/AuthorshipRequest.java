package xml.authorship.service.authorship.service.beans;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Zahtjev", propOrder = {
        "authorshipData",
        "recipient",
        "submitter",
        "commissioner",
        "authorsWork",
        "authors",
        "attachments"
})
@XmlRootElement(name = "Zahtjev")
public class AuthorshipRequest {

    @XmlElement(name = "Podaci_o_zahtjevu", required = true)
    private AuthorshipData authorshipData;

    @XmlElement(name = "Primalac", required = true, namespace = "http://www.ftn.uns.ac.rs/base-schame")
    private Recipient recipient;

    @XmlElement(name = "Podnosilac", required = true)
    private Submitter submitter;

    @XmlElement(name = "Punomocnik")
    private Commissioner commissioner;

    @XmlElement(name = "Autorsko_djelo", required = true)
    private AuthorsWork authorsWork;

    @XmlElement(name = "Autor")
    private List<Author> authors;

//    @XmlElement(name = "ID", required = true)
//    private String id;
//
//    @XmlElement(name = "Datum_podnosenja", required = true)
//    @XmlJavaTypeAdapter(LocalDateAdapter.class)
//    private LocalDate submissionDate;

    @XmlElement(name = "Prilozi")
    private Attachments attachments;


    @XmlAttribute(name="about")  // TODO nezz cemu sluzi
    @XmlSchemaType(name="anyURI")
    private String about;

    @XmlAttribute(name="vocab")
    @XmlSchemaType(name="anyURI")
    private String vocab;


    public String getAuthorshipId() {
        return authorshipData.getID().getText();
    }

    public boolean contain(List<String> searchBy) {
        for (String s : searchBy){
            if (!contains(s.toLowerCase()))
                return false;
        }
        return true;
    }

    private boolean contains(String s){
        if (authorshipData.contains(s))
            return true;
        if (submitter.getGlobalEntity().contains(s))
            return true;
        if (commissioner != null && commissioner.getPerson().contains(s))
            return true;
        if (authorsWork.contains(s))
            return true;
        if (authors != null) {
            for (Author a : authors) {
                if (a.contains(s))
                    return true;
            }
        }
        return false;
    }
}
