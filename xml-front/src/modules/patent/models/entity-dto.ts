export interface EntityDto extends AddressDto {
  phone: string | null
  fax: string | null
  email: string | null
  name: string | null
  surname: string | null
  citizenship: string | null
  businessName: string | null
  completed: boolean
}

export interface AddressDto {
  street: string | null
  number: string | null
  postalNumber: string | null
  city: string | null
  country: string | null
}
