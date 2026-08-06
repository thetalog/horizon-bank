package com.horizon.bank.cards.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCardDetailsDto {
    @JsonAlias("card_number")
    private String cardNumber;
}
