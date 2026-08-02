package com.horizon.bank.accounts.service;

import org.springframework.stereotype.Service;

import com.horizon.bank.accounts.entity.Account;
import com.horizon.bank.accounts.repository.AccountRepository;
import com.horizon.bank.user.entity.User;

@Service
public class AccountService {
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public User getUserByAccountId(String accountId){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getUser();
    }
    public Long getAccountBalance(String accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }
    public Boolean isAccountActive(String accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getIs_active();
    }
    public Account getAccountDetails(String accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
