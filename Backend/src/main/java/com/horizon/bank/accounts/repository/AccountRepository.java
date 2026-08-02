package com.horizon.bank.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horizon.bank.accounts.entity.Account;


public interface AccountRepository extends JpaRepository<Account, String> {
    
}
