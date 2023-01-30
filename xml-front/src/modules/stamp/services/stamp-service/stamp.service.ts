import { Injectable } from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StampRequestDto} from "../../models/stamp-request-dto";
import * as JsonToXML from "js2xmlparser";
import {ApplicantsDto} from "../../models/applicants-dto";
import {ColorsDto} from "../../models/colors-dto";
import {GoodsAndClassesDto} from "../../models/goods-and-classes-dto";

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
}
