import {TitleDto} from "./title-dto";
import {PreviousPatentDto} from "./previous-patent-dto";

export interface FullPatentDto {
  acknowladgedDateOfSubmission: number[]
  additionalPatent: string//boolean
  applicationDate: number[]
  country: string[]
  deliveryCity: string[]
  deliveryCountry: string[]
  deliveryNumber: string[]
  deliveryPostNumber: number[]
  deliveryStreet: string[]
  emailNotification: string[] //bool
  hasSolution: string[] //bool
  letterNotification: string[] //bool
  patentId: string[]
  previousPatentId: string[]
  priorityRights: PreviousPatentDto[]
  recipientCity: string[]
  recipientCountry: string[]
  recipientName: string[]
  recipientNumber: string[]
  recipientPostNumber: string[]
  recipientStreet: string[]
  separatedPatent: string[]//bool
  solution: any
  submissionDate: number[]
  titles: TitleDto[]


  submitterName: string[]
    submitterSurname: string[]
    submitterCitizenship: string[]
    submitterBusinessName: string[]
    submitterStreet: string[]
    submitterNumber: string[]
    submitterPostNumber: string[]
    submitterCity: string[]
    submitterCountry: string[]
    submitterPhone: string[]
    submitterFax: string[]
    submitterEmail: string[]
    submitterIsTheInventor: string[]
    submitterPerson: string[]

    commissionerName: string[]
    commissionerSurname: string[]
    commissionerCitizenship: string[]
    commissionerBusinessName: string[]
    commissionerStreet: string[]
    commissionerNumber: string[]
    commissionerPostNumber: string[]
    commissionerCity: string[]
    commissionerCountry: string[]
    commissionerPhone: string[]
    commissionerFax: string[]
    commissionerEmail: string[]
    commissionerForRepresentation: string[]
    commissionerForReceivingLetters: string[]
    commissionercommonRepresentative: string[]
    commissionerPerson: string[]

   inventorName: string[]
   inventorSurname: string[]
    inventorCitizenship: string[]
    inventorBusinessName: string[]
    inventorStreet: string[]
    inventorNumber: string[]
    inventorPostNumber: string[]
    inventorCity: string[]
    inventorCountry: string[]
    inventorPhone: string[]
    inventorFax: string[]
    inventorEmail: string[]
    wantToBeListed: string[]
    inventorsPerson: string[]
}
