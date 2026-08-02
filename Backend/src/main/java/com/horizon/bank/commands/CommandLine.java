package com.horizon.bank.commands;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.horizon.bank.user.entity.User;
import com.horizon.bank.user.repository.UserRepository;
@Component
public class CommandLine {
    @Bean
    CommandLineRunner seed(UserRepository repo){
        return args -> {
            if (repo.findByEmail("admin@bank.com") == null) {

                User user = new User();

                user.setId(UUID.randomUUID().toString());
                user.setName("Admin");
                user.setEmail("admin@bank.com");
                user.setPassword("123456");
                user.setGender("Male");
                user.setPhone_number("9876543210");
                
                repo.save(user);
            }
        };
    }
}

