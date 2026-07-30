package com.horizon.bank.user.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public AuthController(AuthService service){
        this.service = service;
    }

    @PostMapping("/create-user")
    public Object registerUser(@Valid @RequestBody CreateUserRequestDto entity){
        log.info("Creating user with email: {}", entity.getEmail());
        service.createUser(entity);
        return "User created successfully";
    }
    @PostMapping("/login")
    public HashMap<String, String> loginUser(@Valid @RequestBody LoginRequestDto entity) {
        String email = entity.getEmail();
        String password = entity.getPassword();
        String jwt = service.login(email, password);
        HashMap<String, String> response = new HashMap<>();
        response.put("email", email);
        response.put("jwt", jwt);
        return response;
    }
    
}
