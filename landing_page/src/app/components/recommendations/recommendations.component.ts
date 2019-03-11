import { UserDashboardService } from './../../service/user-dashboard.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-recommendations',
  templateUrl: './recommendations.component.html',
  styleUrls: ['./recommendations.component.css']
})
export class RecommendationsComponent implements OnInit {
  userName: any;

  public recommendations;
  constructor(public route: ActivatedRoute, private router: Router, public userService: UserDashboardService) { }
  ngOnInit() {
    this.userName = this.route.snapshot.paramMap.get('username');
    console.log('recommendations : ' + this.userName);
    this.recommendations = this.userService.getRecommendations(this.userName).subscribe(
      data => {
        this.recommendations = data;
      },
      error => {
        console.log('some error occured');
        console.log(error.errorMessage);
      }


   );
  
    }
    ngOnChanges(changes: SimpleChanges) {
      this.router.navigate(['/dashboard',this.userName]);
    }

   
}
