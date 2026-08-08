package com.horizon.bank.transaction.transfer.repository;

import com.horizon.bank.transaction.transfer.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransferRepository extends JpaRepository<TransferEntity, String> {
  List<TransferEntity> findByPayeeAccountAccountNumber(String accountNumber);

  List<TransferEntity> findByPayerAccountAccountNumber(String accountNumber);

  Optional<TransferEntity> findByReferenceNumber(String referenceNumber);
}
