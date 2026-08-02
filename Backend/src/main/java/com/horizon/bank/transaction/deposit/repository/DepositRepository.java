package com.horizon.bank.transaction.deposit.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.horizon.bank.transaction.deposit.entity.DepositEntity;

public interface DepositRepository  extends JpaRepository<DepositEntity, String> {
    
}
