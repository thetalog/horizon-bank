package com.horizon.bank.transaction.atmWithdrawal.controller;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.accounts.repository.AccountRepository;
import com.horizon.bank.cards.entity.CardEntity;
import com.horizon.bank.cards.enums.CardStatus;
import com.horizon.bank.cards.repository.CardRepository;
import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.transaction.atmWithdrawal.dto.AtmWithdrawalRequestDto;
import com.horizon.bank.transaction.atmWithdrawal.entity.AtmWithdrawalEntity;
import com.horizon.bank.transaction.atmWithdrawal.repository.AtmWithdrawalRepository;
import com.horizon.bank.transaction.atmWithdrawal.service.AtmWithdrawalService;
import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.user.entity.UserEntity;
import com.horizon.bank.user.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/atm-withdrawal")
public class AtmWithdrawalController {
    AtmWithdrawalService atmWithdrawalService;
    ResponseStructure responseStructure;
    CardRepository cardRepository;
    AtmWithdrawalController(ResponseStructure responseStructure, AtmWithdrawalService atmWithdrawalService){
        this.responseStructure = responseStructure;
        this.atmWithdrawalService = atmWithdrawalService;
    }
    @PostMapping("/withdrawal")
    public ResponseStructure withdrawal(@RequestBody AtmWithdrawalRequestDto requestDto){
            return atmWithdrawalService.withdrawal(requestDto);
    }
}
