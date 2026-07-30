package com.horizon.bank.auth.dto;

import java.util.HashMap;
import java.util.List;
public class CreateUserResponseDto {
    public String message;
    public String status;
    public String httpCode;
    public List<HashMap<String, Object>> data;
}
