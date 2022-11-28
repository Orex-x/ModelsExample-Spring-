package com.example.demo.services;

import com.example.demo.interfaces.EmployeeRepository;
import com.example.demo.models.Employee;
import com.example.demo.models.Role;
import com.example.demo.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Iterable<Employee> getAll(){
        return employeeRepository.findAll();
    }

    public Optional<Employee> get(Long id){
        return employeeRepository.findById(id);
    }

    public Iterable<Employee> getAllByName(String name){
        return employeeRepository.getAllByName(name);
    }

    public Iterable<Employee> getAllByNameExists(String name){
        Iterable<Employee> all  = getAll();
        List<Employee> buffer = new ArrayList<>();
        for (var item : all) {
            try{
                if(item.getName().toLowerCase(Locale.ROOT)
                        .contains(name.toLowerCase(Locale.ROOT)))
                    buffer.add(item);
            }catch (Exception e){continue;}
        }

        return buffer;
    }

    public void add(Employee employee){
        employeeRepository.save(employee);
    }

    public void add(Long id, Employee employee){
        Employee u = get(id).get();
        u.setTasks(employee.getTasks());
        u.setAccount(employee.getAccount());
        u.setAmount(employee.getAmount());
        u.setBalance(employee.getBalance());
        u.setName(employee.getName());
        u.setDateOfBirth(employee.getDateOfBirth());
        u.setRole(employee.getRole());
        employeeRepository.save(u);
    }

    public void addTask(Long id, Task task){
        Employee u = get(id).get();
        u.addTask(task);
        employeeRepository.save(u);
    }

    public void delete(Long id){
        employeeRepository.deleteById(id);
    }
}
