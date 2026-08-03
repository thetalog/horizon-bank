package com.horizon.bank.accounts.repository;

import java.util.List;
import java.util.Optional;

import com.horizon.bank.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.horizon.bank.accounts.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    Optional<AccountEntity> findByAccountNumber(String accountNumber);
    List<AccountEntity> findByUserId(String userId);

}
