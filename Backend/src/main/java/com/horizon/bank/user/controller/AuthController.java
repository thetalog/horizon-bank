package com.horizon.bank.user.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.user.dto.CreateUserRequestDto;
import com.horizon.bank.user.dto.LoginRequestDto;
import com.horizon.bank.user.service.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;
    private final ResponseStructure response;

    public AuthController(AuthService service, ResponseStructure response){
        this.service = service;
        this.response = response;
    }

    @PostMapping("/create-user")
    public Object registerUser(@Valid @RequestBody CreateUserRequestDto entity){
        log.info("Creating user with email: {}", entity.getEmail());
        service.createUser(entity);
        return "User created successfully";
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
