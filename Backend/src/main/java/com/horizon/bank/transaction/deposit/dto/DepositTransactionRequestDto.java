package com.horizon.bank.transaction.deposit.dto;

import java.math.BigDecimal;

import com.horizon.bank.transaction.deposit.enums.DepositTransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositTransactionRequestDto {
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private DepositTransactionType depositType;
    private String branchCode;
    private String depositedBy;

}
