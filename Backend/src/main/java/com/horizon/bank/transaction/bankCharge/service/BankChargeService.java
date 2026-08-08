package com.horizon.bank.transaction.bankCharge.service;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.accounts.repository.AccountRepository;
import com.horizon.bank.transaction.bankCharge.entity.BankChargeEntity;
import com.horizon.bank.transaction.bankCharge.repository.BankChargeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class BankChargeService {

  private final BankChargeRepository bankChargeRepository;
  private final AccountRepository accountRepository;

  /** Manually charge a particular account. */
  @Transactional
  public BankChargeEntity chargeAccount(String accountNumber) {

    AccountEntity account =
        accountRepository
            .findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

    if (!Boolean.TRUE.equals(account.getIsActive())) {
      throw new RuntimeException("Account is inactive");
    }

    String currentMonth = YearMonth.now().toString();

    // Prevent duplicate monthly charge
    if (bankChargeRepository.existsByAccountAndChargeMonth(account, currentMonth)) {
      throw new RuntimeException("Bank charge already applied for this month");
    }

    // Generate ₹50 - ₹100
    int randomAmount = ThreadLocalRandom.current().nextInt(50, 101);

    BigDecimal chargeAmount = BigDecimal.valueOf(randomAmount);

    // Check balance
    if (account.getBalance() == null || account.getBalance().compareTo(chargeAmount) < 0) {

      throw new RuntimeException("Insufficient balance for bank charge");
    }

    // Deduct charge
    account.setBalance(account.getBalance().subtract(chargeAmount));

    accountRepository.save(account);

    // Create charge record
    BankChargeEntity charge = new BankChargeEntity();

    charge.setAccount(account);
    charge.setAmount(chargeAmount);
    charge.setChargeMonth(currentMonth);

    return bankChargeRepository.save(charge);
  }

  /**
   * Charge all active accounts.
   *
   * <p>Runs automatically at the end of every month.
   */
  @Transactional
  @Scheduled(cron = "0 0 23 L * *")
  public void chargeAllAccounts() {

    String currentMonth = YearMonth.now().toString();

    List<AccountEntity> accounts = accountRepository.findByIsActiveTrue();

    for (AccountEntity account : accounts) {

      // Prevent duplicate charge
      if (bankChargeRepository.existsByAccountAndChargeMonth(account, currentMonth)) {
        continue;
      }

      // Generate ₹50 - ₹100
      int randomAmount = ThreadLocalRandom.current().nextInt(50, 101);

      BigDecimal chargeAmount = BigDecimal.valueOf(randomAmount);

      // Skip accounts without enough balance
      if (account.getBalance() == null || account.getBalance().compareTo(chargeAmount) < 0) {
        continue;
      }

      // Deduct amount
      account.setBalance(account.getBalance().subtract(chargeAmount));

      accountRepository.save(account);

      // Save charge
      BankChargeEntity charge = new BankChargeEntity();

      charge.setAccount(account);
      charge.setAmount(chargeAmount);
      charge.setChargeMonth(currentMonth);

      bankChargeRepository.save(charge);
    }
  }

  /** Get all bank charges. */
  public List<BankChargeEntity> getAllCharges() {

    return bankChargeRepository.findAll();
  }

  /** Get charges of a particular account. */
  public List<BankChargeEntity> getAccountCharges(String accountNumber) {

    AccountEntity account =
        accountRepository
            .findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

    return bankChargeRepository.findByAccount(account);
  }

  /**
   * Get charges for a particular month.
   *
   * <p>Example: 2026-08
   */
  public List<BankChargeEntity> getChargesByMonth(String chargeMonth) {

    return bankChargeRepository.findByChargeMonth(chargeMonth);
  }
}
