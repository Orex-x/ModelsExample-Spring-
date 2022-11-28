package com.example.demo.viewModels;

import com.example.demo.models.Task;

import java.util.List;

public class EmployeeTaskViewModel {
    private List<Task> tasks;
    private Task selectedTask;


    public EmployeeTaskViewModel(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }
}
