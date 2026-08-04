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
public class CreateCardRequestDto {
    @JsonAlias("account_number")
    private String accountNumber;

    @JsonAlias("user_id")
    private String userId;

    @JsonAlias("employee_Id")
    private String employeeId;

    @JsonAlias("employee_type")
    private CardType cardType;

    @JsonAlias("card_network")
    private CardNetwork cardNetwork;

    @JsonAlias("is_contact_less")
    private Boolean isContactless;

    @JsonAlias("daily_withdrawal_limit")
    private Double dailyWithdrawalLimit;

    @JsonAlias("daily_purchase_limit")
    private Double dailyPurchaseLimit;
}
