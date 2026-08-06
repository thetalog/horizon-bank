package com.horizon.bank.accounts.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.horizon.bank.accounts.dto.CreateAccountRequestDto;
import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.accounts.entity.enums.AccountType;
import com.horizon.bank.accounts.repository.AccountRepository;
import com.horizon.bank.user.controller.GlobalExceptionHandler;
import com.horizon.bank.user.entity.UserEntity;
import com.horizon.bank.user.service.UserService;
@Service
public class AccountService {
    private final GlobalExceptionHandler globalExceptionHandler;
    AccountRepository accountRepository;
    UserService userService;
    public AccountService(AccountRepository accountRepository, UserService userService, GlobalExceptionHandler globalExceptionHandler) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.globalExceptionHandler = globalExceptionHandler;
    }
    public UserEntity getUserByAccountId(String accountNumber){
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getUser();
    }
    public BigDecimal getAccountBalance(String accountNumber) {
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }
    public Boolean isAccountActive(String accountNumber) {
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getIsActive();
    }
    public AccountEntity getAccountDetails(String accountNumber) {
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        return account;
    }
    public List<AccountEntity> getAllAccountDetails(String userId) {
        return accountRepository.findByUserId(userId);
    }
    public void updateAccountBalance(String accountNumber, BigDecimal newBalance) {
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
    public String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.valueOf((long) (Math.random() * 1_000_000_000_000L));
        } while (accountNumber.length() < 12 || accountRepository.findByAccountNumber(accountNumber).isPresent());
        return accountNumber;
    }
    public HashMap<String, Object> createAccount(String accountNumber, CreateAccountRequestDto request) {
        try{
            UserEntity userEntity = userService.getUserById(request.getUserId());
            AccountType accountType = request.getAccountType();
            AccountEntity account = new AccountEntity();
            if (accountType == null) {
                throw new RuntimeException("Account type is required");
            }
            else{
                for (AccountType type : AccountType.values()) {
                    if (type == accountType) {
                        account.setAccountType(AccountType.valueOf(type.name()));
                        break;
                    }
                }
            }
            
            account.setAccountNumber(accountNumber);
            account.setIfscCode(request.getIfscCode());
            account.setBalance(request.getBalance());
            account.setBranchName(request.getBranchName() != null ? request.getBranchName() : request.getBranchCode());
            account.setEmployeeId(request.getEmployeeId());
            account.setUser(userEntity);
            account.setIsActive(false);
            accountRepository.save(account);
            HashMap<String, Object> response = new HashMap<>();
            response.put("accountNumber", account.getAccountNumber());
            response.put("message"," Account created successfully");
            return response;
        } catch (Exception e) {
            HashMap<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", "Error creating account: " + e.getMessage());
            return errorDetails;
        }
    }
}
