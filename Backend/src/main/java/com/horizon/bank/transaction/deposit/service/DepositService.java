package com.horizon.bank.transaction.deposit.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.transaction.deposit.dto.DepositTransactionRequestDto;
import com.horizon.bank.transaction.deposit.entity.DepositEntity;
import com.horizon.bank.transaction.deposit.repository.DepositRepository;

@Service
public class DepositService {
    public DepositRepository depositRepository;
    public DepositService(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    public BigDecimal createDepositTransaction(DepositTransactionRequestDto requestDto) {
        try{
            DepositEntity depositEntity = new DepositEntity();
            depositEntity.setAmount(requestDto.getAmount());
            depositEntity.setCurrency(requestDto.getCurrency());
            depositEntity.setDepositType(requestDto.getDepositType());
            depositEntity.setAccountId(requestDto.getAccountId());
            depositEntity.setDepositedBy(requestDto.getDepositedBy());
            depositEntity.setStatus(TransactionStatus.SUCCESS);
            depositRepository.save(depositEntity);
            return requestDto.getAmount();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create deposit transaction: " + e.getMessage());
        }
    }
}
