import { AuthorDto } from "modules/authorship/models/author"
import { EntityDto } from "modules/patent/models/entity-dto"


export interface AuthorshipRequestDto{
  titles: string
  commissioner: EntityDto
  submitter: EntityDto
  inventor: EntityDto
  authors: AuthorDto[]
  submitterIsTheInventor: boolean
  inventorWantsToBeListed: boolean
  commissionerForRepresentation: boolean
  commissionerForLetters: boolean
  commissionerForJointRepresentation: boolean

//   address: AddressDto
  notifyMeViaLetters: boolean
  notifyMeViaEmails: boolean

  additionalPatent: boolean
  separatedPatent: boolean

  authorsWork: AuthorsWorkDto

}

export interface AuthorsWorkDto {
  title: string
  alternateTitle: string
  isRemade: boolean
  remadeTitle: string
  name: string
  surname: string
  wayOfUsage: string
  form: string
  type: string
}