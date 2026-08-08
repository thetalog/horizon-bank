package com.horizon.bank.transaction.refund.repository;

import com.horizon.bank.transaction.refund.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<RefundEntity, String> {
  boolean existsByOriginalTransactionReferenceNumber(String referenceNumber);
}
