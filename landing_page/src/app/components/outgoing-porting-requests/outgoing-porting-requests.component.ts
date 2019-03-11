import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { InsurerOutgoingportingrequestService } from './../../service/insurer-outgoingportingrequest.service';
import { InsurerAcceptoutgoingportingrequestService } from './../../service/insurer-acceptoutgoingportingrequest.service';
import {MatDialog, MatDialogConfig} from '@angular/material';
import { AllPortingRequestsComponent } from '../all-porting-requests/all-porting-requests.component';
import {ReviewComponent} from '../review/review.component';
@Component({
  selector: 'app-outgoing-porting-requests',
  templateUrl: './outgoing-porting-requests.component.html',
  styleUrls: ['./outgoing-porting-requests.component.css']
})
export class OutgoingPortingRequestsComponent implements OnInit {
  insurerLicense: any;
  requests: any;
  currentCompanyName: string;
  raiseGrievanceButtonIsClicked: boolean;
  idForGrievances: number;
  grievancesComponent = AllPortingRequestsComponent;
  constructor(private route: ActivatedRoute, private incoming: InsurerOutgoingportingrequestService, private portrequest: InsurerAcceptoutgoingportingrequestService, private dialog: MatDialog) { }

  ngOnInit() {
    this.raiseGrievanceButtonIsClicked = false;
    console.log("inside outgoing");
    this.insurerLicense = this.route.snapshot.paramMap.get('insurerLicense');
    console.log(this.insurerLicense);
    this.requests = this.incoming.getSearch(this.insurerLicense).subscribe(data => {
      this.requests = data;
      console.log(data);
      console.log("after service method");
    });

  }
  port(request) {
    this.portrequest.getSearch(request).subscribe(data => console.log(data));
    this.reloadData();
  }
  reloadData() {
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
  raiseGrievanceButtonClicked(portrequestId: number): boolean{
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      portingRequestId: portrequestId
    };

    this.dialog.open(AllPortingRequestsComponent, dialogConfig);
    this.raiseGrievanceButtonIsClicked = false;
    this.idForGrievances = portrequestId;
    return true;
  }

}
