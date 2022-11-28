package com.example.demo.controllers;


import com.example.demo.models.Account;
import com.example.demo.models.Employee;
import com.example.demo.models.Role;
import com.example.demo.models.Task;
import com.example.demo.services.AccountService;
import com.example.demo.services.EmployeeService;
import com.example.demo.services.RoleService;
import com.example.demo.services.TaskService;
import com.example.demo.viewModels.EmployeeTaskViewModel;
import com.example.demo.viewModels.SearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.StreamSupport;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TaskService taskService;

    @GetMapping("/addEmployee")
    public String addEmployee(Model model){
        model.addAttribute("employee", new Employee());
        List<Role> roles = StreamSupport.stream(roleService.getAll().spliterator(), false).toList();
        List<Account> accounts = StreamSupport.stream(accountService.getAll().spliterator(), false).toList();
        model.addAttribute("roles", roles);
        model.addAttribute("accounts", accounts);
        model.addAttribute("isAddAction", true);
        return "employee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute @Valid Employee employee, BindingResult result, Model model){


        if (result.hasErrors()) {
            model.addAttribute("employee", employee);
            List<Role> roles = StreamSupport.stream(roleService.getAll().spliterator(), false).toList();
            List<Account> accounts = StreamSupport.stream(accountService.getAll().spliterator(), false).toList();
            model.addAttribute("roles", roles);
            model.addAttribute("accounts", accounts);
            model.addAttribute("isAddAction", true);
            return "employee";
        }
        employeeService.add(employee);
        List<Employee> list = StreamSupport.stream(employeeService.getAll().spliterator(), false).toList();
        model.addAttribute("employees", list);
        model.addAttribute("searchModel", new SearchModel());
        return "employees";
    }

    @GetMapping("/employee/update/{id}")
    public String update(@PathVariable("id") long id, Model model) {
        Employee employee = employeeService.get(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));

        List<Role> roles = StreamSupport.stream(roleService.getAll().spliterator(), false).toList();
        List<Account> accounts = StreamSupport.stream(accountService.getAll().spliterator(), false).toList();
        model.addAttribute("roles", roles);
        model.addAttribute("accounts", accounts);
        model.addAttribute("employee", employee);
        model.addAttribute("isAddAction", false);
        return "employee";
    }

    @PostMapping("/employee/update/{id}")
    public String update(@PathVariable("id") long id, @Valid Employee employee,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            employee.setId(id);
            List<Role> roles = StreamSupport.stream(roleService.getAll().spliterator(), false).toList();
            List<Account> accounts = StreamSupport.stream(accountService.getAll().spliterator(), false).toList();
            model.addAttribute("roles", roles);
            model.addAttribute("accounts", accounts);
            model.addAttribute("employee", employee);
            model.addAttribute("isAddAction", false);
            return "employee";
        }

        employeeService.add(id, employee);

        List<Employee> list = StreamSupport.stream(employeeService.getAll().spliterator(), false).toList();
        model.addAttribute("employees", list);
        model.addAttribute("searchModel", new SearchModel());
        return "employees";
    }

    @GetMapping("/employee/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        employeeService.delete(id);
        List<Employee> list = StreamSupport.stream(employeeService.getAll().spliterator(), false).toList();
        model.addAttribute("employees", list);
        model.addAttribute("searchModel", new SearchModel());
        return "employees";
    }

    @GetMapping("/employees")
    public String employees(Model model){
        List<Employee> list = StreamSupport.stream(employeeService.getAll().spliterator(), false).toList();
        model.addAttribute("employees", list);
        model.addAttribute("searchModel", new SearchModel());
        return "employees";
    }

    @PostMapping("/employees")
    public String search(@ModelAttribute SearchModel searchModel, Model model){
        List<Employee> list;

        if(searchModel.isHigh()){
            list = StreamSupport.stream(employeeService.getAllByName(
                    searchModel.getTitle()).spliterator(), false).toList();
        }else{
            list = StreamSupport.stream(employeeService.getAllByNameExists(
                    searchModel.getTitle()).spliterator(), false).toList();
        }

        model.addAttribute("employees", list);
        model.addAttribute("searchModel", new SearchModel());
        return "employees";
    }

    @GetMapping("/employee/tasks/{id}")
    public String tasks(@PathVariable("id") long id, Model model) {
        Employee employee = employeeService.get(id).get();
        List<Task> tasks = StreamSupport.stream(taskService.getAll().spliterator(), false).toList();
        model.addAttribute("tasks", employee.getTasks());
        model.addAttribute("viewModel", new EmployeeTaskViewModel(tasks));
        model.addAttribute("idEmployee", id);
        return "employee_tasks";
    }

    @PostMapping("/employee/task/add/{id}")
    public String addTask(@PathVariable("id") long id, @ModelAttribute EmployeeTaskViewModel viewModel, Model model){
        employeeService.addTask(id, viewModel.getSelectedTask());
        Employee employee = employeeService.get(id).get();
        List<Task> tasks = StreamSupport.stream(taskService.getAll().spliterator(), false).toList();
        model.addAttribute("tasks", employee.getTasks());
        model.addAttribute("viewModel", new EmployeeTaskViewModel(tasks));
        model.addAttribute("idEmployee", id);
        return "employee_tasks";
    }
}