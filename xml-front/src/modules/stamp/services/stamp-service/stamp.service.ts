import { Injectable } from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StampRequestDto} from "../../models/stamp-request-dto";
import * as JsonToXML from "js2xmlparser";
import {ApplicantsDto} from "../../models/applicants-dto";
import {ColorsDto} from "../../models/colors-dto";
import {GoodsAndClassesDto} from "../../models/goods-and-classes-dto";
import {AddSolutionDto} from "../../../shared/models/add-solution-dto";
import {SearchBy} from "../../../patent/models/search-by";

@Injectable({
  providedIn: 'root'
})
export class StampService {
  private readonly api_stamp = environment.stamp_path;
  private parser: DOMParser

  constructor(private readonly httpClient: HttpClient) {
    this.parser = new DOMParser();
  }

  addPhoto(data: FormData){
    let headers = new HttpHeaders({
      'Content-Type': 'multipart/form-data;'
    });
    return this.httpClient.post(this.api_stamp + 'photo', data,
        {
            responseType: 'text',
            headers: {
                'Accept': 'application/xml',
                'myattr' : 'my'
            },
        })
  }

  addFile(data:FormData){
      return this.httpClient.post(this.api_stamp + 'file', data,
          {
              responseType: 'text',
              headers: {
                  'Accept': 'application/xml',
                  'myattr' : 'my'
              },
          })
  }

  submitRequest(request : StampRequestDto, applicants:ApplicantsDto, colors :ColorsDto, goods : GoodsAndClassesDto){
      let applicantsXml = JsonToXML.parse("applicants", applicants);
      applicantsXml = applicantsXml.replace("<?xml version='1.0'?>", "")

      let colorsXml = JsonToXML.parse("colors", colors);
      colorsXml = colorsXml.replace("<?xml version='1.0'?>", "");

      let goodsXml = JsonToXML.parse("goodsAndServicesClass", goods);
      goodsXml = goodsXml.replace("<?xml version='1.0'?>", "");

      let log = JsonToXML.parse("root", request);


      log = log.replace("<applicants/>",applicantsXml)
      log = log.replace("<colors/>", colorsXml)
      log = log.replace("<goodsAndServicesClass/>", goodsXml);


      console.log(applicantsXml)
      console.log(colorsXml)
      console.log(goodsXml)
      console.log("request")
      console.log(log)
      return this.httpClient.post(this.api_stamp + "stamp", log, {responseType: 'text'});
  }

    getStampList() {
        return this.httpClient.get(this.api_stamp + "list", {responseType: 'text'});
    }

    getPatentHTML(id: string) {
        return this.httpClient.get(this.api_stamp + "transform/xhtml/" + id.replace("/","-"), {responseType: 'text'});
    }

    getStampPDF(id: string) {
        return this.httpClient.get(this.api_stamp + "transform/pdf/" + id.replace("/","-"), {responseType: 'text'});
    }

    getStampRDF(id: string){
        console.log("ID za RDF ")
        console.log(id);
        return this.httpClient.get(this.api_stamp + "transform/rdf/" + id.replace("/","-"), {responseType: 'text'});
    }

    getStampJSON(id: string){
        console.log("ID za JSON ")
        console.log(id);
        return this.httpClient.get(this.api_stamp + "transform/json/" + id.replace("/","-"), {responseType: 'text'});
    }

    addSolution(solution: AddSolutionDto) {
        const log = JsonToXML.parse("root", solution);
        return this.httpClient.post(this.api_stamp + "solution/save", log, {responseType: 'text'});

    }

    getSolution(requestId: string) {
        return this.httpClient.get(this.api_stamp + "solution/get/" + requestId.replace("/","-"), {responseType: 'text'});
    }

    getStamp(requestId: string) {
        return this.httpClient.get(this.api_stamp + "get/"+ requestId.replace("/", "-"), {responseType: 'text'});
    }

    searchStampList(searchBy: SearchBy[]) {
        const log = JsonToXML.parse("FilterDto", searchBy);
        return this.httpClient.post(this.api_stamp + "search", log, {responseType: 'text'});

    }
}
