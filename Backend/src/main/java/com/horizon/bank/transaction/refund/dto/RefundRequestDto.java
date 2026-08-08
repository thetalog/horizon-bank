package com.horizon.bank.transaction.refund.dto;

import com.horizon.bank.transaction.refund.enums.RefundType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RefundRequestDto {
  private String originalTransactionReferenceNumber;
  private BigDecimal amount;
  private RefundType refundType;
  private String reason;
}
