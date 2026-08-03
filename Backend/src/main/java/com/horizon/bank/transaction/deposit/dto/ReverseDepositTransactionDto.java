package com.horizon.bank.transaction.deposit.dto;

import com.horizon.bank.transaction.deposit.enums.DepositTransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;

@Getter
@Setter
public class ReverseDepositTransactionDto {
    private String accountNumber;
    private String branchCode;
    private String employeeId;
    private String originalTransactionReferenceNumber;
    private String refundReason;
}
