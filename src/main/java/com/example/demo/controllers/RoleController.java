package com.example.demo.controllers;


import com.example.demo.models.Role;
import com.example.demo.services.RoleService;
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
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/addRole")
    public String addRole(Model model){
        model.addAttribute("role", new Role());
        model.addAttribute("isAddAction", true);
        return "role";
    }

    @PostMapping("/addRole")
    public String addRole(@ModelAttribute() @Valid Role role, BindingResult result, Model model){

        if (result.hasErrors()) {
            model.addAttribute("role", role);
            model.addAttribute("isAddAction", true);
            return "role";
        }

        roleService.add(role);
        List<Role> list = StreamSupport.stream(roleService.getAll().spliterator(), false).toList();
        model.addAttribute("roles", list);
        model.addAttribute("searchModel", new SearchModel());
        return "roles";
    }

    @GetMapping("/role/update/{id}")
    public String update(@PathVariable("id") long id, Model model) {
        Role role = roleService.get(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));

        model.addAttribute("role", role);
        model.addAttribute("isAddAction", false);
        return "role";
    }

    @PostMapping("/role/update/{id}")
    public String update(@PathVariable("id") long id, @Valid Role role,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("role", role);
            model.addAttribute("isAddAction", false);
            return "role";
        }

        roleService.add(id, role);

        List<Role> list = StreamSupport.stream(roleService.getAll().spliterator(), false).toList();
        model.addAttribute("roles", list);
        model.addAttribute("searchModel", new SearchModel());
        return "roles";
    }

    @GetMapping("/role/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        roleService.delete(id);
        List<Role> list = StreamSupport.stream(roleService.getAll().spliterator(), false).toList();
        model.addAttribute("roles", list);
        model.addAttribute("searchModel", new SearchModel());
        return "roles";
    }

    @GetMapping("/roles")
    public String roles(Model model){
        List<Role> list = StreamSupport.stream(roleService.getAll().spliterator(), false).toList();
        model.addAttribute("roles", list);
        model.addAttribute("searchModel", new SearchModel());
        return "roles";
    }

    @PostMapping("/roles")
    public String search(@ModelAttribute SearchModel searchModel, Model model){
        List<Role> list;
        if(searchModel.isHigh()){
            list = StreamSupport.stream(roleService.getAllByName(
                    searchModel.getTitle()).spliterator(), false).toList();
        }else{
            list = StreamSupport.stream(roleService.getAllByNameExists(
                    searchModel.getTitle()).spliterator(), false).toList();
        }


        model.addAttribute("roles", list);
        model.addAttribute("searchModel", new SearchModel());
        return "roles";
    }
}
