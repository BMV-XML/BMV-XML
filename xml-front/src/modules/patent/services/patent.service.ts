import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import * as JsonToXML from "js2xmlparser";
import {AddSolutionDto} from "../models/add-solution-dto";
import {PatentRequestDto} from "../models/patent-request-dto";
import {TitleDto} from "../models/title-dto";
import {PreviousPatentDto} from "../models/previous-patent-dto";
import {FilterDto} from "../models/filter-dto";
import {SearchBy} from "../models/search-by";
import {RangeDto} from "../models/range-dto";

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
    return this.httpClient.get(this.api_path + "transform/pdf/" + id.replace("/","-"), {responseType: 'text'});
  }

  getPatentHTML(id: string) {
    return this.httpClient.get(this.api_path + "transform/xhtml/" + id.replace("/","-"), {responseType: 'text'});
  }

  addSolution(solution: AddSolutionDto){
    const log = JsonToXML.parse("root", solution);
    return this.httpClient.post(this.api_path + "solution/save", log, {responseType: 'text'});

  }

  getSolution(requestId: string) {//TODO: updatuj da koristis true key
    //requestId = "P-9856-23"
    return this.httpClient.get(this.api_path + "solution/get/" + requestId.replace("/","-"), {responseType: 'text'});

  }

  submitRequest(result: PatentRequestDto, titles: TitleDto[], priortyRights: PreviousPatentDto[], titlesFromXonomy: string) {
    let titleXml = JsonToXML.parse("titles", titles);
    titleXml = titleXml.replace("<?xml version='1.0'?>", "")
    //titleXml = titleXml.replace("&lt;", "<")
    //titleXml = titleXml.replace("&gt;", ">")
    console.log("*************************************************************")
    console.log(titles)
    //console.log(titleXml)
    let priorityXml = JsonToXML.parse("priorityPatent", priortyRights);
    priorityXml = priorityXml.replace("<?xml version='1.0'?>", "")
    //result.titles = titleXml
    let log = JsonToXML.parse("root", result);
    console.log("----------")
    console.log(log)
    log = log.replace("<titles/>",titleXml)
    //log = log.replace("<titles/>",titlesFromXonomy)
    log = log.replace("<priorityPatent/>",priorityXml)
    console.log(log)
    return this.httpClient.post(this.api_path + "patent", log, {responseType: 'text'});
  }

  getPatent(requestId: string) {
    return this.httpClient.get(this.api_path + "get/"+ requestId.replace("/", "-"), {responseType: 'text'});

  }

  filteRequests(filter: FilterDto[]) {
    const log = JsonToXML.parse("FilterDto", filter);
    console.log(log)
    return this.httpClient.post(this.api_path + "filter", log, {responseType: 'text'});
  }

  getPatentRDF(id: any) {
    return this.httpClient.get(this.api_path + "transform/rdf/" + id.replace("/","-"), {responseType: 'text'});
  }

  getPatentJSON(id: any) {
    return this.httpClient.get(this.api_path + "transform/json/" + id.replace("/","-"), {responseType: 'text'});
  }

  searchPatentList(searchBy: SearchBy[]) {
    const log = JsonToXML.parse("FilterDto", searchBy);
    return this.httpClient.post(this.api_path + "search", log, {responseType: 'text'});
  }

  getReportForPeriod(range: RangeDto) {
    const log = JsonToXML.parse("range", range);
    return this.httpClient.post(this.api_path + "report", log, {responseType: 'text'});
  }


  convertDateToStringInReport(param: string | undefined) {
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
    return day + "." + month +"." + dateParts[2] + "."
  }

  convertDateToStringInPatent(param: string | undefined) {
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

  searchPatentListUser($event: SearchBy[]) {
    const log = JsonToXML.parse("FilterDto", $event);
    return this.httpClient.post(this.api_path + "patent/search/user", log, {responseType: 'text'});
  }

  filteRequestsUser($event: FilterDto[]) {

    const log = JsonToXML.parse("FilterDto", $event);
    console.log(log)
    return this.httpClient.post(this.api_path + "patent/filter/user", log, {responseType: 'text'});
  }

  getPatentListUser() {
    return this.httpClient.get(this.api_path + "patent/list/user", {responseType: 'text'});
  }
}
