import { Component, OnInit } from "@angular/core";
import { FetchPendingTasksService } from "../fetch-pending-tasks.service";
import { PendingTasks } from "../pending-tasks";
import { Task } from "../task";

@Component({
  selector: "app-all-porting-requests",
  templateUrl: "./all-porting-requests.component.html",
  styleUrls: ["./all-porting-requests.component.css"]
})
export class AllPortingRequestsComponent implements OnInit {
  currentCompanyName: string;
  pendingTasks: PendingTasks[];

  newPendingTask: Task;
  newPendingTaskName: string;
  newPendingTaskDescription: string;
  newPendingTaskDueDate: string;

  currentInsuredName: string;
  fetchAllPortingRequestsIsClicked: Boolean;
  addANewPendingTaskIsClicked: Boolean;
  viewPendingTasksOfInsuredIsClicked: Boolean;
  fetchPendingTasksService: FetchPendingTasksService;
  constructor(fetchPendingTasksService: FetchPendingTasksService) {
    this.fetchPendingTasksService = fetchPendingTasksService;
  }

  ngOnInit() {
    this.currentCompanyName = null;
    this.fetchAllPortingRequestsIsClicked = false;
    this.viewPendingTasksOfInsuredIsClicked = false;
    this.addANewPendingTaskIsClicked = false;
  }
  reinitializeAllClickedVariables(): void {
    this.addANewPendingTaskIsClicked = false;
  }
  initNewPendingTask(): void {
    this.newPendingTask = {"taskName": "Temp", "taskDescription": "Temp", "dueDate":"53", "taskStatus": false};
  }
  fetchAllPortingRequests(currentCompanyName: string): void {
    this.reinitializeAllClickedVariables();
    this.fetchAllPortingRequestsIsClicked = true;
    this.fetchPendingTasksService
      .fetchAllPortingRequests(currentCompanyName)
      .subscribe(pendingTasks => (this.pendingTasks = pendingTasks));
  }
  viewPendingTasksOfInsured(insuredName: string): void {
    this.reinitializeAllClickedVariables();
    this.currentInsuredName = insuredName;
    this.viewPendingTasksOfInsuredIsClicked = true;
  }
  addANewPendingTask(): void {
    this.initNewPendingTask();
    this.addANewPendingTaskIsClicked = true;
  }
  saveNewPendingTask(pendingTasks: PendingTasks): void {
    this.newPendingTask.taskStatus = false;
    this.newPendingTask.taskName = this.newPendingTaskName;
    this.newPendingTask.taskDescription= this.newPendingTaskDescription;
    this.newPendingTask.dueDate= this.newPendingTaskDueDate;
    this.ngOnInit();
    this.fetchPendingTasksService
      .addANewPendingTask(pendingTasks.pendingTasksId, this.newPendingTask)
      .subscribe();
  }
  modifyStatusOfTask(taskStatus: boolean, taskName: string, pendingTasksId: number): void{
    this.fetchPendingTasksService.modifyStatusOfTask(!taskStatus, pendingTasksId, taskName).subscribe();
    for(let pendingtask of this.pendingTasks) {
      console.log(pendingtask.pendingTasksId);
      for(let task of pendingtask.taskList) {
        console.log(task.taskStatus);
      }
    }
    
  }
}
