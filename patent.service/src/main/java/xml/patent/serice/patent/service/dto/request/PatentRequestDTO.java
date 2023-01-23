package xml.patent.serice.patent.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "root", propOrder = {
        "titles",
        "commissioner",
        "submitter",
        "inventor",
        "submitterIsTheInventor",
        "inventorWantsToBeListed",
})
@XmlRootElement(name = "root")
public class PatentRequestDTO implements Serializable {
    private List<TitleDTO> titles;
    private EntityDTO commissioner;
    private EntityDTO submitter;
    private EntityDTO inventor;
    private boolean submitterIsTheInventor;
    private boolean inventorWantsToBeListed;
    private boolean commissionerForRepresentation;
    private boolean commissionerForLetters;
    private boolean commissionerForJointRepresentation;
    private boolean notifyMeViaLetters;
    private boolean notifyMeViaEmails;
    private boolean additionalPatent;
    private boolean separatedPatent;
    private AddressDTO address;
    private PreviousPatentDTO previousPatent;
    private ArrayList<PreviousPatentDTO> priorityPatent;

    @Override
    public String toString() {
        return "PatentRequestDTO{" +
                "titles=" + titles +
                ", commissioner=" + commissioner +
                ", submitter=" + submitter +
                ", inventor=" + inventor +
                ", submitterIsTheInventor=" + submitterIsTheInventor +
                ", inventorWantsToBeListed=" + inventorWantsToBeListed +
                ", commissionerForRepresentation=" + commissionerForRepresentation +
                ", commissionerForLetters=" + commissionerForLetters +
                ", commissionerForJointRepresentation=" + commissionerForJointRepresentation +
                ", notifyMeViaLetters=" + notifyMeViaLetters +
                ", notifyMeViaEmails=" + notifyMeViaEmails +
                ", additionalPatent=" + additionalPatent +
                ", separatedPatent=" + separatedPatent +
                ", address=" + address +
                ", previousPatent=" + previousPatent +
                ", priorityPatent=" + priorityPatent +
                '}';
    }
}


/*

  titles: TitleDto[]

  commissioner: EntityDto | undefined
  submitter: EntityDto | undefined
  inventor: EntityDto | undefined

  submitterIsTheInventor: boolean
  inventorWantsToBeListed: boolean
  commissionerForRepresentation: boolean
  commissionerForLetters: boolean
  commissionerForJointRepresentation: boolean

  address: AddressDto | undefined
  notifyMeViaLetters: boolean
  notifyMeViaEmails: boolean

  additionalPatent: boolean
  separatedPatent: boolean
  previousPatent: PreviousPatentDto | undefined

  priorityPatent: PreviousPatentDto[]
 */