package com.horizon.bank.cards.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToggleCardStatusDto {
    @JsonAlias("employee_id")
    public String employeeId;

    @JsonAlias("card_id")
    public String cardId;
}
