import { Injectable } from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";

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
    return this.httpClient.post(this.api_stamp + '/photo', data,
        {
          responseType: 'blob',
            headers: {
                'Accept': 'application/json'
            },
        })
  }
}
