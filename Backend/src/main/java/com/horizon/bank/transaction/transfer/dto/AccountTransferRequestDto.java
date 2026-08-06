package com.horizon.bank.transaction.transfer.dto;

import com.horizon.bank.transaction.transfer.enums.TransferType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountTransferRequestDto {
    public BigDecimal amount;
    public String payerAccountNumber;
    public String payeeAccountNumber;
    public TransferType transferType;
    public String transactionPin;
    public String remarks;
}
