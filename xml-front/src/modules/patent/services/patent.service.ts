import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import * as JsonToXML from "js2xmlparser";
import {AddSolutionDto} from "../models/add-solution-dto";
import {PatentRequestDto} from "../models/patent-request-dto";
import {TitleDto} from "../models/title-dto";
import {PreviousPatentDto} from "../models/previous-patent-dto";

@Injectable({
  providedIn: 'root'
})
export class PatentService {
  private readonly api_path = environment.patent_path;
  private parser: DOMParser

  constructor(private readonly httpClient: HttpClient) {
    this.parser = new DOMParser();
  }

  getPatentList() {
   // const log = JsonToXML.parse("root", loginObj);
    return this.httpClient.get(this.api_path + "list", {responseType: 'text'});
  }

  getPatentPDF(id: string) {
    return this.httpClient.get(this.api_path + "transform/pdf/" + id, {responseType: 'text'});
  }

  getPatentHTML(id: string) {
    return this.httpClient.get(this.api_path + "transform/xhtml/" + id, {responseType: 'text'});
  }

  addSolution(solution: AddSolutionDto){
    const log = JsonToXML.parse("root", solution);
    return this.httpClient.post(this.api_path + "solution/save", log, {responseType: 'text'});

  }

  getSolution(requestId: string) {//TODO: updatuj da koristis true key
    requestId = "P-9856-23"
    return this.httpClient.get(this.api_path + "solution/get/" + requestId, {responseType: 'text'});

  }

  submitRequest(result: PatentRequestDto, titles: TitleDto[], priortyRights: PreviousPatentDto[]) {
    let titleXml = JsonToXML.parse("titles", titles);
    titleXml = titleXml.replace("<?xml version='1.0'?>", "")
    let priorityXml = JsonToXML.parse("priorityPatent", priortyRights);
    priorityXml = priorityXml.replace("<?xml version='1.0'?>", "")
    //result.titles = titleXml
    let log = JsonToXML.parse("root", result);
    console.log("----------")
    console.log(log)
    log = log.replace("<titles/>",titleXml)
    log = log.replace("<priorityPatent/>",priorityXml)
    console.log(log)
    return this.httpClient.post(this.api_path + "patent", log, {responseType: 'text'});
  }

}
