package com.horizon.bank.accounts.entity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.horizon.bank.accounts.entity.enums.AccountType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="accounts")
public class Accounts {
    @Id
    private String id;
    private String account_number;
    private AccountType account_type;
    private String ifsc_code;
    private Long balance;

    private Long created_by;

    @CreationTimestamp
    private Long created_at;
    @UpdateTimestamp
    private Long updated_at;
}
