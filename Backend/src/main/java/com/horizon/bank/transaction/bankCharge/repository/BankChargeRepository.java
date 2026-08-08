package com.horizon.bank.transaction.bankCharge.repository;

import com.horizon.bank.transaction.bankCharge.entity.BankChargeEntity;
import com.horizon.bank.accounts.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankChargeRepository extends JpaRepository<BankChargeEntity, String> {

  boolean existsByAccountAndChargeMonth(AccountEntity account, String chargeMonth);

  List<BankChargeEntity> findByAccount(AccountEntity account);

  List<BankChargeEntity> findByChargeMonth(String chargeMonth);
}
