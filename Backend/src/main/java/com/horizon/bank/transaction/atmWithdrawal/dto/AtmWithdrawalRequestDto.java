package com.horizon.bank.transaction.atmWithdrawal.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AtmWithdrawalRequestDto {
    private BigDecimal amount;
    private String cardNumber;
    private String atmId;
    private String pin;
    private String employeeId;
}
