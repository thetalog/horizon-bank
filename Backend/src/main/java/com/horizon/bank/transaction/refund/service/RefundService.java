package com.horizon.bank.transaction.refund.service;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.accounts.repository.AccountRepository;
import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.transaction.refund.dto.RefundApprovalDto;
import com.horizon.bank.transaction.refund.dto.RefundRequestDto;
import com.horizon.bank.transaction.refund.entity.RefundEntity;
import com.horizon.bank.transaction.refund.repository.RefundRepository;
import com.horizon.bank.transaction.transfer.entity.TransferEntity;
import com.horizon.bank.transaction.transfer.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefundService {

  final AccountRepository accountRepository;
  private final RefundRepository refundRepository;
  private final TransferRepository transferRepository;
  private final ResponseStructure responseStructure;

  public RefundService(
      RefundRepository refundRepository,
      TransferRepository transferRepository,
      ResponseStructure responseStructure,
      AccountRepository accountRepository) {
    this.refundRepository = refundRepository;
    this.transferRepository = transferRepository;
    this.responseStructure = responseStructure;
    this.accountRepository = accountRepository;
  }

  public ResponseStructure approveRefund(RefundApprovalDto requestDto) {

    Optional<RefundEntity> refund = refundRepository.findById(requestDto.getRefundId());

    if (refund.isEmpty()) {
      responseStructure.setStatusCode(404);
      responseStructure.setError(true);
      responseStructure.setMessage("Refund request not found");
      return responseStructure;
    }

    RefundEntity refundEntity = refund.get();

    // Already processed
    if (refundEntity.getStatus() != TransactionStatus.PENDING) {
      responseStructure.setStatusCode(400);
      responseStructure.setError(true);
      responseStructure.setMessage("Refund already processed");
      return responseStructure;
    }

    // Reject
    if (requestDto.getStatus() == TransactionStatus.FAILED) {

      refundEntity.setStatus(TransactionStatus.FAILED);
      refundRepository.save(refundEntity);

      responseStructure.setStatusCode(200);
      responseStructure.setError(false);
      responseStructure.setMessage("Refund rejected");
      responseStructure.setData(refundEntity);

      return responseStructure;
    }

    // Approve
    Optional<AccountEntity> payerAccount =
        accountRepository.findByAccountNumber(refundEntity.getPayerAccountNumber());

    Optional<AccountEntity> payeeAccount =
        accountRepository.findByAccountNumber(refundEntity.getPayee_account_number());

    if (payerAccount.isEmpty() || payeeAccount.isEmpty()) {
      responseStructure.setStatusCode(404);
      responseStructure.setError(true);
      responseStructure.setMessage("Account not found");
      return responseStructure;
    }

    // Check merchant balance
    if (payeeAccount.get().getBalance().compareTo(refundEntity.getAmount()) < 0) {

      responseStructure.setStatusCode(400);
      responseStructure.setError(true);
      responseStructure.setMessage("Merchant has insufficient balance");
      return responseStructure;
    }

    BigDecimal payerBefore = payerAccount.get().getBalance();
    BigDecimal payeeBefore = payeeAccount.get().getBalance();

    BigDecimal payerAfter = payerBefore.add(refundEntity.getAmount());

    BigDecimal payeeAfter = payeeBefore.subtract(refundEntity.getAmount());

    payerAccount.get().setBalance(payerAfter);
    payeeAccount.get().setBalance(payeeAfter);

    accountRepository.save(payerAccount.get());
    accountRepository.save(payeeAccount.get());

    refundEntity.setBalanceBefore(payeeBefore);
    refundEntity.setBalanceAfter(payeeAfter);
    refundEntity.setStatus(TransactionStatus.SUCCESS);

    refundRepository.save(refundEntity);

    responseStructure.setStatusCode(200);
    responseStructure.setError(false);
    responseStructure.setMessage("Refund approved successfully");
    responseStructure.setData(refundEntity);

    return responseStructure;
  }

  public ResponseStructure requestRefund(RefundRequestDto requestDto) {

    Optional<TransferEntity> transfer =
        transferRepository.findByReferenceNumber(
            requestDto.getOriginalTransactionReferenceNumber());

    if (transfer.isEmpty()) {
      responseStructure.setStatusCode(404);
      responseStructure.setError(true);
      responseStructure.setMessage("Original transaction not found");
      responseStructure.setData(null);
      return responseStructure;
    }

    TransferEntity original = transfer.get();

    if (original.getStatus() != TransactionStatus.SUCCESS) {
      responseStructure.setStatusCode(400);
      responseStructure.setError(true);
      responseStructure.setMessage("Refund cannot be requested");
      responseStructure.setData(null);
      return responseStructure;
    }

    if (refundRepository.existsByOriginalTransactionReferenceNumber(
        requestDto.getOriginalTransactionReferenceNumber())) {

      responseStructure.setStatusCode(400);
      responseStructure.setError(true);
      responseStructure.setMessage("Refund already requested");
      responseStructure.setData(null);
      return responseStructure;
    }

    if (requestDto.getAmount().compareTo(original.getAmount()) > 0) {
      responseStructure.setStatusCode(400);
      responseStructure.setError(true);
      responseStructure.setMessage("Invalid refund amount");
      responseStructure.setData(null);
      return responseStructure;
    }

    RefundEntity refund = new RefundEntity();

    refund.setId(UUID.randomUUID().toString());
    refund.setReferenceNumber(UUID.randomUUID().toString());

    refund.setOriginalTransactionReferenceNumber(original.getReferenceNumber());

    refund.setPayerAccountNumber(original.getPayerAccount().getAccountNumber());

    refund.setPayee_account_number(original.getPayeeAccount().getAccountNumber());

    refund.setAmount(requestDto.getAmount());
    refund.setCurrency(original.getCurrency());

    refund.setRefundType(requestDto.getRefundType());
    refund.setReason(requestDto.getReason());

    refund.setStatus(TransactionStatus.PENDING);

    refund.setBalanceBefore(original.getPayeeAccount().getBalance());

    refund.setBalanceAfter(original.getPayeeAccount().getBalance());

    refund.setCreatedAt(System.currentTimeMillis());
    refund.setUpdatedAt(System.currentTimeMillis());

    refundRepository.save(refund);

    responseStructure.setStatusCode(200);
    responseStructure.setError(false);
    responseStructure.setMessage("Refund request submitted successfully");
    responseStructure.setData(refund);

    return responseStructure;
  }

  public ResponseStructure getAllRefundRequests() {

    List<RefundEntity> refunds = refundRepository.findAll();

    if (refunds.isEmpty()) {
      responseStructure.setStatusCode(404);
      responseStructure.setError(true);
      responseStructure.setMessage("No refund requests found");
      return responseStructure;
    }

    responseStructure.setStatusCode(200);
    responseStructure.setError(false);
    responseStructure.setMessage("Refund requests fetched successfully");
    responseStructure.setData(refunds);

    return responseStructure;
  }

  public ResponseStructure getRefundRequest(String refundId) {

    Optional<RefundEntity> refund = refundRepository.findById(refundId);

    if (refund.isEmpty()) {
      responseStructure.setStatusCode(404);
      responseStructure.setError(true);
      responseStructure.setMessage("Refund request not found");
      return responseStructure;
    }

    responseStructure.setStatusCode(200);
    responseStructure.setError(false);
    responseStructure.setMessage("Refund request fetched successfully");
    responseStructure.setData(refund.get());

    return responseStructure;
  }
}
