import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { RequestService } from './../../service/request.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-porting-request',
  templateUrl: './porting-request.component.html',
  styleUrls: ['./porting-request.component.css']
})
export class PortingRequestComponent implements OnInit {
  portingForm: any;
  loggedIn: any;
  constructor(private formBuilder: FormBuilder, private requestService: RequestService,private router: Router) { }
 
  ngOnInit() {
    let name = window.localStorage.getItem("insuredname"); 
    this.portingForm = this.formBuilder.group({
      insuredName:['',Validators.required],
      dateOfBirth:['',Validators.required],
      insuredAddress:['',Validators.required],
      insurerProduct:['',Validators.required],
      sumInsured:['',Validators.required],
      cumulativeBonus:['',Validators.required],
      addOns:['',Validators.required],
      policyNumber:['',Validators.required],
      newInsurerProduct:['',Validators.required],
      newSumInsured:['',Validators.required],
      newCumulativeBonus:['',Validators.required],
      reasonForPortability:['',Validators.required],
      familyMembers:[''],
      exclusionPeriod:['',Validators.required]});
  }
  submitForm() {
    console.log("hi");
    console.log(this.portingForm);
    const res = Object.assign(this.portingForm.value);
    console.log("hiii" + res);
    this.requestService.postRequest(res).subscribe(
      data => {
        console.log('hel');
        console.log(data);
      },
      error => {
        console.log(error);
      }
    );
    this.router.navigate(['/dashboard',name]);
  }
  logged(){
    this.loggedIn=true;
  }

}
