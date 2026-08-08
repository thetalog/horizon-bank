package com.horizon.bank.transaction.interestCredit.service;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.accounts.repository.AccountRepository;
import com.horizon.bank.transaction.interestCredit.entity.InterestCreditEntity;
import com.horizon.bank.transaction.interestCredit.enums.InterestCreditType;
import com.horizon.bank.transaction.interestCredit.repository.InterestCreditRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestCreditService {

  /*
   * Monthly interest rate.
   *
   * 0.50 means 0.50%
   */
  private static final BigDecimal SAVINGS_INTEREST_RATE = new BigDecimal("0.50");
  private final InterestCreditRepository interestCreditRepository;
  private final AccountRepository accountRepository;

  /*
   * Manually credit interest to an account.
   */
  @Transactional
  public InterestCreditEntity creditInterest(String accountNumber, InterestCreditType creditType) {

    AccountEntity account =
        accountRepository
            .findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

    /*
     * Check account status.
     */
    if (!Boolean.TRUE.equals(account.getIsActive())) {

      throw new RuntimeException("Account is inactive");
    }

    /*
     * Current month.
     *
     * Example:
     * 2026-08
     */
    String currentMonth = YearMonth.now().toString();

    /*
     * Prevent duplicate interest.
     */
    boolean alreadyCredited =
        interestCreditRepository.existsByAccountAndCreditMonthAndCreditType(
            account, currentMonth, creditType);

    if (alreadyCredited) {

      throw new RuntimeException("Interest already credited for " + creditType + " for this month");
    }

    /*
     * Check balance.
     */
    if (account.getBalance() == null || account.getBalance().compareTo(BigDecimal.ZERO) <= 0) {

      throw new RuntimeException("Account balance must be greater than zero");
    }

    /*
     * Balance before interest.
     */
    BigDecimal balance = account.getBalance();

    /*
     * Get interest rate.
     */
    BigDecimal interestRate = getInterestRate(creditType);

    /*
     * Calculate interest.
     *
     * interest =
     * balance × rate / 100
     */
    BigDecimal interest = calculateInterest(balance, interestRate);

    /*
     * Add interest to account balance.
     */
    account.setBalance(balance.add(interest));

    /*
     * Save updated account.
     */
    accountRepository.save(account);

    /*
     * Create interest record.
     */
    InterestCreditEntity credit = new InterestCreditEntity();

    credit.setAccount(account);

    credit.setCreditType(creditType);

    credit.setInterestAmount(interest);

    credit.setBalanceAtCalculation(balance);

    credit.setInterestRate(interestRate);

    credit.setCreditMonth(currentMonth);

    /*
     * Save interest record.
     */
    return interestCreditRepository.save(credit);
  }

  /*
   * Automatically credit SAVINGS interest
   * to all active accounts at month end.
   */
  @Transactional
  @Scheduled(cron = "0 0 23 L * *", zone = "Asia/Kolkata")
  public void creditMonthlySavingsInterest() {

    String currentMonth = YearMonth.now().toString();

    /*
     * Get all active accounts.
     */
    List<AccountEntity> accounts = accountRepository.findByIsActiveTrue();

    for (AccountEntity account : accounts) {

      /*
       * Prevent duplicate credit.
       */
      boolean alreadyCredited =
          interestCreditRepository.existsByAccountAndCreditMonthAndCreditType(
              account, currentMonth, InterestCreditType.SAVINGS);

      if (alreadyCredited) {
        continue;
      }

      /*
       * Skip accounts with zero/null balance.
       */
      if (account.getBalance() == null || account.getBalance().compareTo(BigDecimal.ZERO) <= 0) {

        continue;
      }

      /*
       * Balance before interest.
       */
      BigDecimal balance = account.getBalance();

      /*
       * Savings interest rate.
       */
      BigDecimal interestRate = getInterestRate(InterestCreditType.SAVINGS);

      /*
       * Calculate interest.
       */
      BigDecimal interest = calculateInterest(balance, interestRate);

      /*
       * Credit interest.
       */
      account.setBalance(balance.add(interest));

      /*
       * Save account.
       */
      accountRepository.save(account);

      /*
       * Save interest transaction.
       */
      InterestCreditEntity credit = new InterestCreditEntity();

      credit.setAccount(account);

      credit.setCreditType(InterestCreditType.SAVINGS);

      credit.setInterestAmount(interest);

      credit.setBalanceAtCalculation(balance);

      credit.setInterestRate(interestRate);

      credit.setCreditMonth(currentMonth);

      interestCreditRepository.save(credit);
    }
  }

  /*
   * Calculate interest.
   */
  private BigDecimal calculateInterest(BigDecimal balance, BigDecimal interestRate) {

    return balance.multiply(interestRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
  }

  /*
   * Return interest rate according
   * to interest type.
   */
  private BigDecimal getInterestRate(InterestCreditType creditType) {

    return switch (creditType) {
      case SAVINGS -> new BigDecimal("0.50");

      case FIXED_DEPOSIT -> new BigDecimal("0.75");

      case RECURRING_DEPOSIT -> new BigDecimal("0.60");

      case BONUS_INTEREST -> new BigDecimal("1.00");

      case PROMOTIONAL_INTEREST -> new BigDecimal("1.25");
    };
  }

  /*
   * Get all interest credits.
   */
  public List<InterestCreditEntity> getAllInterestCredits() {

    return interestCreditRepository.findAll();
  }

  /*
   * Get interest history of an account.
   */
  public List<InterestCreditEntity> getAccountInterest(String accountNumber) {

    AccountEntity account =
        accountRepository
            .findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

    return interestCreditRepository.findByAccount(account);
  }

  /*
   * Get interest credits by month.
   */
  public List<InterestCreditEntity> getInterestByMonth(String creditMonth) {

    return interestCreditRepository.findByCreditMonth(creditMonth);
  }

  /*
   * Get interest credits by type.
   */
  public List<InterestCreditEntity> getInterestByType(InterestCreditType creditType) {

    return interestCreditRepository.findByCreditType(creditType);
  }
}
