import { Injectable } from '@angular/core';
import { AuthorshipRequestDto } from '../models/authtorship-request-dto';
import * as JsonToXML from "js2xmlparser";
import { environment } from 'environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthorDto } from '../models/author';
import { SearchBy } from 'modules/patent/models/search-by';
import { SearchDTO } from '../models/search-dto';
import { FilterDto } from 'modules/patent/models/filter-dto';

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
 
  //  addSolution(solution: AddSolutionDto){
  //    const log = JsonToXML.parse("root", solution);
  //    return this.httpClient.post(this.api_path + "solution/save", log, {responseType: 'text'});
 
  //  }
 
  //  getSolution(requestId: string) {//TODO: updatuj da koristis true key
  //    //requestId = "P-9856-23"
  //    return this.httpClient.get(this.api_path + "solution/get/" + requestId.replace("/","-"), {responseType: 'text'});
 
  //  }
 
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
       observe: 'body',
       responseType: 'text',
       headers: {
          'Content-Type': 'multipart/form-data',
          Accept: 'application/xml'
       },
     });
   }

   setDescriptionFile(data: any) {
     return this.httpClient.post(this.api_path + "authorship/description/file", data, {responseType: 'text'});
   }
 
  //  getPatent(requestId: string) {
  //    return this.httpClient.get(this.api_path + "get/"+ requestId.replace("/", "-"), {responseType: 'text'});
 
  //  }
 
   searchMetadata(filter: FilterDto[]) {
     const log = JsonToXML.parse("FilterDTO", filter);
     return this.httpClient.post(this.api_path + "authorship/filter", log, {responseType: 'text'});
   }
 
   searchBasicData(searchBy: SearchDTO[]) {
     const log = JsonToXML.parse("SearchDTO", searchBy);
     return this.httpClient.post(this.api_path + "authorship/search", log, {responseType: 'text'});
   }
 
  //  getReportForPeriod(range: RangeDto) {
  //    const log = JsonToXML.parse("range", range);
  //    return this.httpClient.post(this.api_path + "report", log, {responseType: 'text'});
  //  }
}
