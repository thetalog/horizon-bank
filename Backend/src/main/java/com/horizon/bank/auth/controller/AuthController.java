package com.horizon.bank.auth.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horizon.bank.auth.entity.AuthEntity;
import com.horizon.bank.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service){
        this.service = service;
    }

    @PostMapping("/create-user")
    public Object registerUser(@Valid @RequestBody AuthEntity data){
        return service.save(data);
    }

    @DeleteMapping("/delete-user")
    public Object deleteUser(@Valid @RequestBody AuthEntity data){
        return service.delete(data.getId());        
    }
}
