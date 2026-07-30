package com.horizon.bank.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {
    private String name;
    private String email;
    private String password;
    private String gender;
    private String phone_number;
    private String address_line;
    private String city;
    private String state;
    private Integer pincode;
}
