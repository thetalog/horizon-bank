package com.horizon.bank.transaction.transfer.repository;

import com.horizon.bank.transaction.transfer.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<TransferEntity, String> {
}
