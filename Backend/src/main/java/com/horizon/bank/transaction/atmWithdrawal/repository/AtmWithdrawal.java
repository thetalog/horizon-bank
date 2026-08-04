package com.horizon.bank.transaction.atmWithdrawal.repository;

import com.horizon.bank.transaction.atmWithdrawal.entity.AtmWithdrawalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtmWithdrawal extends JpaRepository<AtmWithdrawalEntity, String> {
}
