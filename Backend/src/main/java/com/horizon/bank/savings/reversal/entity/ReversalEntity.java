package com.horizon.bank.savings.reversal.entity;

import java.math.BigDecimal;

import com.horizon.bank.savings.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.savings.reversal.enums.ReversalType;

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
@Table(name = "reversals")
public class ReversalEntity {

    @Id
    @NotBlank(message = "id is required")
    private String id;

    // Customer Account
    @NotBlank(message = "account_id is required")
    private String accountId;

    // Transaction Being Reversed
    @NotBlank(message = "original_transaction_id is required")
    private String originalTransactionId;

    // Reversal Amount
    @NotNull(message = "amount is required")
    private BigDecimal amount;

    @NotBlank(message = "currency is required")
    private String currency;

    // Reversal Information
    @Enumerated(EnumType.STRING)
    @NotNull(message = "reversal_type is required")
    private ReversalType reversalType;

    private String reason;

    private String referenceNumber;

    // Status
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