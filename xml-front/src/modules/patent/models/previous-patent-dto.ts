export interface PreviousPatentDto {
  applicationNumber: string
  submissionDate: Date | null | number[]
  country: string
  completed: boolean
  id: number
}
export interface PreviousPatentDtoFromServer {
  applicationNumber: string[]
  submissionDate: Date
  country: string[]
}
