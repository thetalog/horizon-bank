package com.horizon.bank.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horizon.bank.user.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

    User findByEmail(String email);
}
