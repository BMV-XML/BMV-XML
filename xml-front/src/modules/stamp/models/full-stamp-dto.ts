import {EntityDto} from "../../patent/models/entity-dto";
import {StampDTO} from "./stamp-dto";
import {RecepientDto} from "./recepient-dto";
import {AttachmentDataDto} from "./attachment-data-dto";

export interface FullStampDto{
    applicants:EntityDto[]
    lowyer:EntityDto[];
    commonRepresentative : EntityDto[];
    stamp :StampDTO[];
    recepient : RecepientDto[];
    attachmentData : AttachmentDataDto[];
    baseTax : number[];
    classTax : number[];
    graphicSolutionTax : number[];
    totalTax : number[];
    applicationDate : number[];
    id : string[];
    hasSolution : boolean[];
}
