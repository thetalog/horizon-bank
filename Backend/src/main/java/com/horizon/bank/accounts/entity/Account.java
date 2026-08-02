package com.horizon.bank.accounts.entity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.horizon.bank.accounts.entity.enums.AccountType;
import com.horizon.bank.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="accounts")
public class Account {
    @Id
    private String id;
    private String account_number;
    private AccountType account_type;
    private String ifsc_code;
    private Long balance;
    private String branch_name;
    private Long created_by;
    private Boolean is_active;
    @ManyToOne(cascade={jakarta.persistence.CascadeType.ALL})
    @JoinColumn(name="user_id")
    private User user;
    @CreationTimestamp
    private Long created_at;
    @UpdateTimestamp
    private Long updated_at;
}
