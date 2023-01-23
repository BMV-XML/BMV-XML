package xml.patent.serice.patent.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "titles", propOrder = {
        "language",
        "title",
        })
@XmlRootElement(name = "titles")
public class TitleDTO implements Serializable {
    @XmlAttribute(name = "jezik")
    private String language;
    @XmlElement(name = "title")
    private String title;

    @Override
    public String toString() {
        return "TitleDTO{" +
                "language='" + language + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

/*
  language: string
  title: string
 */