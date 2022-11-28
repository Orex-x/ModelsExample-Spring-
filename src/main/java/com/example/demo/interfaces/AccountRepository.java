package com.example.demo.interfaces;

import com.example.demo.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Iterable<Account> getAllByNumber(String number);
}
