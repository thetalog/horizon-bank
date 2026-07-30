package com.horizon.bank.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horizon.bank.auth.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

    User findByEmail(String email);
}
