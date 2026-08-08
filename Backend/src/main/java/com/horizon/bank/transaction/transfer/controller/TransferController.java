package com.horizon.bank.transaction.transfer.controller;

import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.transaction.transfer.dto.AccountTransferRequestDto;
import com.horizon.bank.transaction.transfer.dto.GetAllTransferTransactionsDto;
import com.horizon.bank.transaction.transfer.dto.GetParticularTransferTransactionDto;
import com.horizon.bank.transaction.transfer.service.TransferService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account-transfer")
public class TransferController {
  ResponseStructure responseStructure;
  TransferService transferService;

  TransferController(ResponseStructure responseStructure, TransferService transferService) {
    this.responseStructure = responseStructure;
    this.transferService = transferService;
  }

  @PostMapping("/transfer")
  public ResponseStructure transfer(@RequestBody AccountTransferRequestDto requestDto) {
    return transferService.transfer(requestDto, responseStructure);
  }

  @GetMapping("/get-all-tranfer-transactions")
  public ResponseStructure getAllTransferTransactions(
      @RequestBody GetAllTransferTransactionsDto requestDto) {
    return transferService.getAllTransferTransactions(requestDto, responseStructure);
  }

  @GetMapping("/get-particular-tranfer-transaction")
  public ResponseStructure getParticularTransferTransaction(
      @RequestBody GetParticularTransferTransactionDto requestDto) {
    return transferService.getParticularTransferTransaction(requestDto, responseStructure);
  }
}
