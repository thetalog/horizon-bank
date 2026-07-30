package com.horizon.bank.savings.transfer.entity;

import java.math.BigDecimal;

import com.horizon.bank.savings.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.savings.transfer.enums.TransferType;

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
@Entity(name = "TransferModuleTransferEntity")
@Table(name = "transfers")
public class TransferEntity {

    @Id
    @NotBlank(message = "id is required")
    private String id;

    // Sender Account
    @NotBlank(message = "from_account_id is required")
    private String fromAccountId;

    // Receiver Account
    @NotBlank(message = "to_account_id is required")
    private String toAccountId;

    // Transfer Amount
    @NotNull(message = "amount is required")
    private BigDecimal amount;

    @NotBlank(message = "currency is required")
    private String currency;

    // Transfer Details
    @Enumerated(EnumType.STRING)
    @NotNull(message = "transfer_type is required")
    private TransferType transferType;

    private String description;

    private String referenceNumber;

    // Status
    @Enumerated(EnumType.STRING)
    @NotNull(message = "status is required")
    private TransactionStatus status;

    // Sender Balance
    @NotNull(message = "balance_before is required")
    private BigDecimal balanceBefore;

    @NotNull(message = "balance_after is required")
    private BigDecimal balanceAfter;

    // Audit
    private Long createdAt;

    private Long updatedAt;
}