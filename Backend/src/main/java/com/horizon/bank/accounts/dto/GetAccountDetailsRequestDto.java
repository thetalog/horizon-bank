package com.horizon.bank.accounts.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAccountDetailsRequestDto {
    @JsonAlias("account_number")
    private String accountNumber;

    @JsonAlias("user_id")
    private String userId;

    @JsonAlias("employee_id")
    private String employeeId;
}
