package com.horizon.bank.transaction.deposit.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horizon.bank.transaction.deposit.dto.DepositTransactionRequestDto;
import com.horizon.bank.transaction.deposit.service.DepositService;
@RestController
@RequestMapping("/transaction/deposit")
public class DepositController {
    public DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/create-deposit-transaction")
    public String createDepositTransaction(@RequestBody DepositTransactionRequestDto requestDto) {
        try{
            depositService.createDepositTransaction(requestDto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create deposit transaction: " + e.getMessage());
        }
        return "Deposit transaction created successfully";
    }
}
