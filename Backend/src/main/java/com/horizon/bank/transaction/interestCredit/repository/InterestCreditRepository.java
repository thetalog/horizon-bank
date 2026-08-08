package com.horizon.bank.transaction.interestCredit.repository;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.transaction.interestCredit.entity.InterestCreditEntity;
import com.horizon.bank.transaction.interestCredit.enums.InterestCreditType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestCreditRepository extends JpaRepository<InterestCreditEntity, String> {

  /*
   * Check whether this account already received
   * this type of interest during the month.
   */
  boolean existsByAccountAndCreditMonthAndCreditType(
      AccountEntity account, String creditMonth, InterestCreditType creditType);

  /*
   * Get all interest records of an account.
   */
  List<InterestCreditEntity> findByAccount(AccountEntity account);

  /*
   * Get all interest records for a month.
   */
  List<InterestCreditEntity> findByCreditMonth(String creditMonth);

  /*
   * Get all records of a specific interest type.
   */
  List<InterestCreditEntity> findByCreditType(InterestCreditType creditType);
}
