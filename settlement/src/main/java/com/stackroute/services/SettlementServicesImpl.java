package com.stackroute.services;

import com.stackroute.repository.SettlementRepository;
import com.stackroute.domain.PendingTasks;
import com.stackroute.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettlementServicesImpl implements SettlementServices {
    private SettlementRepository settlementRepository;
    @Autowired
    public SettlementServicesImpl(SettlementRepository settlementRepository) {this.settlementRepository = settlementRepository;}
    @Override
    public List<PendingTasks> getAllPendingTasksForInsurer(String insurerName) {
        List<PendingTasks> allPendingTasks = settlementRepository.findAllByInsurerNameOrderByInsuredNameAsc(insurerName);
        return allPendingTasks;
    }
    @Override
    public PendingTasks appendTask(int pendingTasksId, Task task) {
        PendingTasks pendingTasks = settlementRepository.findByPendingTasksId(pendingTasksId);
        List<Task> currentListOfTasks = pendingTasks.getTaskList();
        if(currentListOfTasks == null) {
            List<Task> newTaskList = new ArrayList<Task>();
            newTaskList.add(task);
            pendingTasks.setTaskList(newTaskList);
            settlementRepository.deleteById(pendingTasksId);
            settlementRepository.save(pendingTasks);
            return pendingTasks;
        }
        currentListOfTasks.add(task);
        pendingTasks.setTaskList(currentListOfTasks);
        settlementRepository.deleteById(pendingTasksId);
        settlementRepository.save(pendingTasks);
        return pendingTasks;
    }


    @Override
    public PendingTasks modifyTask(int pendingTasksId, Task oldTask, Task newTask) {
        PendingTasks pendingTasks = settlementRepository.findByPendingTasksId(pendingTasksId);
        settlementRepository.deleteById(pendingTasksId);
        List<Task> currentListOfTasks = pendingTasks.getTaskList();
        for(Task task : currentListOfTasks) {
            if(task.equals(oldTask)) {
                task = newTask;
                break;
            }
        }
        pendingTasks.setTaskList(currentListOfTasks);
        settlementRepository.save(pendingTasks);
        return pendingTasks;
    }


    @Override
    public PendingTasks getAllPendingTasksForInsured(String insurerName, String insuredName) {
        PendingTasks allPendingTasksForInsured = settlementRepository.findAllByInsurerNameAndInsuredName(insurerName, insuredName);
        return allPendingTasksForInsured;
    }

    @Override
    public PendingTasks putPendingTasks(PendingTasks pendingTasks) {
        settlementRepository.save(pendingTasks);
        return pendingTasks;
    }

    @Override
    public PendingTasks deleteTask(int pendingTasksId, String taskName) {
        PendingTasks pendingTasks = settlementRepository.findByPendingTasksId(pendingTasksId);
        settlementRepository.deleteById(pendingTasksId);
        List<Task> newTaskList = new ArrayList<Task>();
        List<Task> currentTaskList = pendingTasks.getTaskList();
        for(Task task: currentTaskList) {
            if(task.getTaskName().equals(taskName)) {
                continue;
            }
            newTaskList.add(task);
        }
        pendingTasks.setTaskList(newTaskList);
        settlementRepository.save(pendingTasks);
        return pendingTasks;
    }

    @Override
    public PendingTasks changePendingTaskStatus(int pendingTasksId, String taskName, boolean status) {
        PendingTasks pendingTasks = settlementRepository.findByPendingTasksId(pendingTasksId);
        List<Task> taskList = pendingTasks.getTaskList();
        if(taskList == null)
            return null;
        for(Task task: taskList) {
            if(task.getTaskName().equals(taskName)) {
                task.setTaskStatus(status);
                break;
            }
        }
        settlementRepository.deleteById(pendingTasksId);
        settlementRepository.save(pendingTasks);
        return pendingTasks;
    }

    @Override
    public List<PendingTasks> getPendingTasksByPortingRequestId(int portingRequestId) {
        List<PendingTasks> pendingTasks = new ArrayList<PendingTasks>();
        pendingTasks.add(settlementRepository.findByPortingRequestId(portingRequestId));
        return pendingTasks;
    }
}
