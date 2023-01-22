import {AddressDto, EntityDto} from "./entity-dto";
import {PreviousPatentDto} from "./previous-patent-dto";
import {TitleDto} from "./title-dto";

export interface PatentRequestDto {
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
}
