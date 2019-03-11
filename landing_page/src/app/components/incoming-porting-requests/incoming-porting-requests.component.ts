import { CalculatorComponent } from './../calculator/calculator.component';
import { InsurerAcceptincomingportingrequestService } from './../../service/insurer-acceptincomingportingrequest.service';
import { InsurerIncomingportingrequestService } from './../../service/InsurerIncomingportingrequestService';
import { InsurerOutgoingportingrequestService } from './../../service/insurer-outgoingportingrequest.service';
import { ActivatedRoute } from '@angular/router';

import { MatDialog } from '@angular/material';
import { ReviewComponent } from '../review/review.component';

import { Component, OnInit } from '@angular/core';
import { InsurerAcceptoutgoingportingrequestService } from './../../service/insurer-acceptoutgoingportingrequest.service';
import { InsurerRejectincomingportingrequestService } from './../../service/InsurerRejectincomingportingrequestService';


@Component({
  selector: 'app-incoming-porting-requests',
  templateUrl: './incoming-porting-requests.component.html',
  styleUrls: ['./incoming-porting-requests.component.css']
})
export class IncomingPortingRequestsComponent implements OnInit {
  insurerLicense: any;
  requests: any;
  // request: any;
  // public reviewComponent: ReviewComponent;
  public insuredName: any;
  constructor(private route:ActivatedRoute, private incoming: InsurerIncomingportingrequestService,
    private portrequest: InsurerAcceptincomingportingrequestService, 
    private portrequest1: InsurerRejectincomingportingrequestService, 
    public dialog: MatDialog) { }

  ngOnInit() {
    console.log("inside Incoming");
    this.insurerLicense = this.route.snapshot.paramMap.get('insurerLicense');
    console.log(this.insurerLicense);
    this.requests = this.incoming.getSearch(this.insurerLicense).subscribe(data =>
      {
        this.requests = data;
        console.log(data);
        console.log("after service method");
    });

  }
  port(request) {
    this.portrequest.getSearch(request).subscribe(data=>console.log(data));
    this.reloadData();
    
  }

  port1(request) {
    this.portrequest1.getSearch(request).subscribe(data=>console.log(data));
    this.reloadData();
  }
  reloadData(){
    window.location.reload();
  }
  openDialog(insuredname): void {
    let as = window.localStorage.setItem("insuredname",insuredname);
    const dialogRef = this.dialog.open(ReviewComponent, {
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
