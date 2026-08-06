package com.horizon.bank.cards.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAtmPinDto {
    private String cardNumber;
    private String pin;
}
