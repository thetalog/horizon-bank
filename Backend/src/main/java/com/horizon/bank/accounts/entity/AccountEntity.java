package com.horizon.bank.accounts.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.horizon.bank.accounts.entity.enums.AccountType;
import com.horizon.bank.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "account_number")
    private String accountNumber;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "created_at")
    private Long createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Long updatedAt;
}