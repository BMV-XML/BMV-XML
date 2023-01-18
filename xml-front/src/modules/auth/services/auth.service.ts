import { Injectable } from '@angular/core';
import {LoginDto} from "../models/login-dto";
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import * as JsonToXML from "js2xmlparser";
import {RegisterDto} from "../models/register-dto";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private headers = new HttpHeaders();
  private readonly api_path = environment.patent_path;
  private parser: DOMParser

  constructor(private readonly httpClient: HttpClient) {
    this.headers = this.headers.append('Content-Type', 'application/xml');
    this.headers.set('Accept' , 'application/xml');
    this.parser = new DOMParser();
  }

  login(loginObj: LoginDto) {
    const log = JsonToXML.parse("root", loginObj);
    return this.httpClient.post(this.api_path + "login", log, {headers: this.headers, responseType: 'text'});
  }

  register(registerDto: RegisterDto) {
    const log = JsonToXML.parse("root", registerDto);
    return this.httpClient.post(this.api_path + "register", log, {headers: this.headers, responseType: 'text'});

  }
}
