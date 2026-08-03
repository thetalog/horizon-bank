package com.horizon.bank.transaction.deposit.dto;

import java.math.BigDecimal;

import com.horizon.bank.transaction.deposit.enums.DepositTransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositTransactionRequestDto {
    public String payeeAccountNumber;
    public BigDecimal amount;
    public String currency;
    public DepositTransactionType depositType;
    public String branchCode;
    public String employeeId;
    public String payerAccountNumber;
}
