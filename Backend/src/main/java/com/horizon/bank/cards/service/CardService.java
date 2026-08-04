package com.horizon.bank.cards.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.accounts.service.AccountService;
import com.horizon.bank.cards.dto.CreateCardRequestDto;
import com.horizon.bank.cards.dto.GetCardRequestDto;
import com.horizon.bank.cards.entity.CardEntity;
import com.horizon.bank.cards.enums.ApprovalStatus;
import com.horizon.bank.cards.enums.CardStatus;
import com.horizon.bank.cards.enums.CardType;
import com.horizon.bank.cards.repository.CardRepository;
import com.horizon.bank.common.component.ResponseStructure;
import org.springframework.stereotype.Service;

import com.horizon.bank.user.controller.GlobalExceptionHandler;
import com.horizon.bank.user.entity.User;
import com.horizon.bank.user.service.UserService;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CardService {
    UserService userService;
    AccountService accountService;
    CardRepository cardRepository;

    public CardService(UserService userService, AccountService accountService, CardRepository cardRepository){
        this.userService = userService;
        this.accountService = accountService;
        this.cardRepository = cardRepository;
    }
    public ResponseStructure getAllCardRequest(ResponseStructure responseStructure){
        List<CardEntity> cards = cardRepository.getAllByStatus(CardStatus.PENDING);
        if(cards.isEmpty()){
            responseStructure.setStatusCode(200);
            responseStructure.setMessage("No card with pending status found");
            responseStructure.setError(false);
            responseStructure.setData(null);
        }
        responseStructure.setStatusCode(200);
        responseStructure.setMessage("Found card pending lists");
        responseStructure.setError(false);
        responseStructure.setData(cards);
        return responseStructure;
    }
    public ResponseStructure getParticularCardRequest(ResponseStructure responseStructure, GetCardRequestDto requestDto){
        CardEntity cards = cardRepository.getByStatusAndUserId(CardStatus.PENDING, requestDto.getUserId());
        if(cards == null){
            responseStructure.setStatusCode(200);
            responseStructure.setMessage("No card with pending status found");
            responseStructure.setError(false);
            responseStructure.setData(null);
        }
        responseStructure.setStatusCode(200);
        responseStructure.setMessage("Found card of the user");
        responseStructure.setError(false);
        responseStructure.setData(cards);
        return responseStructure;
    }
    public String generateCardNumber() {
        String cardNumber;
        do {
            cardNumber = String.valueOf((long) (Math.random() * 1_000_000_000_000L));
        } while (cardNumber.length() < 12 || cardRepository.findByCardNumber(cardNumber).isPresent());
        return cardNumber;
    }

    public ResponseStructure createCardRequest(CreateCardRequestDto requestDto, ResponseStructure responseStructure){
        try{
            //user active
            User user = userService.getUserById(requestDto.getUserId());
            //account active
            AccountEntity account = accountService.getAccountDetails(requestDto.getAccountNumber());
            //account and user should match
            if(user.getId().equals(account.getUser().getId())){
                responseStructure.setError(true);
                responseStructure.setMessage("User and Account does not match");
                responseStructure.setData(null);
                return responseStructure;
            }
            //save request
            CardEntity cardEntity = new CardEntity();
            cardEntity.setId(UUID.randomUUID().toString());
            cardEntity.setAccount(account);
            cardEntity.setApprovalStatus(ApprovalStatus.PENDING);
            cardEntity.setCardHolderName(user.getName());
            cardEntity.setCardNetwork(requestDto.getCardNetwork());
            cardEntity.setCardNumber(this.generateCardNumber());
            cardEntity.setCvv(ThreadLocalRandom.current().nextInt(100, 999));
            cardEntity.setCardType(requestDto.getCardType());
            cardEntity.setDailyPurchaseLimit(requestDto.getDailyPurchaseLimit());
            cardEntity.setDailyWithdrawalLimit(requestDto.getDailyWithdrawalLimit());
            cardEntity.setIsContactless(requestDto.getIsContactless());
            cardEntity.setExpiryMonth(ThreadLocalRandom.current().nextInt(0, 11));
            cardEntity.setExpiryYear(ThreadLocalRandom.current().nextInt(new Date().getYear(), new Date().getYear() + 20));
            cardEntity.setPin("");
            cardEntity.setStatus(CardStatus.PENDING);
            cardRepository.save(cardEntity);
            responseStructure.setData(cardEntity);
            responseStructure.setError(false);
            responseStructure.setStatusCode(201);
            responseStructure.setMessage("Card Request saved");
        }
        catch (Exception e) {
            responseStructure.setData(null);
            responseStructure.setError(true);
            responseStructure.setStatusCode(500);
            responseStructure.setMessage("Card Request failed");
        }
        return responseStructure;
    }
}
