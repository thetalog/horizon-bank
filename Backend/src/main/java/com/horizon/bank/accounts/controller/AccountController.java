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
    public ResponseStructure getAccountDetails(@RequestBody GetAccountDetailsRequestDto request){
        AccountEntity accountDetailsResponse = accountService.getAccountDetails(request.getAccountNumber());
        response.setData(accountDetailsResponse);
        response.setMessage("Account details fetched.");
        response.setError(false);
        response.setStatusCode(200);
        return response;
    }

    @GetMapping("/get-all-account-details")
    public ResponseStructure getAccountDetails(@RequestBody GetAllAccountDetailsRequestDto request){
        List<AccountEntity> accountDetailsResponse = accountService.getAllAccountDetails(request.getUserId());
        response.setData(accountDetailsResponse);
        response.setMessage("Account details fetched.");
        response.setError(false);
        response.setStatusCode(200);
        return response;
    }


    @PostMapping("/create-account")
    public ResponseStructure createAccount(@RequestBody CreateAccountRequestDto request) {
        String accountNumber = accountService.generateAccountNumber();
        HashMap<String, Object> accountResponse = accountService.createAccount(accountNumber, request);
        response.setData(accountResponse);
        response.setMessage("Account created successfully.");
        response.setError(false);
        response.setStatusCode(200);
        return response;
    }
}
