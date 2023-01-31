import {EntityDto} from "../../patent/models/entity-dto";
import {RecepientDto} from "./recepient-dto";
import {StampDTO} from "./stamp-dto";
import {AttachmentDataDto} from "./attachment-data-dto";

export interface StampRequestDto{
    applicants: string
    lowyer: EntityDto
    commonRepresentative: EntityDto
    recepient : RecepientDto,
    stamp : StampDTO
    attachmentData:AttachmentDataDto

}
