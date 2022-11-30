package com.example.demo.controllers;

import com.example.demo.models.Account;
import com.example.demo.services.AccountService;
import com.example.demo.viewModels.SearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/addAccount")
    public String addAccount(Model model){
        model.addAttribute("account", new Account());
        model.addAttribute("isAddAction", true);
        return "account";
    }

    @PostMapping("/addAccount")
    public String addAccount(@ModelAttribute() @Valid Account account, BindingResult result, Model model){

        if (result.hasErrors()) {
            model.addAttribute("account", account);
            model.addAttribute("isAddAction", true);
            return "account";
        }

        accountService.add(account);
        List<Account> list = StreamSupport.stream(accountService.getAll().spliterator(), false).toList();
        model.addAttribute("accounts", list);
        model.addAttribute("searchModel", new SearchModel());
        return "accounts";
    }

    @GetMapping("/account/update/{id}")
    public String update(@PathVariable("id") long id, Model model) {
        Account account = accountService.get(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));

        model.addAttribute("account", account);
        model.addAttribute("isAddAction", false);
        return "account";
    }

    @PostMapping("/account/update/{id}")
    public String update(@PathVariable("id") long id, @Valid Account account,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("account", account);
            model.addAttribute("isAddAction", false);
            return "account";
        }

        accountService.add(id, account);

        List<Account> list = StreamSupport.stream(accountService.getAll().spliterator(), false).toList();
        model.addAttribute("accounts", list);
        model.addAttribute("searchModel", new SearchModel());
        return "accounts";
    }

    @GetMapping("/account/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        accountService.delete(id);

        List<Account> list = StreamSupport.stream(accountService.getAll().spliterator(), false).toList();
        model.addAttribute("accounts", list);
        model.addAttribute("searchModel", new SearchModel());
        return "accounts";
    }

    @GetMapping("/accounts")
    public String roles(Model model){
        List<Account> list = StreamSupport.stream(accountService.getAll().spliterator(), false).toList();
        model.addAttribute("accounts", list);
        model.addAttribute("searchModel", new SearchModel());
        return "accounts";
    }

    @PostMapping("/accounts")
    public String search(@ModelAttribute SearchModel searchModel, Model model){
        List<Account> list;
        if(searchModel.isHigh()){
            list = StreamSupport.stream(accountService.getAllByNumber(
                    searchModel.getTitle()).spliterator(), false).toList();
        }else{
            list = StreamSupport.stream(accountService.getAllByBankNameExists(
                    searchModel.getTitle()).spliterator(), false).toList();
        }

        model.addAttribute("accounts", list);
        model.addAttribute("searchModel", new SearchModel());
        return "accounts";
    }

}
