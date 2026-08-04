package com.horizon.bank.cards.repository;

import java.util.List;
import java.util.Optional;

import com.horizon.bank.cards.entity.CardEntity;
import com.horizon.bank.cards.enums.CardStatus;
import com.horizon.bank.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, String> {
    Optional<CardEntity> findByCardNumber(String cardNumber);

    List<CardEntity> getAllByStatus(CardStatus cardStatus);
    CardEntity getByStatusAndUserId(CardStatus cardStatus, String userId);
}
