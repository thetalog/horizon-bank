package com.horizon.bank.savings.deposit.entity;

import java.math.BigDecimal;

import com.horizon.bank.savings.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.savings.deposit.enums.DepositTransactionType;

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
@Table(name = "deposits")
public class DepositEntity {

    @Id
    @NotBlank(message = "id is required")
    private String id;

    // Customer Account
    @NotBlank(message = "account_id is required")
    private String accountId;

    // Deposit Amount
    @NotNull(message = "amount is required")
    private BigDecimal amount;

    @NotBlank(message = "currency is required")
    private String currency;

    // Deposit Information
    @Enumerated(EnumType.STRING)
    @NotNull(message = "deposit_type is required")
    private DepositTransactionType depositType;

    private String description;

    // Optional Reference Details
    private String referenceNumber;

    private String chequeNumber;

    private String branchCode;

    private String depositedBy;

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