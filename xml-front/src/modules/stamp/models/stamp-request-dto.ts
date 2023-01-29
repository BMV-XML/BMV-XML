import {EntityDto} from "../../patent/models/entity-dto";
import {RecepientDto} from "./recepient-dto";
import {StampDTO} from "./stamp-dto";

export interface StampRequestDto{
    applicants: EntityDto[]
    lowyer: EntityDto
    commonRepresentative: EntityDto
    recepient : RecepientDto,
    stamp : StampDTO

}
