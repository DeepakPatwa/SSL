import { Component, OnInit } from '@angular/core';
import { ChatService } from '../../service/chat.service';

@Component({
  selector: 'app-show-chat-results',
  templateUrl: './show-chat-results.component.html',
  styleUrls: ['./show-chat-results.component.css']
})
export class ShowChatResultsComponent implements OnInit {
  policies: any;
  constructor(private chat:ChatService) { }

  ngOnInit() {
   this.chat.showChatResults().subscribe(
      data=>{
        console.log(data);
        this.policies=data;
      }
    );
  }

}
