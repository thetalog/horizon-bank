package com.horizon.bank.user.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.user.dto.CreateUserRequestDto;
import com.horizon.bank.user.dto.LoginRequestDto;
import com.horizon.bank.user.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService service;
    private final ResponseStructure response;

    public UserController(UserService service, ResponseStructure response){
        this.service = service;
        this.response = response;
    }

    @PostMapping("/create-user")
    public Object registerUser(@Valid @RequestBody CreateUserRequestDto entity) throws Exception {
        Object result = service.createUser(entity);
        if(result instanceof String) {
            response.setResponse(403, (String) result, null);
            return response.send();
        }
        HashMap<String, Object> data = new HashMap<>();
        response.setResponse(200, "User created successfully", data);
        return response.send();
    }
    @PostMapping("/login")
    public HashMap<String, Object> loginUser(@Valid @RequestBody LoginRequestDto entity) {
        try {
            String email = entity.getEmail();
            String password = entity.getPassword();
            HashMap<String, String> tokens = service.login(email, password);
            HashMap<String, Object> data = new HashMap<>();
            data.put("email", email);
            data.put("tokens", tokens);
            response.setResponse(200, "Login successful",  data );
            return response.send();
            
        } catch (Exception e) {
            response.setResponse(403, "Invalid credentials!", null);
            return response.send();
        }
    }
}
