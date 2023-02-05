import { AddressDto } from "../../patent/models/entity-dto"

export interface AuthorDto extends AddressDto {
    name: string | null
    surname: string | null
    citizenship: string | null
    completed: boolean
    alive: boolean | null
    id: number
    yearOfDeath: number | null
    pseudonym: string | null
  }