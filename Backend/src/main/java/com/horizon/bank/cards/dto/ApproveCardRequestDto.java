package com.horizon.bank.cards.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveCardRequestDto {
    @JsonAlias("employee_id")
    public String employeeId;

    @JsonAlias("card_request_id")
    public String cardRequestId;

    @JsonAlias("is_approved")
    public Boolean isApproved;
}
