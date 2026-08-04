package com.horizon.bank.cards.dto;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

import com.horizon.bank.cards.enums.CardNetwork;
import com.horizon.bank.cards.enums.CardType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalCardRequestDto {
    @JsonAlias("employee_Id")
    private String employeeId;

    @JsonAlias("card_request_id")
    private String cardRequestId;

    @JsonAlias("approval")
    private Boolean approval;
}
