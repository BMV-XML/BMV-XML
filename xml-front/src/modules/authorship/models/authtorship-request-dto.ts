import { AuthorDto } from "modules/authorship/models/author"
import { EntityDto } from "modules/patent/models/entity-dto"


export interface AuthorshipRequestDto{
  submitter: EntityDto | null
  commissioner: EntityDto | null
  authors: string | null
  submitterIsAuthor: boolean
  anonymusAuthor: boolean
  authorsWork: AuthorsWorkDto | null
  attachments: AttachmentsDto | null
}

export interface AuthorsWorkDto {
  title: string | null
  alternateTitle: string | null
  isRemade: boolean
  remadeTitle: string | null
  name: string | null
  surname: string | null
  wayOfUsage: string | null
  form: string | null
  madeInWorkRelationship: boolean
  type: string | null
  completed: boolean
}

export interface AttachmentsDto {
  descriptionPath: string | null
  examplePath: string | null
}