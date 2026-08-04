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
    public HashMap<String, Object> response;

    public void setResponse(int statusCode, String message, Boolean error, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.data = data;
    }
    public HashMap<String, Object> send() {
        response = new HashMap<>();
        response.put("statusCode", statusCode);
        response.put("message", message);
        response.put("error", error);
        response.put("timestamp", timestamp);
        response.put("data", data);
        return response;
    }
}
