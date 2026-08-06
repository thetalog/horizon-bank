package com.horizon.bank.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horizon.bank.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{
    UserEntity findByEmail(String email);
}
