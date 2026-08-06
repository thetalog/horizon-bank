package com.horizon.bank.common.component;

import java.sql.Timestamp;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ResponseStructure {
    public int statusCode;
    public String message;
    public Boolean error;
    public Timestamp timestamp;
    public Object data;

    public void setResponse(int statusCode, String message, Boolean error, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.data = data;
    }
}
