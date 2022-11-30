package com.example.demo.interfaces;

import com.example.demo.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Iterable<Role> getAllByName(String name);
    Role findRoleByName(String name);
}
