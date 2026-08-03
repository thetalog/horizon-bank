package com.horizon.bank.transaction.deposit.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.horizon.bank.accounts.service.AccountService;
import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.transaction.deposit.dto.DepositTransactionRequestDto;
import com.horizon.bank.transaction.deposit.entity.DepositEntity;
import com.horizon.bank.transaction.deposit.repository.DepositRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class DepositService {
    public DepositRepository depositRepository;
    public AccountService accountService;
    public DepositService(DepositRepository depositRepository, AccountService accountService) {
        this.depositRepository = depositRepository;
        this.accountService = accountService;
    }
    
    public BigDecimal createDepositTransaction(DepositTransactionRequestDto requestDto) {
        try{
            BigDecimal accountBalance = accountService.getAccountBalance(requestDto.getAccountId());
            DepositEntity depositEntity = new DepositEntity();
            depositEntity.setId(UUID.randomUUID().toString());
            depositEntity.setAmount(requestDto.getAmount());
            depositEntity.setCurrency(requestDto.getCurrency());
            depositEntity.setDepositType(requestDto.getDepositType());
            depositEntity.setAccountId(requestDto.getAccountId());
            depositEntity.setDepositedBy(requestDto.getDepositedBy());
            depositEntity.setStatus(TransactionStatus.SUCCESS);
            depositEntity.setBalanceBefore(accountBalance);
            depositEntity.setBalanceAfter(accountBalance.add(requestDto.getAmount()));
            accountService.updateAccountBalance(requestDto.getAccountId(), accountBalance.add(requestDto.getAmount()));
            depositRepository.save(depositEntity);
            return requestDto.getAmount();
        } catch (Exception e) {
            log.error("Failed to create deposit transaction: " + e.getMessage(), e);
            throw new RuntimeException("Failed to create deposit transaction: " + e.getMessage());
        }
    }
}
