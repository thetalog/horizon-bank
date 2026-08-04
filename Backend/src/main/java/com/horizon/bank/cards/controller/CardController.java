package com.horizon.bank.cards.controller;

import com.horizon.bank.cards.dto.CreateCardRequestDto;
import com.horizon.bank.cards.service.CardService;
import org.springframework.web.bind.annotation.*;

import com.horizon.bank.common.component.ResponseStructure;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    public ResponseStructure responseStructure;
    public CardService cardService;
    public CardController(ResponseStructure responseStructure, CardService cardService ){
        this.responseStructure = responseStructure;
        this.cardService = cardService;
    }
    @PostMapping("/create-card-request")
    ResponseStructure createCardRequest(@RequestBody CreateCardRequestDto requestDto){
        return cardService.createCardRequest(requestDto, responseStructure);
    }
}
