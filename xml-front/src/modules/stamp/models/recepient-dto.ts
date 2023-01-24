import {AddressDto} from "../../patent/models/entity-dto";

export interface RecepientDto extends AddressDto{
    name : string | null;
}
