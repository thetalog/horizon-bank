package com.horizon.bank.user.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {

    private String name;
    private String email;
    private String password;
    private String gender;

    @JsonAlias("phone_number")
    private String phoneNumber;

    @JsonAlias("address_line")
    private String addressLine;

    private String city;

    private String state;

    private Integer pincode;

    @JsonAlias("created_by")
    private String createdBy;
}