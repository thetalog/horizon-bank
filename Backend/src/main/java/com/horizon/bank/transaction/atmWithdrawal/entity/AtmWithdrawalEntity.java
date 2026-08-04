package com.horizon.bank.transaction.atmWithdrawal.entity;

import java.math.BigDecimal;

import com.horizon.bank.transaction.atmWithdrawal.enums.WithdrawalTransactionType;
import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;

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
@Table(name = "atm_withdrawals")
public class AtmWithdrawalEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "account_number")
    @NotBlank(message = "account_number is required")
    private String accountNumber;

    @Column(name = "amount")
    @NotNull(message = "amount is required")
    private BigDecimal amount;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "atm_id")
    private String atmId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "status is required")
    private TransactionStatus status;

    @Column(name = "balance_before")
    @NotNull(message = "balance_before is required")
    private BigDecimal balanceBefore;

    @Column(name = "balance_after")
    @NotNull(message = "balance_after is required")
    private BigDecimal balanceAfter;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}