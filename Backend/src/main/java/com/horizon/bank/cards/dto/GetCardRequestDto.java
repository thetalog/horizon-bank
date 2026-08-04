package com.horizon.bank.cards.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCardRequestDto {
    @JsonAlias("user_id")
    private String userId;
}
