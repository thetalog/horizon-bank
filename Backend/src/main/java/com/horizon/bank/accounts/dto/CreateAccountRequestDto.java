package com.horizon.bank.accounts.dto;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.horizon.bank.accounts.entity.enums.AccountType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequestDto {
    @JsonAlias("account_number")
    private String accountNumber;

    @JsonAlias("account_type")
    private AccountType accountType;

    @JsonAlias("ifsc_code")
    private String ifscCode;

    private BigDecimal balance;

    @JsonAlias("branch_name")
    private String branchName;

    @JsonAlias("branch_code")
    private String branchCode;

    @JsonAlias("employee_Id")
    private String employeeId;

    @JsonAlias("user_id")
    private String userId;
}
