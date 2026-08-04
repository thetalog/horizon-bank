package com.horizon.bank.transaction.atmWithdrawal.controller;

import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.transaction.atmWithdrawal.dto.AtmWithdrawalRequestDto;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atm-withdrawal")
public class AtmWithdrawalController {

    @PostMapping("withdrawal")
    public ResponseStructure withdrawal(@RequestBody AtmWithdrawalRequestDto request){

    }
}
