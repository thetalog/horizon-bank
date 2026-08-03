package com.horizon.bank.transaction.deposit.entity;

import java.math.BigDecimal;

import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.transaction.deposit.enums.DepositTransactionType;

import jakarta.persistence.Column;
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
    @Column(name = "id")
    private String id;

    // Customer Account
    @Column(name = "payer_account_number")
    @NotBlank(message = "payer_account_number is required")
    private String payerAccountNumber;

    @Column(name = "payee_account_number")
    @NotBlank(message = "payee_account_number is required")
    private String payeeAccountNumber;
    // Deposit Amount
    @Column(name = "amount")
    @NotNull(message = "amount is required")
    private BigDecimal amount;

    @Column(name = "currency")
    @NotBlank(message = "currency is required")
    private String currency;

    // Deposit Information
    @Column(name = "deposit_type")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "deposit_type is required")
    private DepositTransactionType depositType;

    @Column(name = "description")
    private String description;

    // Optional Reference Details
    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "cheque_number")
    private String chequeNumber;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "employee_id")
    private String employeeId;

    // Transaction Status
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "status is required")
    private TransactionStatus status;

    @Column(name = "original_transaction_reference_number")
    private String originalTransactionReferenceNumber;
    // Balance
    @Column(name = "payee_balance_before")
    @NotNull(message = "payee_balance_before is required")
    private BigDecimal payeeBalanceBefore;

    @Column(name = "payee_balance_after")
    @NotNull(message = "payee_balance_after is required")
    private BigDecimal payeeBalanceAfter;

    @Column(name = "payer_balance_before")
    @NotNull(message = "payer_balance_before is required")
    private BigDecimal payerBalanceBefore;

    @Column(name = "payer_balance_after")
    @NotNull(message = "payer_balance_after is required")
    private BigDecimal payerBalanceAfter;

    // Audit
    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}