package com.horizon.bank.savings.debitCardPayment.entity;

import java.math.BigDecimal;

import com.horizon.bank.savings.debitCardPayment.enums.DebitCardTransactionType;
import com.horizon.bank.savings.debitCardPayment.enums.TransactionStatus;

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
@Table(name = "debit_card_payments")
public class DebitCardPaymentEntity {

    @Id
    @NotBlank(message = "id is required")
    private String id;

    // Customer Account
    @NotBlank(message = "account_id is required")
    private String accountId;

    // Debit Card
    @NotBlank(message = "debit_card_id is required")
    private String debitCardId;

    // Payment Amount
    @NotNull(message = "amount is required")
    private BigDecimal amount;

    @NotBlank(message = "currency is required")
    private String currency;

    // Merchant Details
    @NotBlank(message = "merchant_name is required")
    private String merchantName;

    @NotBlank(message = "merchant_id is required")
    private String merchantId;

    @NotBlank(message = "merchant_category is required")
    private String merchantCategory;

    // Optional Description
    private String description;

    // Authorization Details
    @NotBlank(message = "authorization_code is required")
    private String authorizationCode;

    @NotBlank(message = "terminal_id is required")
    private String terminalId;

    @NotBlank(message = "rrn is required")
    private String rrn;

    // Transaction Status
    @Enumerated(EnumType.STRING)
    @NotNull(message = "status is required")
    private TransactionStatus status;

    // Transaction Type
    @Enumerated(EnumType.STRING)
    @NotNull(message = "transaction_type is required")
    private DebitCardTransactionType transactionType;

    // Balance
    @NotNull(message = "balance_before is required")
    private BigDecimal balanceBefore;

    @NotNull(message = "balance_after is required")
    private BigDecimal balanceAfter;

    // Security
    private Boolean pinVerified = false;

    private Boolean contactless = false;

    // Audit
    private Long createdAt;

    private Long updatedAt;
}