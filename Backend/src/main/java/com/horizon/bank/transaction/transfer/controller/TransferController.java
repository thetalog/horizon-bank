package com.horizon.bank.transaction.transfer.controller;

import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.transaction.transfer.dto.AccountTransferRequestDto;
import com.horizon.bank.transaction.transfer.service.TransferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account-transfer")
public class TransferController {
    ResponseStructure responseStructure;
    TransferService transferService;
    TransferController(ResponseStructure responseStructure, TransferService transferService){
        this.responseStructure = responseStructure;
        this.transferService = transferService;
    }
    @PostMapping("/transfer")
    public ResponseStructure transfer(@RequestBody AccountTransferRequestDto requestDto){
        return transferService.transfer(requestDto, responseStructure);
    }
}
