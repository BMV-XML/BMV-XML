import { AddressDto } from "../../patent/models/entity-dto"

export interface AuthorDto extends AddressDto {
    phone: string | null
    fax: string | null
    email: string | null
    name: string | null
    surname: string | null
    citizenship: string | null
    completed: boolean
    alive: boolean
    id: number
    yearOfDeath: string | null
  }