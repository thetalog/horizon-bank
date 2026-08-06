package com.horizon.bank.cards.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.cards.enums.ApprovalStatus;
import com.horizon.bank.cards.enums.CardNetwork;
import com.horizon.bank.cards.enums.CardStatus;
import com.horizon.bank.cards.enums.CardType;
import com.horizon.bank.transaction.atmWithdrawal.entity.AtmWithdrawalEntity;
import com.horizon.bank.user.entity.UserEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class CardEntity {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "account_number")
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus;

    @JoinColumn(name = "approved_by")
    @ManyToOne
    @JsonBackReference
    private UserEntity user;

    @OneToMany(mappedBy = "card")
    @JsonManagedReference
    private List<AtmWithdrawalEntity> atmWithdrawal;

    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_network")
    private CardNetwork cardNetwork;

    @Column(name = "cvv")
    private int cvv;

    @Column(name = "pin")
    private String pin;

    @Column(name = "expiry_month")
    private Integer expiryMonth;

    @Column(name = "expiry_year")
    private Integer expiryYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CardStatus status;

    @Column(name = "is_contactless")
    private Boolean isContactless;

    @Column(name = "daily_withdrawal_limit")
    private Double dailyWithdrawalLimit;

    @Column(name = "daily_purchase_limit")
    private Double dailyPurchaseLimit;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}