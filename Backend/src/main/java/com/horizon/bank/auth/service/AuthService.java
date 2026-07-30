package com.horizon.bank.auth.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.horizon.bank.auth.dto.CreateUserRequestDto;
import com.horizon.bank.auth.entity.User;
import com.horizon.bank.auth.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public User createUser(CreateUserRequestDto user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User with this email already exists");
        }
        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());  
        newUser.setPassword(user.getPassword());
        newUser.setGender(user.getGender());
        newUser.setPhone_number(user.getPhone_number());
        newUser.setAddress_line(user.getAddress_line());
        newUser.setCity(user.getCity());
        newUser.setState(user.getState());
        newUser.setPincode(user.getPincode());
        return userRepository.save(newUser);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid email or password");
        }
        return jwtService.generateToken(email);
        
    }
}
