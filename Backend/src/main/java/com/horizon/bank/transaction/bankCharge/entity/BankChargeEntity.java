package com.horizon.bank.transaction.bankCharge.entity;

import com.horizon.bank.accounts.entity.AccountEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(
    name = "bank_charges",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_account_charge_month",
          columnNames = {"account_number", "charge_month"})
    })
public class BankChargeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  /** Account which was charged. */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "account_number", referencedColumnName = "account_number", nullable = false)
  private AccountEntity account;

  /** Charge amount: ₹50 - ₹100 */
  @Column(name = "amount", nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  /** Example: 2026-08 */
  @Column(name = "charge_month", nullable = false)
  private String chargeMonth;

  /** When the charge was applied. */
  @CreationTimestamp
  @Column(name = "charged_at")
  private Long chargedAt;
}
