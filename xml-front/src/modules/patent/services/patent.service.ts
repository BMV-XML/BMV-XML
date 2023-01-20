import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {LoginDto} from "../../auth/models/login-dto";
import * as JsonToXML from "js2xmlparser";

@Injectable({
  providedIn: 'root'
})
export class PatentService {
  private headers = new HttpHeaders();
  private readonly api_path = environment.patent_path;
  private parser: DOMParser

  constructor(private readonly httpClient: HttpClient) {
    this.headers = this.headers.append('Content-Type', 'application/xml');
    this.headers.set('Accept' , 'application/xml');
    this.parser = new DOMParser();
  }

  getPatentList() {
   // const log = JsonToXML.parse("root", loginObj);
    return this.httpClient.get(this.api_path + "list", {headers: this.headers, responseType: 'text'});
  }

  getPatentPDF(id: string) {
    return this.httpClient.get(this.api_path + "transform/pdf/" + id, {headers: this.headers, responseType: 'text'});
  }

  getPatentHTML(id: string) {
    return this.httpClient.get(this.api_path + "transform/xhtml/" + id, {headers: this.headers, responseType: 'text'});
  }
}
