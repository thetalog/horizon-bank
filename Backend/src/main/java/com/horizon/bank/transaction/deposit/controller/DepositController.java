package com.horizon.bank.transaction.deposit.controller;

import com.horizon.bank.transaction.deposit.dto.ReverseDepositTransactionDto;
import com.horizon.bank.transaction.deposit.entity.DepositEntity;
import com.horizon.bank.transaction.deposit.enums.DepositTransactionType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horizon.bank.transaction.deposit.dto.DepositTransactionRequestDto;
import com.horizon.bank.transaction.deposit.service.DepositService;
import com.horizon.bank.common.component.ResponseStructure;

import java.util.HashMap;

@RestController
@RequestMapping("/transaction/deposit")
public class DepositController {
    public DepositService depositService;
    public ResponseStructure responseStructure;
    public DepositController(DepositService depositService, ResponseStructure responseStructure) {
        this.depositService = depositService;
        this.responseStructure = responseStructure;
    }

    @PostMapping("/create-deposit-transaction-non-cash")
    public HashMap<String, Object> createDepositTransactionNonCash(@RequestBody DepositTransactionRequestDto requestDto) {
        try{
            if(requestDto.getDepositType() == DepositTransactionType.CASH){
                responseStructure.setResponse(403, "Support Non Cash Deposit only", null);
                return responseStructure.send();
            }
           DepositEntity depositTransactionResponse = depositService.createDepositTransactionNonCash(requestDto);
            responseStructure.setResponse(200, "Deposit transaction created successfully", depositTransactionResponse);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create deposit transaction: " + e.getMessage());
        }
        return responseStructure.send();
    }

    @PutMapping("/reverse-deposit-transaction")
    public HashMap<String, Object> reverseDepositTransaction(@RequestBody ReverseDepositTransactionDto requestDto) {
        try{
            Object reverseDepositTransactionResponse = depositService.reverseDepositTransaction(requestDto);
            responseStructure.setResponse(200, "Deposit transaction reversed successfully", reverseDepositTransactionResponse);
        } catch (Exception e) {
            responseStructure.setResponse(200, "Deposit transaction reversed failed", e.getMessage());
        }
        return responseStructure.send();
    }

//    @PutMapping("/reverse-deposit-transaction")
//    public HashMap<String, Object> refundDepositTransaction(@RequestBody ReverseDepositTransactionDto requestDto) {
//        try{
//            DepositEntity reverseDepositTransactionResponse = depositService.reverseDepositTransaction(requestDto);
//            responseStructure.setResponse(200, "Deposit transaction reversed successfully", reverseDepositTransactionResponse);
//        } catch (Exception e) {
//            responseStructure.setResponse(200, "Deposit transaction reversed failed", e.getMessage());
//        }
//        return responseStructure.send();
//    }
}
