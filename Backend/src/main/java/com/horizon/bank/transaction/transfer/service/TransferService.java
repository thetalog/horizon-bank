package com.horizon.bank.transaction.transfer.service;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.accounts.repository.AccountRepository;
import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.transaction.transfer.dto.AccountTransferRequestDto;
import com.horizon.bank.transaction.transfer.entity.TransferEntity;
import com.horizon.bank.transaction.transfer.repository.TransferRepository;
import com.horizon.bank.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransferService {
    TransferRepository transferRepository;
    ResponseStructure responseStructure;
    UserRepository userRepository;
    AccountRepository accountRepository;
    TransferService(ResponseStructure responseStructure, UserRepository userRepository, AccountRepository accountRepository, TransferRepository transferRepository){
        this.responseStructure = responseStructure;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    public ResponseStructure transfer(AccountTransferRequestDto requestDto, ResponseStructure responseStructure){
        //check account active
        Optional<AccountEntity> payeeAccount = accountRepository.findByAccountNumber(requestDto.getPayeeAccountNumber());
        Optional<AccountEntity> payerAccount = accountRepository.findByAccountNumber(requestDto.getPayerAccountNumber());
        if(payeeAccount.isEmpty() || payerAccount.isEmpty()){
            responseStructure.setStatusCode(404);
            responseStructure.setError(true);
            responseStructure.setMessage("payee or payer account not found");
            responseStructure.setData(null);
        }

        if(payeeAccount == null || payerAccount == null){
            responseStructure.setStatusCode(404);
            responseStructure.setError(true);
            responseStructure.setMessage("payee or payer account not found");
            responseStructure.setData(null);
        }

        if(!payeeAccount.get().getIsActive() || !payerAccount.get().getIsActive()){
            responseStructure.setStatusCode(404);
            responseStructure.setError(true);
            responseStructure.setMessage("Some of the account is inactive");
            responseStructure.setData(null);
        }
        BigDecimal currentPayerBalance = payerAccount.get().getBalance();
        BigDecimal currentPayeeBalance = payeeAccount.get().getBalance();

        TransferEntity transaction = new TransferEntity();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setAmount(requestDto.amount);
        transaction.setPayerBalanceAfter(currentPayerBalance.subtract(requestDto.getAmount()));
        transaction.setPayerBalanceBefore(currentPayerBalance);
        transaction.setPayeeBalanceAfter(currentPayeeBalance.add(requestDto.getAmount()));
        transaction.setPayeeBalanceBefore(currentPayeeBalance);
        transaction.setCurrency("INR");
        transaction.setPayeeAccountId(requestDto.getPayeeAccountNumber());
        transaction.setPayerAccountId(requestDto.getPayerAccountNumber());
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setRemarks(requestDto.getRemarks());
        transaction.setTransactionPin(requestDto.getTransactionPin());
        transaction.setTransferType(requestDto.getTransferType());
        //check sufficient balance in payer account
        if (currentPayerBalance.compareTo(BigDecimal.ZERO) < 0) {
            transaction.setStatus(TransactionStatus.FAILED);

            transferRepository.save(transaction);

            responseStructure.setStatusCode(400);
            responseStructure.setError(true);
            responseStructure.setMessage("Insufficient balance");

            return responseStructure;
        }
        transaction.setStatus(TransactionStatus.SUCCESS);
        // transfer
        payerAccount.get().setBalance(currentPayerBalance.subtract(requestDto.getAmount()));
        payeeAccount.get().setBalance(currentPayeeBalance.add(requestDto.getAmount()));
        accountRepository.save(payerAccount.get());
        accountRepository.save(payeeAccount.get());
        transferRepository.save(transaction);
        responseStructure.setStatusCode(200);
        responseStructure.setError(false);
        responseStructure.setMessage("Transfer Successful");
        responseStructure.setData(transaction);
        return responseStructure;
    }

}
