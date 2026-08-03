package com.horizon.bank.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.horizon.bank.user.dto.CreateUserRequestDto;
import com.horizon.bank.user.entity.User;
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
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public Boolean isCreatorIdValidAndActive(String createdById) {
        if (createdById == null || createdById.isBlank()) {
            return false;
        }

        Optional<User> creator = userRepository.findById(createdById);
        if (creator.isEmpty()) {
            return false;
        }

        Boolean isValid = creator.get().getIsActive();
        return Boolean.TRUE.equals(isValid);
    }
    public Boolean isUserActive(String id) {
        User user =  userRepository.findById(id).orElse(null);
        if (user == null || user.getAccountLocked() != null && user.getAccountLocked()) {
            return false;
        }
        return true;
    }
    public Object createUser(@Valid CreateUserRequestDto user) throws Exception {
        try{
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User with this email already exists");
        }
        List<UserRoles> roles = new ArrayList<>();
        roles.add(UserRoles.USER);

        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());  
        newUser.setPassword(user.getPassword());
        newUser.setGender(user.getGender());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setCreatedBy(user.getCreatedBy());
        newUser.setRoles(roles);
        return userRepository.save(newUser);
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
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid email or password");
        }
        HashMap<String, String> tokens = jwtService.generateToken(email);
        return tokens;
    }
}
