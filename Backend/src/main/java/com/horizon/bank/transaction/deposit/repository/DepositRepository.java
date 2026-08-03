package com.horizon.bank.transaction.deposit.repository;
import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.horizon.bank.transaction.deposit.entity.DepositEntity;

import java.util.List;

public interface DepositRepository  extends JpaRepository<DepositEntity, String> {
    List<DepositEntity> getAllByStatusAndReferenceTransactionForRevert(TransactionStatus transactionStatus, String referenceTransactionForRevert);
    DepositEntity getByReferenceNumber(String referenceNumber);
}
