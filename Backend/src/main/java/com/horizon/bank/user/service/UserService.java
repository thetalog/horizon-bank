package com.horizon.bank.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.horizon.bank.user.dto.CreateUserRequestDto;
import com.horizon.bank.user.entity.UserEntity;
import com.horizon.bank.user.entity.enums.UserRoles;
import com.horizon.bank.user.repository.UserRepository;

import jakarta.validation.Valid;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    public UserEntity getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public Boolean isCreatorIdValidAndActive(String createdById) {
        if (createdById == null || createdById.isBlank()) {
            return false;
        }

        Optional<UserEntity> creator = userRepository.findById(createdById);
        if (creator.isEmpty()) {
            return false;
        }

        Boolean isValid = creator.get().getIsActive();
        return Boolean.TRUE.equals(isValid);
    }
    public Boolean isUserActive(String id) {
        UserEntity userEntity =  userRepository.findById(id).orElse(null);
        if (userEntity == null || userEntity.getAccountLocked() != null && userEntity.getAccountLocked()) {
            return false;
        }
        return true;
    }
    public Object createUser(@Valid CreateUserRequestDto user) throws Exception {
        try{
        UserEntity existingUserEntity = userRepository.findByEmail(user.getEmail());
        if (existingUserEntity != null) {
            throw new RuntimeException("User with this email already exists");
        }
        List<UserRoles> roles = new ArrayList<>();
        roles.add(UserRoles.USER);

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setId(UUID.randomUUID().toString());
        newUserEntity.setName(user.getName());
        newUserEntity.setEmail(user.getEmail());
        newUserEntity.setPassword(user.getPassword());
        newUserEntity.setGender(user.getGender());
        newUserEntity.setPhoneNumber(user.getPhoneNumber());
        newUserEntity.setCreatedBy(user.getCreatedBy());
        newUserEntity.setRoles(roles);
        newUserEntity.setIsActive(true);
        return userRepository.save(newUserEntity);
        } catch (Exception e) {
            if(e.getMessage().contains("duplicate key value violates unique constraint")) {
                String text = e.getMessage();   
                String result = "";
                int start = text.indexOf("Detail:");
                int end = text.indexOf(".]");
    
                if (start != -1 && end != -1 && start < end) {
                    result = text.substring(start, end);
                }
                return result;
            } else {
                return "User creation failed due to an unexpected error: " + e.getMessage();
            }
        }
    }

    public HashMap<String, String> login(String email, String password) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null || !userEntity.getPassword().equals(password)) {
            throw new RuntimeException("Invalid email or password");
        }
        HashMap<String, String> tokens = jwtService.generateToken(email);
        return tokens;
    }
}
