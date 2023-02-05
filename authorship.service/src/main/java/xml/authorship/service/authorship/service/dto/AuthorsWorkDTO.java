package xml.authorship.service.authorship.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorsWorkDTO {

    private String title;
    private String alternateTitle;
    private boolean remade;
    private String remadeTitle;
    private String name;
    private String surname;
    private String wayOfUsage;
    private String form;
    private boolean madeInWorkRelationship;
    private String type;
    private boolean completed;


//    title: string | null
//    alternateTitle: string | null
//    isRemade: boolean
//    remadeTitle: string | null
//    name: string | null
//    surname: string | null
//    wayOfUsage: string | null
//    form: string | null
//    madeInWorkRelationship: boolean
//    type: string | null
//    completed: boolean
}
