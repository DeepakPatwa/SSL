import { SearchService } from './../../service/search.service';
import { Component, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  message: any;
  policies: any;
  policyName: string;
  diseasesList: any;
  premium: any;
  agegroup: any;
  flag = 0;
  result;
  public policy = {
    policyName : this.policyName,
    diseasesList: this.diseasesList,
    premium: this.premium,
    agegroup: this.agegroup
  };
  constructor(private route: ActivatedRoute, private searchService: SearchService) { }
  ngOnInit() {
    console.log("in search");
    this.message = this.route.snapshot.paramMap.get('value');
    console.log( "abd" + this.message);
    this.policies = this.searchService.getSearch(this.message).subscribe(data=>this.policies = data);
    console.log("fcgvhbj" + this.policies.policyId);
    
  }
  ngOnChanges(changes: SimpleChanges): void {
    //Called before any other lifecycle hook. Use it to inject dependencies, but avoid any serious work here.
    //Add '${implements OnChanges}' to the class.
    window.location.reload();
  }

}
