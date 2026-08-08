package com.horizon.bank.transaction.refund.dto;

import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import lombok.Getter;

@Getter
public class RefundApprovalDto {
  private String refundId;
  private TransactionStatus status;
}
