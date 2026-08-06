package com.horizon.bank.cards.service;

import java.util.*;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.accounts.service.AccountService;
import com.horizon.bank.cards.dto.*;
import com.horizon.bank.cards.entity.CardEntity;
import com.horizon.bank.cards.enums.ApprovalStatus;
import com.horizon.bank.cards.enums.CardStatus;
import com.horizon.bank.cards.repository.CardRepository;
import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.user.entity.enums.UserRoles;
import org.springframework.stereotype.Service;

import com.horizon.bank.user.entity.UserEntity;
import com.horizon.bank.user.service.UserService;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.net.InetAddress;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    public ZonedDateTime getNTPTimeAndDate() {
        try {
            NTPUDPClient client = new NTPUDPClient();
            client.setDefaultTimeout(5000);

            InetAddress host = InetAddress.getByName("pool.ntp.org");
            TimeInfo timeInfo = client.getTime(host);

            long networkTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();

            ZonedDateTime dateTime = Instant.ofEpochMilli(networkTime)
                    .atZone(ZoneId.systemDefault());

            client.close();
            return dateTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
    public ResponseStructure getCardDetails(GetCardDetailsDto requestDto, ResponseStructure responseStructure){
        CardEntity card = cardRepository.getByCardNumber(requestDto.getCardNumber());
        if(card == null){
            responseStructure.setError(true);
            responseStructure.setStatusCode(404);
            responseStructure.setMessage("Card not found!");
        }
        responseStructure.setError(false);
        responseStructure.setStatusCode(200);
        responseStructure.setMessage("Card found!");
        responseStructure.setData(card);
        return responseStructure;
    }
    public ResponseStructure getParticularCardRequest(GetCardPendingRequestDto requestDto, ResponseStructure responseStructure){
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
    public ResponseStructure approveCardRequest(ApproveCardRequestDto requestDto, ResponseStructure responseStructure){
        // Check Employee has admin role
        UserEntity employee = userService.getUserById(requestDto.getEmployeeId());
        if(!employee.getRoles().contains(UserRoles.ADMIN)){
            responseStructure.setMessage("Employee is not admin");
            responseStructure.setError(true);
        }
        //Get Card Request
        Optional<CardEntity> card = cardRepository.findById(requestDto.getCardRequestId());
        //Check Account active
        AccountEntity account = accountService.getAccountDetails(card.get().getAccount().getAccountNumber());
        if(!Boolean.TRUE.equals(account.getIsActive())){
            responseStructure.setMessage("Account is not active");
            responseStructure.setError(true);
        }
        //Check User Active
        UserEntity userEntity = userService.getUserById(card.get().getAccount().getUser().getId());
        if(!Boolean.TRUE.equals(userEntity.getIsActive())){
            responseStructure.setMessage("User is not active");
            responseStructure.setError(true);
        }
        //Approve or deny
        ApprovalStatus approval = requestDto.isApproved ? ApprovalStatus.APPROVED : ApprovalStatus.REJECTED;
        card.get().setApprovalStatus(approval);
        card.get().setStatus(CardStatus.ACTIVE);
        cardRepository.save(card.get());
        responseStructure.setMessage("Card " + approval);
        responseStructure.setError(false);
        responseStructure.setData(card.get());
        return responseStructure;
    }
    public ResponseStructure createCardRequest(CreateCardRequestDto requestDto, ResponseStructure responseStructure){
        try{
            //user active
            UserEntity userEntity = userService.getUserById(requestDto.getUserId());
            //account active
            AccountEntity account = accountService.getAccountDetails(requestDto.getAccountNumber());
            //account and user should match
            if(userEntity.getId().equals(account.getUser().getId())){
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
            cardEntity.setCardHolderName(userEntity.getName());
            cardEntity.setCardNetwork(requestDto.getCardNetwork());
            cardEntity.setCardNumber(this.generateCardNumber());
            cardEntity.setCvv(ThreadLocalRandom.current().nextInt(100, 999));
            cardEntity.setCardType(requestDto.getCardType());
            cardEntity.setDailyPurchaseLimit(requestDto.getDailyPurchaseLimit());
            cardEntity.setDailyWithdrawalLimit(requestDto.getDailyWithdrawalLimit());
            cardEntity.setIsContactless(requestDto.getIsContactless());
            cardEntity.setExpiryMonth(ThreadLocalRandom.current().nextInt(0, 11));
            cardEntity.setExpiryYear(ThreadLocalRandom.current().nextInt(this.getNTPTimeAndDate().getYear(), this.getNTPTimeAndDate().getYear() + 10));
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
    public ResponseStructure toggleCardStatus(ToggleCardStatusDto requestDto, ResponseStructure responseStructure){
        // Check Employee has admin role
        UserEntity employee = userService.getUserById(requestDto.getEmployeeId());
        if(!employee.getRoles().contains(UserRoles.ADMIN)){
            responseStructure.setMessage("Employee is not admin");
            responseStructure.setError(true);
        }
        //Get Card Request
        Optional<CardEntity> card = cardRepository.findById(requestDto.getCardId());
        CardStatus toggleStatus;
        if(card.get().getStatus() == CardStatus.ACTIVE){
            toggleStatus = CardStatus.INACTIVE;
        } else if(card.get().getStatus() == CardStatus.INACTIVE){
            toggleStatus = CardStatus.ACTIVE;
        } else{
            responseStructure.setError(true);
            responseStructure.setMessage("Card is already " + card.get().getStatus());
            return responseStructure;
        }
        card.get().setStatus(toggleStatus);
        cardRepository.save(card.get());
        responseStructure.setData(card.get());
        responseStructure.setError(false);
        responseStructure.setMessage("Card toggled to: " + toggleStatus);
        responseStructure.setStatusCode(201);
        return responseStructure;
    }
    public ResponseStructure updateCardPin(UpdateAtmPinDto requestDto, ResponseStructure responseStructure){

        //Get Card Request
        Optional<CardEntity> card = cardRepository.findByCardNumber(requestDto.getCardNumber());
        if(card.isEmpty()){
            responseStructure.setStatusCode(403);
            responseStructure.setError(true);
            responseStructure.setMessage("card not found");
            responseStructure.setData(null);
            return responseStructure;
        }
        if(requestDto.getPin().length() > 6){
            responseStructure.setStatusCode(403);
            responseStructure.setError(true);
            responseStructure.setMessage("Pin needs to be valid");
            responseStructure.setData(null);
            return responseStructure;
        }
        card.get().setPin(requestDto.getPin());
        cardRepository.save(card.get());
        responseStructure.setStatusCode(201);
        responseStructure.setError(false);
        responseStructure.setMessage("pin update  successful");
        responseStructure.setData(null);
        return responseStructure;
    }
}
