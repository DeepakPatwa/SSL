import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { UserDashboardService } from './../../service/user-dashboard.service';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {
  firstForm: FormGroup;
  secondForm: FormGroup;
  thirdForm: FormGroup;
  username;
  constructor(private _route: ActivatedRoute, private userService: UserDashboardService, private router: Router,private formBuilder: FormBuilder) { }
 
 
  ngOnInit() {
    console.log("abcd");
    this.username = this._route.snapshot.paramMap.get('username');
    this.firstForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
 
    });
    this.secondForm = this.formBuilder.group({
      gender: ['', Validators.required],
      fullName: ['', Validators.required],
      insuredId: ['',Validators.required]
      });
      
   }
 
   submitForm() {
    console.log("hi");
 
     const signUpForm = Object.assign(this.firstForm.value, this.secondForm.value,this.thirdForm.value);
      console.log('registerForm.value : ', signUpForm);
 
    this.userService.updateProfile(this.username, signUpForm).subscribe(
      data => {
        console.log(data);
      },
      error => {
        console.log(error);
      }
    );
  }
}
