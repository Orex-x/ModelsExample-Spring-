package com.example.demo.services;


import com.example.demo.interfaces.RoleRepository;
import com.example.demo.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Iterable<Role> getAll(){
        return roleRepository.findAll();
    }

    public Optional<Role> get(Long id){
        return roleRepository.findById(id);
    }

    public Iterable<Role> getAllByName(String name){
        return roleRepository.getAllByName(name);
    }

    public Iterable<Role> getAllByNameExists(String name){
        Iterable<Role> all  = getAll();
        List<Role> buffer = new ArrayList<>();
        for (var item : all) {
            try{
                if(item.getName().toLowerCase(Locale.ROOT)
                        .contains(name.toLowerCase(Locale.ROOT)))
                    buffer.add(item);
            }catch (Exception e){continue;}
        }

        return buffer;
    }

    public void add(Role role){
        roleRepository.save(role);
    }

    public void add(Long id, Role role){
        Role u = get(id).get();
        u.setName(role.getName());
        roleRepository.save(u);
    }

    public void delete(Long id){
        roleRepository.deleteById(id);
    }
}
