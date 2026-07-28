package com.horizon.bank.auth.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Auth")
public class AuthEntity {
    @Id
    private String id;
    @NotNull(message="Name is required")
    @NotBlank(message="Name is required")
    private String name;
    @NotNull(message="Email is required")
    @NotBlank(message="Email is required")
    @Email(message = "Invalid email")
    @Column(unique=true)
    private String email;
    @NotNull(message="Password is required")
    @NotBlank(message="Password is required")
    private String password;
    @NotNull(message="Gender is required")
    @NotBlank(message="Gender is required")
    private String gender;
    @NotNull(message="Phone Number is required")
    @NotBlank(message="Phone Number is required")
    @JsonAlias("phone_number")
    @Column(name = "phone_number", unique=true)
    private String phone_number;
    @NotNull(message="Address Line is required")
    private String address_line;
    @NotNull(message="City is required")
    @NotBlank(message="City is required")
    private String city;
    @NotNull(message="State is required")
    @NotBlank(message="State is required")
    private String state;
    @NotNull(message="pincode is required")
    private Integer pincode;
    @NotNull(message="Two Factor Enable is required")
    private Boolean two_factor_enable;
    private boolean account_locked;
    private long failed_login_attempts;
    private long last_login_at;
    private long last_password_change_at;
    private long created_at;
    private long updated_at;
    private long created_by;
}
