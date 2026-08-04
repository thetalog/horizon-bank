package com.horizon.bank.user.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.horizon.bank.cards.entity.CardEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.user.entity.enums.UserRoles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import com.horizon.bank.user.entity.Kyc;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Column(unique = true)
    private String email;

    private String password;

    @NotNull(message = "Gender is required")
    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Phone Number is required")
    @NotBlank(message = "Phone Number is required")
    @JsonAlias("phone_number")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "two_factor_enable")
    private Boolean twoFactorEnable;

    @Column(name = "account_locked")
    private Boolean accountLocked;

    @Column(name = "failed_login_attempts")
    private Long failedLoginAttempts;

    @Column(name = "last_login_at")
    private Long lastLoginAt;

    @Column(name = "last_password_change_at")
    private Long lastPasswordChangeAt;

    @Column(name = "is_active")
    private Boolean isActive;

    @ElementCollection
    private List<UserRoles> roles;

    @Column(name = "profile_picture")
    private String profilePicture;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<AccountEntity> accounts;

    @OneToMany(mappedBy = "approved_by")
    @JsonManagedReference
    private List<CardEntity> cardApprovals;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kyc_id")
    private Kyc kyc;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private Long createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Long updatedAt;
}