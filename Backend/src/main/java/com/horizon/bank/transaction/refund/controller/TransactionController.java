package com.horizon.bank.transaction.refund.controller;

import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.transaction.refund.dto.RefundApprovalDto;
import com.horizon.bank.transaction.refund.dto.RefundRequestDto;
import com.horizon.bank.transaction.refund.service.RefundService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refunds")
public class TransactionController {
  ResponseStructure responseStructure;
  RefundService refundService;

  TransactionController(ResponseStructure responseStructure, RefundService refundService) {
    this.responseStructure = responseStructure;
    this.refundService = refundService;
  }

  @PostMapping("/request")
  public ResponseStructure requestRefund(@RequestBody RefundRequestDto dto) {
    return refundService.requestRefund(dto);
  }

  @PutMapping("/approval")
  public ResponseStructure approveRefund(@RequestBody RefundApprovalDto dto) {
    return refundService.approveRefund(dto);
  }

  @GetMapping
  public ResponseStructure getAllRefunds() {
    return refundService.getAllRefundRequests();
  }

  @GetMapping("/{id}")
  public ResponseStructure getRefund(@PathVariable String id) {
    return refundService.getRefundRequest(id);
  }
}
