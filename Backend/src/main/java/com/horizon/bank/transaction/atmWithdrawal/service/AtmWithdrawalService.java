package com.horizon.bank.transaction.atmWithdrawal.service;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.accounts.repository.AccountRepository;
import com.horizon.bank.cards.entity.CardEntity;
import com.horizon.bank.cards.enums.CardStatus;
import com.horizon.bank.cards.repository.CardRepository;
import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.transaction.atmWithdrawal.dto.AtmWithdrawalRequestDto;
import com.horizon.bank.transaction.atmWithdrawal.entity.AtmWithdrawalEntity;
import com.horizon.bank.transaction.atmWithdrawal.repository.AtmWithdrawalRepository;
import com.horizon.bank.transaction.debitCardPayment.enums.TransactionStatus;
import com.horizon.bank.user.entity.UserEntity;
import com.horizon.bank.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class AtmWithdrawalService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AtmWithdrawalRepository atmWithdrawalRepository;
    private final ResponseStructure responseStructure;

    public AtmWithdrawalService(
            CardRepository cardRepository,
            AccountRepository accountRepository,
            UserRepository userRepository,
            AtmWithdrawalRepository atmWithdrawalRepository,
            ResponseStructure responseStructure) {

        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.atmWithdrawalRepository = atmWithdrawalRepository;
        this.responseStructure = responseStructure;
    }

    public ResponseStructure withdrawal(AtmWithdrawalRequestDto requestDto) {

        // Check Card
        Optional<CardEntity> card = cardRepository.findByCardNumber(requestDto.getCardNumber());

        if (card.isEmpty()) {
            responseStructure.setStatusCode(404);
            responseStructure.setError(true);
            responseStructure.setMessage("Card not found");
            return responseStructure;
        }

        if (card.get().getStatus() == CardStatus.INACTIVE) {
            responseStructure.setStatusCode(403);
            responseStructure.setError(true);
            responseStructure.setMessage("Card not active");
            return responseStructure;
        }

        // Check Account
        Optional<AccountEntity> account =
                accountRepository.findById(card.get().getAccount().getId());

        if (account.isEmpty()) {
            responseStructure.setStatusCode(404);
            responseStructure.setError(true);
            responseStructure.setMessage("Account not found");
            return responseStructure;
        }

        if (!account.get().getIsActive()) {
            responseStructure.setStatusCode(403);
            responseStructure.setError(true);
            responseStructure.setMessage("Account not active");
            return responseStructure;
        }

        // Check User
        Optional<UserEntity> user =
                userRepository.findById(card.get().getAccount().getUser().getId());

        if (user.isEmpty()) {
            responseStructure.setStatusCode(404);
            responseStructure.setError(true);
            responseStructure.setMessage("User not found");
            return responseStructure;
        }

        if (!user.get().getIsActive()) {
            responseStructure.setStatusCode(403);
            responseStructure.setError(true);
            responseStructure.setMessage("User not active");
            return responseStructure;
        }

        // Check PIN
        if (!requestDto.getPin().equals(card.get().getPin())) {
            responseStructure.setStatusCode(403);
            responseStructure.setError(true);
            responseStructure.setMessage("PIN does not match");
            return responseStructure;
        }

        BigDecimal currentBalance = account.get().getBalance();
        BigDecimal remainingBalance = currentBalance.subtract(requestDto.getAmount());

        AtmWithdrawalEntity transaction = new AtmWithdrawalEntity();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setAccountNumber(account.get().getAccountNumber());
        transaction.setAmount(requestDto.getAmount());
        transaction.setAtmId(requestDto.getAtmId());
        transaction.setCard(card.get());
        transaction.setBalanceBefore(currentBalance);
        transaction.setBalanceAfter(remainingBalance);

        // Insufficient balance
        if (remainingBalance.compareTo(BigDecimal.ZERO) < 0) {

            transaction.setStatus(TransactionStatus.FAILED);
            atmWithdrawalRepository.save(transaction);

            responseStructure.setStatusCode(400);
            responseStructure.setError(true);
            responseStructure.setMessage("Insufficient balance");

            return responseStructure;
        }

        // Update account balance
        account.get().setBalance(remainingBalance);
        accountRepository.save(account.get());

        transaction.setStatus(TransactionStatus.SUCCESS);
        atmWithdrawalRepository.save(transaction);

        responseStructure.setStatusCode(200);
        responseStructure.setError(false);
        responseStructure.setMessage("Withdrawal successful");
        responseStructure.setData(transaction);

        return responseStructure;
    }
}