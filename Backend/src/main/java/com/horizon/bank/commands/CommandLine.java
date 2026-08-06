package com.horizon.bank.commands;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.horizon.bank.user.entity.enums.UserRoles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.horizon.bank.user.entity.UserEntity;
import com.horizon.bank.user.repository.UserRepository;
@Component
public class CommandLine {
    @Bean
    CommandLineRunner seed(UserRepository repo){
        return args -> {
            if (repo.findByEmail("admin@bank.com") == null) {

                UserEntity userEntity = new UserEntity();

                userEntity.setId(UUID.randomUUID().toString());
                userEntity.setName("Admin");
                userEntity.setEmail("admin@bank.com");
                userEntity.setPassword("123456");
                userEntity.setGender("Male");
                userEntity.setPhoneNumber("9876543210");
                userEntity.setIsActive(true);
                List<UserRoles> userRoles = new ArrayList<>();
                userRoles.add(UserRoles.ADMIN);
                userEntity.setRoles(userRoles);
                repo.save(userEntity);
            }
        };
    }
}

