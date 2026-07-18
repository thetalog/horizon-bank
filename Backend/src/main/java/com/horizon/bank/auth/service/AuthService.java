package com.horizon.bank.auth.service;

import org.springframework.stereotype.Service;

import com.horizon.bank.auth.entity.AuthEntity;
import com.horizon.bank.auth.repository.AuthRepository;

@Service
public class AuthService {

    private final AuthRepository repository;

    public AuthService(AuthRepository repository){
        this.repository = repository;
    }

    public AuthEntity save(AuthEntity entity){
        return repository.save(entity);
    }

    public AuthEntity delete(String id){
        AuthEntity entity = repository.findById(id).orElse(null);
        if (entity != null) {
            repository.delete(entity);
        }
        return entity;
    }
}
