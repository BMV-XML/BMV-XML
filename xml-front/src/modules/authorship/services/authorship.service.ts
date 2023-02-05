import { Injectable } from '@angular/core';
import { AuthorshipRequestDto } from '../models/authtorship-request-dto';
import * as JsonToXML from "js2xmlparser";
import { environment } from 'environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthorDto } from '../models/author';
import { SearchBy } from 'modules/patent/models/search-by';
import { SearchDTO } from '../models/search-dto';
import { FilterDto } from 'modules/patent/models/filter-dto';
import { RangeDto } from 'modules/patent/models/range-dto';
import {AddSolutionDto} from "../../shared/models/add-solution-dto";

@Injectable({
  providedIn: 'root'
})
export class AuthorshipService {

  private readonly api_path = environment.authorship_path;
  private parser: DOMParser

  constructor(private readonly httpClient: HttpClient) {
    this.parser = new DOMParser();
  }

   getAuthorshipList() {
     return this.httpClient.get(this.api_path + "authorship/get/all", {responseType: 'text'});
   }

   getPDF(id: string) {
     return this.httpClient.get(this.api_path + "transform/pdf/" + id, {responseType: 'blob'});
   }

   getHTML(id: string) {
     return this.httpClient.get(this.api_path + "transform/xhtml/" + id, {responseType: 'blob'});
   }

   getRDF(id: string) {
    const options = {
      headers: new HttpHeaders().append('Content-Type', 'application/rdf+xml'),
      responseType: 'blob' as 'json'
    };
     return this.httpClient.get(this.api_path + "authorship/getRdfMetadata?id=" + id, options);
   }

   getJSON(id: string) {
    const options = {
      headers: new HttpHeaders().append('Content-Type', 'application/json'),
      responseType: 'blob' as 'json'
    };
     return this.httpClient.get(this.api_path + "authorship/getJsonMetadata?id=" + id, options);
   }

   addSolution(solution: AddSolutionDto){
     const log = JsonToXML.parse("root", solution);
     return this.httpClient.post(this.api_path + "authorship/addSolution", log, {responseType: 'text'});

   }

   getSolution(requestId: string) {
     //requestId = "P-9856-23"
     return this.httpClient.get(this.api_path + "authorship/solution/get/" + requestId, {responseType: 'text'});

   }

   submitRequest(result: AuthorshipRequestDto, authors: AuthorDto[]) {
     console.log("*************************************************************")
     console.log(authors)
     let authorXml = JsonToXML.parse("authors", authors);
     console.log(authorXml)
     authorXml = authorXml.replace("<?xml version='1.0'?>", "")
     console.log(authorXml)

     let log = JsonToXML.parse("root", result);
     log = log.replace("<authors/>", authorXml);
     console.log(log)
     log = log.replace("null", "")
     return this.httpClient.post(this.api_path + "authorship", log, {responseType: 'text'});
   }

   setExampleFile(data: any) {
     return this.httpClient.post(this.api_path + "authorship/example/file", data,
     {
       responseType: 'text',
       headers: {
          'Accept': 'application/xml',
          'myattr' : 'my'
       },
     });
   }

   setDescriptionFile(data: any) {
     return this.httpClient.post(this.api_path + "authorship/description/file", data,
     {
        responseType: 'text',
        headers: {
            'Accept': 'application/xml',
            'myattr' : 'my'
        }
     });
   }

   getExampleFile(id: any) {
     return this.httpClient.get(this.api_path + "authorship/get/example/" + id, {responseType: 'text'})
   }

   getDescriptionFile(id: any) {
    return this.httpClient.get(this.api_path + "authorship/get/description/" + id, {responseType: 'text'})
  }

   searchMetadata(filter: FilterDto[]) {
     const log = JsonToXML.parse("FilterDTO", filter);
     return this.httpClient.post(this.api_path + "authorship/filter", log, {responseType: 'text'});
   }

   searchBasicData(searchBy: SearchDTO[]) {
     const log = JsonToXML.parse("SearchDTO", searchBy);
     return this.httpClient.post(this.api_path + "authorship/search", log, {responseType: 'text'});
   }

   getReportForPeriod(range: RangeDto) {
     const log = JsonToXML.parse("range", range);
     return this.httpClient.post(this.api_path + "authorship/report", log, {responseType: 'text'});
   }

   getAcceptedAuthorships() {
    return this.httpClient.get(this.api_path + "authorship/list/soluted", {responseType: 'text'});
   }

   convertDateToStringInReport(param: string | undefined) {
    if (param === undefined)
      return ''
    let elems = param.split(" ")
    console.log(elems)
    let dateParts = elems[0].split(".")
    let month : string = dateParts[1]
    if (dateParts[1].length === 1)
      month = '0' + dateParts[1]
    let day : string = dateParts[0]
    if (dateParts[0].length === 1)
      day = '0' + dateParts[0]
    return day + "." + month +"." + dateParts[2] + "."
  }

  convertDateToStringInRequest(param: string | undefined) {
    if (param === undefined)
      return ''
    let elems = param.split(",")
    let dateParts = elems[0].split("/")
    let month : string = dateParts[1]
    if (dateParts[1].length === 1)
      month = '0' + dateParts[1]
    let day : string = dateParts[0]
    if (dateParts[0].length === 1)
      day = '0' + dateParts[0]
    return month + "." + day +"." + dateParts[2] + "."
  }
}
