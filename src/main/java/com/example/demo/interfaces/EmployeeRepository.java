package com.example.demo.interfaces;

import com.example.demo.models.Employee;
import com.example.demo.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Iterable<Employee> getAllByName(String name);

    Employee getEmployeeByName(String name);
}