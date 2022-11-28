package com.example.demo.viewModels;

import com.example.demo.models.Employee;

public class ViewModelEmployee {
    private Employee employee;
    private Long roleId;


    public ViewModelEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long countryId) {
        this.roleId = countryId;
    }
}
