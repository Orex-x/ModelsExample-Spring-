package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import com.example.demo.viewModels.SearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.StreamSupport;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/addTask")
    public String addTask(Model model){
        model.addAttribute("task", new Task());
        model.addAttribute("isAddAction", true);
        return "task";
    }

    @PostMapping("/addTask")
    public String addTask(@ModelAttribute() @Valid Task task, BindingResult result, Model model){

        if (result.hasErrors()) {
            model.addAttribute("task", task);
            model.addAttribute("isAddAction", true);
            return "task";
        }

        taskService.add(task);
        model.addAttribute("task", new Task());
        model.addAttribute("isAddAction", true);
        return "task";
    }

    @GetMapping("/task/update/{id}")
    public String update(@PathVariable("id") long id, Model model) {
        Task task = taskService.get(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));

        model.addAttribute("task", task);
        model.addAttribute("isAddAction", false);
        return "task";
    }

    @PostMapping("/task/update/{id}")
    public String update(@PathVariable("id") long id, @Valid Task task,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("task", task);
            model.addAttribute("isAddAction", false);
            return "task";
        }

        taskService.add(id, task);

        List<Task> list = StreamSupport.stream(taskService.getAll().spliterator(), false).toList();
        model.addAttribute("tasks", list);
        model.addAttribute("searchModel", new SearchModel());
        return "tasks";
    }

    @GetMapping("/task/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        taskService.delete(id);
        List<Task> list = StreamSupport.stream(taskService.getAll().spliterator(), false).toList();
        model.addAttribute("tasks", list);
        model.addAttribute("searchModel", new SearchModel());
        return "tasks";
    }

    @GetMapping("/tasks")
    public String tasks(Model model){
        List<Task> list = StreamSupport.stream(taskService.getAll().spliterator(), false).toList();
        model.addAttribute("tasks", list);
        model.addAttribute("searchModel", new SearchModel());
        return "tasks";
    }

    @PostMapping("/tasks")
    public String search(@ModelAttribute SearchModel searchModel, Model model){
        List<Task> list;
        if(searchModel.isHigh()){
            list = StreamSupport.stream(taskService.getAllByTitle(
                    searchModel.getTitle()).spliterator(), false).toList();
        }else{
            list = StreamSupport.stream(taskService.getAllByTitleExists(
                    searchModel.getTitle()).spliterator(), false).toList();
        }


        model.addAttribute("tasks", list);
        model.addAttribute("searchModel", new SearchModel());
        return "tasks";
    }
}
