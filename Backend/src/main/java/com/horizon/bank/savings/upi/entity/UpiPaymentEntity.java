package com.horizon.bank.savings.upi.entity;

import java.math.BigDecimal;

import com.horizon.bank.savings.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.savings.upi.enums.UpiTransactionType;

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
@Table(name = "upi_payments")
public class UpiPaymentEntity {

    @Id
    @NotBlank(message = "id is required")
    private String id;

    // Sender Account
    @NotBlank(message = "account_id is required")
    private String accountId;

    // UPI IDs
    @NotBlank(message = "payer_upi_id is required")
    private String payerUpiId;

    @NotBlank(message = "payee_upi_id is required")
    private String payeeUpiId;

    // Payment
    @NotNull(message = "amount is required")
    private BigDecimal amount;

    @NotBlank(message = "currency is required")
    private String currency;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "transaction_type is required")
    private UpiTransactionType transactionType;

    // Merchant (optional)
    private String merchantName;

    private String merchantId;

    // NPCI / Bank Reference
    private String utrNumber;

    private String referenceNumber;

    private String description;

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