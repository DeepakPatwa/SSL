import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { PolicyService } from './../../service/policy.service';

@Component({
  selector: 'app-buy-policy',
  templateUrl: './buy-policy.component.html',
  styleUrls: ['./buy-policy.component.css']
})
export class BuyPolicyComponent implements OnInit {

  insurerName: any;
  policyName: any;
  insuredName: any;
  updatedPolicy: any;
  constructor(public route: ActivatedRoute, private router: Router, private policyService: PolicyService ) { }
  ngOnInit() {
      this.insurerName= this.route.snapshot.paramMap.get('insurername');
      this.policyName= this.route.snapshot.paramMap.get('policyname');
      this.insuredName= this.route.snapshot.paramMap.get('insuredname');
      console.log(this.insurerName +" "+this.policyName+" "+this.insuredName);
      this.updatedPolicy = this.policyService.buyPolicy(this.insurerName,this.policyName,this.insuredName).subscribe(
        data => {
          console.log(data);
          console.log(this.updatedPolicy);
        },
        error => {
          console.log("error occured");
          console.log(error);
        }
      );
  }

}
