
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
// import 'rxjs/Rx';
@Injectable({
  providedIn: 'root'
})
export class PolicyService {
  public httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      // 'Authorization': 'my-auth-token'
    })
  };
  public policyDetailsUrl = 'http://13.126.73.190:8092/policy/api/v1/policy/';
  policyDetails:any;
  constructor(private http: HttpClient) {
    console.log('http service got called');
  }
  public getPolicyDetails(insurerName,policyName): any {
    console.log("inside service");
   
    return this.http.get(this.policyDetailsUrl + insurerName +'/'+ policyName, this.httpOptions);
}

  public buyPolicy(insurername,policyname,insuredname): any {
    console.log('inside buy policy');
   
    let as = this.http.put(this.policyDetailsUrl + insurername +'/' + policyname+'/' + insuredname,0);
    return as;
  } 
}
