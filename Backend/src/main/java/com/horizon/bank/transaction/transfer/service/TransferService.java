package com.horizon.bank.transaction.transfer.service;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.accounts.repository.AccountRepository;
import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.transaction.transfer.dto.AccountTransferRequestDto;
import com.horizon.bank.transaction.transfer.dto.GetAllTransferTransactionsDto;
import com.horizon.bank.transaction.transfer.dto.GetParticularTransferTransactionDto;
import com.horizon.bank.transaction.transfer.entity.TransferEntity;
import com.horizon.bank.transaction.transfer.repository.TransferRepository;
import com.horizon.bank.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransferService {
  private final TransferRepository transferRepository;
  private final ResponseStructure responseStructure;
  private final UserRepository userRepository;
  private final AccountRepository accountRepository;

  TransferService(
      ResponseStructure responseStructure,
      UserRepository userRepository,
      AccountRepository accountRepository,
      TransferRepository transferRepository) {
    this.responseStructure = responseStructure;
    this.userRepository = userRepository;
    this.accountRepository = accountRepository;
    this.transferRepository = transferRepository;
  }

  public ResponseStructure getAllTransferTransactions(
      GetAllTransferTransactionsDto requestDto, ResponseStructure responseStructure) {
    List<TransferEntity> payeeTransactions =
        transferRepository.findByPayeeAccountAccountNumber(requestDto.getAccountNumber());
    List<TransferEntity> payerTransactions =
        transferRepository.findByPayerAccountAccountNumber(requestDto.getAccountNumber());

    List<TransferEntity> mergedTransactions = new java.util.ArrayList<>();
    mergedTransactions.addAll(payeeTransactions);
    mergedTransactions.addAll(payerTransactions);

    if (mergedTransactions.isEmpty()) {
      responseStructure.setStatusCode(404);
      responseStructure.setError(true);
      responseStructure.setMessage("No transfer transactions found for the given account number");
      responseStructure.setData(null);
      return responseStructure;
    }

    mergedTransactions.sort(
        Comparator.comparing(
            TransferEntity::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())));

    responseStructure.setStatusCode(200);
    responseStructure.setError(false);
    responseStructure.setMessage("Transfer transactions found");
    responseStructure.setData(mergedTransactions);
    return responseStructure;
  }

  public ResponseStructure getParticularTransferTransaction(
      GetParticularTransferTransactionDto requestDto, ResponseStructure responseStructure) {
    Optional<TransferEntity> transaction =
        transferRepository.findByReferenceNumber(requestDto.getTransactionReferenceNumber());

    if (transaction.isEmpty()) {
      responseStructure.setStatusCode(404);
      responseStructure.setError(true);
      responseStructure.setMessage("Transfer transaction not found");
      responseStructure.setData(null);
      return responseStructure;
    }

    responseStructure.setStatusCode(200);
    responseStructure.setError(false);
    responseStructure.setMessage("Transfer transaction found");
    responseStructure.setData(transaction.get());
    return responseStructure;
  }

  public ResponseStructure transfer(
      AccountTransferRequestDto requestDto, ResponseStructure responseStructure) {
    Optional<AccountEntity> payeeAccount =
        accountRepository.findByAccountNumber(requestDto.getPayeeAccountNumber());
    Optional<AccountEntity> payerAccount =
        accountRepository.findByAccountNumber(requestDto.getPayerAccountNumber());

    if (payeeAccount.isEmpty() || payerAccount.isEmpty()) {
      responseStructure.setStatusCode(404);
      responseStructure.setError(true);
      responseStructure.setMessage("payee or payer account not found");
      responseStructure.setData(null);
      return responseStructure;
    }

    if (!payeeAccount.get().getIsActive() || !payerAccount.get().getIsActive()) {
      responseStructure.setStatusCode(404);
      responseStructure.setError(true);
      responseStructure.setMessage("Some of the account is inactive");
      responseStructure.setData(null);
      return responseStructure;
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
    transaction.setPayeeAccount(payeeAccount.get());
    transaction.setPayerAccount(payerAccount.get());
    transaction.setReferenceNumber(UUID.randomUUID().toString());
    transaction.setRemarks(requestDto.getRemarks());
    transaction.setTransactionPin(requestDto.getTransactionPin());
    transaction.setTransferType(requestDto.getTransferType());
    transaction.setCreatedAt(System.currentTimeMillis());
    transaction.setUpdatedAt(System.currentTimeMillis());

    if (currentPayerBalance.compareTo(requestDto.getAmount()) < 0) {
      transaction.setStatus(TransactionStatus.FAILED);
      transferRepository.save(transaction);

      responseStructure.setStatusCode(400);
      responseStructure.setError(true);
      responseStructure.setMessage("Insufficient balance");
      responseStructure.setData(transaction);
      return responseStructure;
    }

    transaction.setStatus(TransactionStatus.SUCCESS);
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
