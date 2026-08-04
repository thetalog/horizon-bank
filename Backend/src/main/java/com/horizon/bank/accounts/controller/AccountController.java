package com.horizon.bank.accounts.controller;

import com.horizon.bank.accounts.entity.AccountEntity;
import org.springframework.web.bind.annotation.*;

import com.horizon.bank.accounts.dto.CreateAccountRequestDto;
import com.horizon.bank.accounts.service.AccountService;

import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.accounts.dto.GetAccountDetailsRequestDto;
import com.horizon.bank.accounts.dto.GetAllAccountDetailsRequestDto;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    public AccountService accountService;
    public ResponseStructure response;

    public AccountController(AccountService accountService, ResponseStructure response) {
        this.accountService = accountService;
        this.response = response;
    }
    @GetMapping("/get-account-details")
    public HashMap<String, Object> getAccountDetails(@RequestBody GetAccountDetailsRequestDto request){
        AccountEntity accountDetailsResponse = accountService.getAccountDetails(request.getAccountNumber());
        response.setResponse(200, "Account details fetched.", false, accountDetailsResponse);
        return response.send();
    }

    @GetMapping("/get-all-account-details")
    public HashMap<String, Object> getAccountDetails(@RequestBody GetAllAccountDetailsRequestDto request){
        List<AccountEntity> accountDetailsResponse = accountService.getAllAccountDetails(request.getUserId());
        response.setResponse(200, "Account details fetched.",false, accountDetailsResponse);
        return response.send();
    }


    @PostMapping("/create-account")
    public HashMap<String, Object> createAccount(@RequestBody CreateAccountRequestDto request) {
        String accountNumber = accountService.generateAccountNumber();
        HashMap<String, Object> accountResponse = accountService.createAccount(accountNumber, request);
        response.setResponse(200, "Account created successfully",false, accountResponse);
        return response.send();
    }
}
