export interface PreviousPatentDto {
  applicationNumber: string
  submissionDate: string
  country: string
  completed: boolean
  id: number
}
export interface PreviousPatentDtoFromServer {
  applicationNumber: string[]
  submissionDate: Date
  country: string[]
}
