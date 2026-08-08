package com.horizon.bank.transaction.transfer.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.transaction.transfer.enums.TransferType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "TransferModuleTransferEntity")
@Table(name = "transfers")
public class TransferEntity {

  @Id
  @NotBlank(message = "id is required")
  private String id;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "payer_account_id")
  private AccountEntity payerAccount;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "payee_account_id")
  private AccountEntity payeeAccount;

  // transaction pin
  @NotBlank(message = "transaction_pin is required")
  private String transactionPin;

  // Transfer Amount
  @NotNull(message = "amount is required")
  private BigDecimal amount;

  @NotBlank(message = "currency is required")
  private String currency;

  // Transfer Details
  @Enumerated(EnumType.STRING)
  @NotNull(message = "transfer_type is required")
  private TransferType transferType;

  private String remarks;

  private String referenceNumber;

  // Status
  @Enumerated(EnumType.STRING)
  @NotNull(message = "status is required")
  private TransactionStatus status;

  // Sender Balance
  @NotNull(message = "payee_balance_before is required")
  private BigDecimal payeeBalanceBefore;

  @NotNull(message = "payee_balance_after is required")
  private BigDecimal payeeBalanceAfter;

  @NotNull(message = "payer_balance_before is required")
  private BigDecimal payerBalanceBefore;

  @NotNull(message = "payer_balance_after is required")
  private BigDecimal payerBalanceAfter;

  // Audit
  private Long createdAt;

  private Long updatedAt;
}
