package com.horizon.bank.transaction.interestCredit.entity;

import java.math.BigDecimal;

import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.transaction.interestCredit.enums.InterestCreditType;

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
@Table(name = "interest_credits")
public class InterestCreditEntity {

    @Id
    @NotBlank(message = "id is required")
    private String id;

    // Customer Account
    @NotBlank(message = "account_id is required")
    private String accountId;

    // Interest Amount
    @NotNull(message = "amount is required")
    private BigDecimal amount;

    @NotBlank(message = "currency is required")
    private String currency;

    // Interest Details
    @Enumerated(EnumType.STRING)
    @NotNull(message = "interest_type is required")
    private InterestCreditType interestType;

    // Annual interest rate (e.g. 3.50)
    @NotNull(message = "interest_rate is required")
    private BigDecimal interestRate;

    // Interest calculation period
    private Long periodStart;

    private Long periodEnd;

    private String description;

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