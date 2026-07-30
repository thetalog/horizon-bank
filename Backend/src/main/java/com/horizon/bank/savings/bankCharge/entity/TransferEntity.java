package com.horizon.bank.savings.bankCharge.entity;

import com.horizon.bank.savings.atmWithdrawal.enums.WithdrawalTransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "BankChargeTransferEntity")
@Table(name="savings_transfers")
public class TransferEntity {
    @Id
    private String id;
    @NotNull(message="account_id is required")
    @NotBlank(message="account_id is required")
    private String account_id;
    @NotNull(message="from_account_id is required")
    @NotBlank(message="from_account_id is required")
    private String from_account_id;
    @NotNull(message="to_account_id is required")
    @NotBlank(message="to_account_id is required")
    private String to_account_id;
    @NotNull(message="amount is required")
    @NotBlank(message="amount is required")
    private Double amount;
    @NotNull(message="currency is required")
    @NotBlank(message="currency is required")
    private String currency;
    @NotNull(message="description is required")
    @NotBlank(message="description is required")
    private String description;
    @NotNull(message="status is required")
    @NotBlank(message="status is required")
    private String status;
    @NotNull(message="withdrawal_transaction_type is required")
    @NotBlank(message="withdrawal_transaction_type is required")
    private Enum<WithdrawalTransactionType> withdrawal_transaction_type;
    @NotNull(message="balance_before is required")
    @NotBlank(message="balance_before is required")
    private long balance_before;
    @NotNull(message="balance_after is required")
    @NotBlank(message="balance_after is required")
    private long balance_after;
    @NotNull(message="created_at is required")
    @NotBlank(message="created_at is required")
    private long created_at;
    @NotNull(message="updated_at is required")
    @NotBlank(message="updated_at is required")
    private long updated_at;
}
