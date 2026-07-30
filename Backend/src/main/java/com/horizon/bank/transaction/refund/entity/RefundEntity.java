package com.horizon.bank.transaction.refund.entity;

import java.math.BigDecimal;

import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.transaction.refund.enums.RefundType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "refunds")
public class RefundEntity {

    @Id
    @NotBlank(message = "id is required")
    private String id;

    // Customer Account
    @NotBlank(message = "account_id is required")
    private String accountId;

    // Original Transaction
    @NotBlank(message = "original_transaction_id is required")
    private String originalTransactionId;

    // Refund Amount
    @NotNull(message = "amount is required")
    private BigDecimal amount;

    @NotBlank(message = "currency is required")
    private String currency;

    // Refund Details
    @Enumerated(EnumType.STRING)
    @NotNull(message = "refund_type is required")
    private RefundType refundType;

    private String reason;

    private String referenceNumber;

    // Transaction Status
    @Enumerated(EnumType.STRING)
    @NotNull(message = "status is required")
    private TransactionStatus status;

    // Balance
    @NotNull(message = "balance_before is required")
    private BigDecimal balanceBefore;

    @NotNull(message = "balance_after is required")
    private BigDecimal balanceAfter;

    // Audit
    private Long createdAt;

    private Long updatedAt;
}