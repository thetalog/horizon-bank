package com.horizon.bank.transaction.atmWithdrawal.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.transaction.atmWithdrawal.enums.WithdrawalTransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "savings_withdrawals")
public class WithdrawalEntity {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @NotNull(message = "Account is required")
    private AccountEntity account;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotNull(message = "Currency is required")
    private String currency;

    private String description;

    @NotNull(message = "Status is required")
    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "withdrawal_transaction_type")
    private WithdrawalTransactionType withdrawalTransactionType;

    @Column(name = "balance_before")
    private Long balanceBefore;

    @Column(name = "balance_after")
    private Long balanceAfter;

    @CreationTimestamp
    @Column(name = "created_at")
    private Long createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Long updatedAt;
}