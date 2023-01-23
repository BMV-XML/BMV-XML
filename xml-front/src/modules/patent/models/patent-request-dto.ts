import {AddressDto, EntityDto} from "./entity-dto";
import {PreviousPatentDto} from "./previous-patent-dto";
import {TitleDto} from "./title-dto";

export interface PatentRequestDto {
  titles: string
  commissioner: EntityDto
  submitter: EntityDto
  inventor: EntityDto

  submitterIsTheInventor: boolean
  inventorWantsToBeListed: boolean
  commissionerForRepresentation: boolean
  commissionerForLetters: boolean
  commissionerForJointRepresentation: boolean

  address: AddressDto
  notifyMeViaLetters: boolean
  notifyMeViaEmails: boolean

  additionalPatent: boolean
  separatedPatent: boolean
  previousPatent: PreviousPatentDto

  priorityPatent: ""
}
