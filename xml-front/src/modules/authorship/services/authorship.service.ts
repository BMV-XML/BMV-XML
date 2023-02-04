import { Injectable } from '@angular/core';
import { AuthorshipRequestDto } from '../models/authtorship-request-dto';
import * as JsonToXML from "js2xmlparser";
import { environment } from 'environments/environment';
import { HttpClient } from '@angular/common/http';
import { AuthorDto } from '../models/author';

@Injectable({
  providedIn: 'root'
})
export class AuthorshipService {

  private readonly api_path = environment.authorship_path;
  private parser: DOMParser

  constructor(private readonly httpClient: HttpClient) {
    this.parser = new DOMParser();
  }

  //  getPatentList() {
  //   // const log = JsonToXML.parse("root", loginObj);
  //    return this.httpClient.get(this.api_path + "list", {responseType: 'text'});
  //  }
 
  //  getPatentPDF(id: string) {
  //    return this.httpClient.get(this.api_path + "transform/pdf/" + id.replace("/","-"), {responseType: 'text'});
  //  }
 
  //  getPatentHTML(id: string) {
  //    return this.httpClient.get(this.api_path + "transform/xhtml/" + id.replace("/","-"), {responseType: 'text'});
  //  }
 
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
 
  //  filteRequests(filter: FilterDto[]) {
  //    const log = JsonToXML.parse("FilterDto", filter);
  //    console.log(log)
  //    return this.httpClient.post(this.api_path + "filter", log, {responseType: 'text'});
  //  }
 
  //  getPatentRDF(id: any) {
  //    return this.httpClient.get(this.api_path + "transform/rdf/" + id.replace("/","-"), {responseType: 'text'});
  //  }
 
  //  getPatentJSON(id: any) {
  //    return this.httpClient.get(this.api_path + "transform/json/" + id.replace("/","-"), {responseType: 'text'});
  //  }
 
  //  searchPatentList(searchBy: SearchBy[]) {
  //    const log = JsonToXML.parse("FilterDto", searchBy);
  //    return this.httpClient.post(this.api_path + "search", log, {responseType: 'text'});
  //  }
 
  //  getReportForPeriod(range: RangeDto) {
  //    const log = JsonToXML.parse("range", range);
  //    return this.httpClient.post(this.api_path + "report", log, {responseType: 'text'});
  //  }
}
