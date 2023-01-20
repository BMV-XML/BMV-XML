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
  private readonly api_path = environment.patent_path;
  private parser: DOMParser

  constructor(private readonly httpClient: HttpClient) {
    this.parser = new DOMParser();
  }

  login(loginObj: LoginDto) {
    const log = JsonToXML.parse("root", loginObj);
    return this.httpClient.post(this.api_path + "login", log, {responseType: 'text'});
  }

  register(registerDto: RegisterDto) {
    const log = JsonToXML.parse("root", registerDto);
    return this.httpClient.post(this.api_path + "register", log, {responseType: 'text'});

  }

  saveCredentialsAndType(loginObj: LoginDto, resultElementElement: any) {
    if (typeof loginObj.username === "string")
      localStorage.setItem('username', loginObj.username)
    else
      localStorage.removeItem('username')
    if (typeof loginObj.password === "string")
      localStorage.setItem('password', loginObj.password)
    else
      localStorage.removeItem('password')
    if (typeof resultElementElement === "string")
      localStorage.setItem('type', resultElementElement)
    else
      localStorage.removeItem('type')
  }

}
