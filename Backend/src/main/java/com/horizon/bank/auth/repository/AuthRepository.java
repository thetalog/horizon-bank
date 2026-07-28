package com.horizon.bank.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horizon.bank.auth.entity.AuthEntity;

public interface AuthRepository extends JpaRepository<AuthEntity, String>{
}
