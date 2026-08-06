package com.horizon.bank.transaction.atmWithdrawal.repository;

import com.horizon.bank.transaction.atmWithdrawal.entity.AtmWithdrawalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtmWithdrawalRepository extends JpaRepository<AtmWithdrawalEntity, String> {
}
