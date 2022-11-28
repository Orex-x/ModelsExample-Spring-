package com.example.demo.services;

import com.example.demo.interfaces.AccountRepository;
import com.example.demo.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Iterable<Account> getAll(){
        return accountRepository.findAll();
    }

    public Optional<Account> get(Long id){
        return accountRepository.findById(id);
    }

    public Iterable<Account> getAllByNumber(String number){
        return accountRepository.getAllByNumber(number);
    }

    public Iterable<Account> getAllByBankNameExists(String bankName){
        Iterable<Account> all  = getAll();
        List<Account> buffer = new ArrayList<>();
        for (var item : all) {
            try{
                if(item.getBankName().toLowerCase(Locale.ROOT)
                        .contains(bankName.toLowerCase(Locale.ROOT)))
                    buffer.add(item);
            }catch (Exception e){continue;}
        }

        return buffer;
    }

    public void add(Account account){
        accountRepository.save(account);
    }

    public void add(Long id, Account account){
        Account u = get(id).get();
        u.setEmployee(account.getEmployee());
        u.setBankName(account.getBankName());
        u.setNumber(account.getNumber());
        accountRepository.save(u);
    }

    public void delete(Long id){
        accountRepository.deleteById(id);
    }
}
