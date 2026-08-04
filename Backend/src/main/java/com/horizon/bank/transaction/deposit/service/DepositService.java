package com.horizon.bank.transaction.deposit.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.transaction.deposit.enums.DepositTransactionType;
import org.springframework.stereotype.Service;

import com.horizon.bank.accounts.service.AccountService;
import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.transaction.deposit.dto.DepositTransactionRequestDto;
import com.horizon.bank.transaction.deposit.entity.DepositEntity;
import com.horizon.bank.transaction.deposit.repository.DepositRepository;
import com.horizon.bank.transaction.deposit.dto.ReverseDepositTransactionDto;

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
    
    public ResponseStructure createDepositTransactionNonCash(DepositTransactionRequestDto requestDto, ResponseStructure responseStructure) {
        try{
            if(requestDto.getPayerAccountNumber().isBlank()) {
                throw new RuntimeException("Deductor account id not mentioned");
            }
            AccountEntity payeeAccountDetails = accountService.getAccountDetails(requestDto.getPayeeAccountNumber());
            AccountEntity payerAccountDetails = accountService.getAccountDetails(requestDto.getPayerAccountNumber());

            if (payerAccountDetails.getBalance().compareTo(requestDto.getAmount()) < 0) {
                throw new RuntimeException("Insufficient balance");
            }

            DepositEntity depositEntity = new DepositEntity();
            depositEntity.setId(UUID.randomUUID().toString());
            depositEntity.setAmount(requestDto.getAmount());
            depositEntity.setCurrency(requestDto.getCurrency());
            depositEntity.setDepositType(requestDto.getDepositType());
            depositEntity.setPayeeAccountNumber(requestDto.getPayeeAccountNumber());
            depositEntity.setPayerAccountNumber(requestDto.getPayerAccountNumber());
            depositEntity.setEmployeeId(requestDto.getEmployeeId());
            depositEntity.setStatus(TransactionStatus.SUCCESS);
            depositEntity.setBranchCode(requestDto.getBranchCode());
            depositEntity.setPayeeBalanceBefore(payeeAccountDetails.getBalance());
            depositEntity.setPayeeBalanceAfter(payeeAccountDetails.getBalance().add(requestDto.getAmount()));
            depositEntity.setPayerBalanceBefore(payerAccountDetails.getBalance());
            depositEntity.setPayerBalanceAfter(payerAccountDetails.getBalance().subtract(requestDto.getAmount()));
            depositEntity.setReferenceNumber(UUID.randomUUID().toString());
            accountService.updateAccountBalance(payerAccountDetails.getAccountNumber(), payerAccountDetails.getBalance().subtract(requestDto.getAmount()));
            accountService.updateAccountBalance(payeeAccountDetails.getAccountNumber(), payeeAccountDetails.getBalance().add(requestDto.getAmount()));
            depositRepository.save(depositEntity);
            responseStructure.setData(depositEntity);
            responseStructure.setMessage("Deposit successful");
            responseStructure.setError(false);
        } catch (Exception e) {
            responseStructure.setData(null);
            responseStructure.setMessage("Failed to create deposit transaction: " + e.getMessage());
            responseStructure.setError(true);
        }
        return responseStructure;
    }

    public ResponseStructure reverseDepositTransaction(ReverseDepositTransactionDto requestDto, ResponseStructure responseStructure){
        DepositEntity originalDepositEntity = new DepositEntity();

        try{
            //check if reverse transaction with same transaction id happen before
            List<DepositEntity> listOfAlreadyRefundedTranasaction = depositRepository.getAllByStatusAndOriginalTransactionReferenceNumber(TransactionStatus.REFUNDED, requestDto.getOriginalTransactionReferenceNumber());
            if(!listOfAlreadyRefundedTranasaction.isEmpty()){
                throw new RuntimeException("Transaction already refunded.");
            }

            // get original transaction to revert
            DepositEntity originalTransaction = depositRepository.getByReferenceNumber(requestDto.getOriginalTransactionReferenceNumber());

            // accounts
            AccountEntity payeeAccountDetails = accountService.getAccountDetails(originalTransaction.getPayeeAccountNumber());
            AccountEntity payerAccountDetails = accountService.getAccountDetails(originalTransaction.getPayerAccountNumber());

            if(originalTransaction.getDepositType() != DepositTransactionType.CASH) {
                AccountEntity recipientAccountDetails = new AccountEntity();

                //deduct from original and add amount to recipient account id
                accountService.updateAccountBalance(payeeAccountDetails.getAccountNumber(), payeeAccountDetails.getBalance().subtract(originalTransaction.getAmount()));
                accountService.updateAccountBalance(payerAccountDetails.getAccountNumber(), payerAccountDetails.getBalance().add(originalTransaction.getAmount()));

                // 1st deposit entity for original account refund
                originalDepositEntity.setId(UUID.randomUUID().toString());
                originalDepositEntity.setAmount(originalTransaction.getAmount());
                originalDepositEntity.setCurrency(originalTransaction.getCurrency());
                originalDepositEntity.setDepositType(originalTransaction.getDepositType());
                originalDepositEntity.setPayeeAccountNumber(payeeAccountDetails.getAccountNumber());
                originalDepositEntity.setPayerAccountNumber(payerAccountDetails.getAccountNumber());
                originalDepositEntity.setEmployeeId(requestDto.getEmployeeId());
                originalDepositEntity.setStatus(TransactionStatus.REFUNDED);
                originalDepositEntity.setOriginalTransactionReferenceNumber(requestDto.getOriginalTransactionReferenceNumber());
                originalDepositEntity.setBranchCode(originalTransaction.getBranchCode());
                originalDepositEntity.setPayeeBalanceBefore(payeeAccountDetails.getBalance());
                originalDepositEntity.setPayeeBalanceAfter(payeeAccountDetails.getBalance().subtract(originalTransaction.getAmount()));
                originalDepositEntity.setPayerBalanceBefore(payerAccountDetails.getBalance());
                originalDepositEntity.setPayerBalanceAfter(payerAccountDetails.getBalance().add(originalTransaction.getAmount()));
                depositRepository.save(originalDepositEntity);
            }
            responseStructure.setError(false);
            responseStructure.setMessage("Transaction refund successfully");
            responseStructure.setData(originalDepositEntity);
        } catch (Exception e) {
            responseStructure.setError(true);
            responseStructure.setMessage("Transaction refund failed");
            responseStructure.setData(e.getMessage());
        }
        return responseStructure;
    }
}
