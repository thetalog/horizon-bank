package com.horizon.bank.transaction.interestCredit.entity;

import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.transaction.interestCredit.enums.InterestCreditType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(
    name = "interest_credits",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_account_interest_month_type",
          columnNames = {"account_number", "credit_month", "credit_type"})
    })
public class InterestCreditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  /*
   * Account receiving the interest.
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "account_number", referencedColumnName = "account_number", nullable = false)
  private AccountEntity account;

  /*
   * Type of interest.
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "credit_type", nullable = false)
  private InterestCreditType creditType;

  /*
   * Interest amount credited.
   */
  @Column(name = "interest_amount", nullable = false, precision = 15, scale = 2)
  private BigDecimal interestAmount;

  /*
   * Account balance before interest was credited.
   */
  @Column(name = "balance_at_calculation", nullable = false, precision = 15, scale = 2)
  private BigDecimal balanceAtCalculation;

  /*
   * Interest rate in percentage.
   *
   * Example:
   * 0.50 = 0.50%
   */
  @Column(name = "interest_rate", nullable = false, precision = 5, scale = 2)
  private BigDecimal interestRate;

  /*
   * Example:
   * 2026-08
   */
  @Column(name = "credit_month", nullable = false)
  private String creditMonth;

  /*
   * Timestamp when interest was credited.
   */
  @CreationTimestamp
  @Column(name = "credited_at")
  private Long creditedAt;
}
