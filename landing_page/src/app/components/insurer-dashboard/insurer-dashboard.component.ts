import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-insurer-dashboard',
  templateUrl: './insurer-dashboard.component.html',
  styleUrls: ['./insurer-dashboard.component.css']
})
export class InsurerDashboardComponent implements OnInit {
  insurerLicense: any;
  
  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.insurerLicense = this.route.snapshot.paramMap.get('insurerLicense');
    let company = window.localStorage.setItem("insurername",this.insurerLicense);
  }

}
