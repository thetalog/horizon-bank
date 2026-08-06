package com.horizon.bank.cards.controller;

import com.horizon.bank.cards.dto.*;
import com.horizon.bank.cards.service.CardService;
import org.springframework.web.bind.annotation.*;

import com.horizon.bank.common.component.ResponseStructure;

@RestController
@RequestMapping("/card")
public class CardController {
    public ResponseStructure responseStructure;
    public CardService cardService;
    public CardController(ResponseStructure responseStructure, CardService cardService ){
        this.responseStructure = responseStructure;
        this.cardService = cardService;
    }
    @GetMapping("/get-all-card-pending-requests")
    ResponseStructure getAllCardPendingRequests(){
            return cardService.getAllCardRequest(responseStructure);
    }
    @GetMapping("/get-particular-card-pending-requests")
    ResponseStructure getParticularCardPendingRequests(@RequestBody GetCardPendingRequestDto requestDto){
        return cardService.getParticularCardRequest(requestDto, responseStructure);
    }
    @PostMapping("/create-card-request")
    ResponseStructure createCardRequest(@RequestBody CreateCardRequestDto requestDto){
        return cardService.createCardRequest(requestDto, responseStructure);
    }
    @PutMapping("/approval-card-request")
    ResponseStructure approvalCardRequest(@RequestBody ApproveCardRequestDto requestDto){
        return cardService.approveCardRequest(requestDto, responseStructure);
    }
    @PutMapping("/toggle-card-status")
    ResponseStructure toggleCardStatus(@RequestBody ToggleCardStatusDto requestDto){
        return cardService.toggleCardStatus(requestDto, responseStructure);
    }
    @GetMapping("/get-card-details")
    ResponseStructure toggleCardStatus(@RequestBody GetCardDetailsDto requestDto){
        return cardService.getCardDetails(requestDto, responseStructure);
    }
    @PutMapping("/update-card-pin")
    ResponseStructure updateCardPin(@RequestBody UpdateAtmPinDto requestDto){
        return cardService.updateCardPin(requestDto, responseStructure);
    }
}
