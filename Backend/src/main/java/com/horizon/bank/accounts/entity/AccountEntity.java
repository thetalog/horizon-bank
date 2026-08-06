package com.horizon.bank.accounts.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.horizon.bank.cards.entity.CardEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.horizon.bank.accounts.entity.enums.AccountType;
import com.horizon.bank.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    List<CardEntity> cards;
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
    private UserEntity user;

    @CreationTimestamp
    @Column(name = "created_at")
    private Long createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Long updatedAt;
}