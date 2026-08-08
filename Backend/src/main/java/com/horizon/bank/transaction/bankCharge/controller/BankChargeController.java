package com.horizon.bank.transaction.bankCharge.controller;

import com.horizon.bank.transaction.bankCharge.entity.*;
import com.horizon.bank.transaction.bankCharge.service.BankChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.horizon.bank.transaction.bankCharge.entity.BankChargeEntity;
import java.util.List;

@RestController
@RequestMapping("/api/bank-charges")
@RequiredArgsConstructor
public class BankChargeController {

  private final BankChargeService bankChargeService;

  /**
   * Manually charge an account.
   *
   * <p>POST /api/bank-charges/charge/1234567890
   */
  @PostMapping("/charge/{accountNumber}")
  public ResponseEntity<BankChargeEntity> chargeAccount(@PathVariable String accountNumber) {

    return ResponseEntity.ok(bankChargeService.chargeAccount(accountNumber));
  }

  /**
   * Get all charges.
   *
   * <p>GET /api/bank-charges
   */
  @GetMapping
  public ResponseEntity<List<BankChargeEntity>> getAllCharges() {

    return ResponseEntity.ok(bankChargeService.getAllCharges());
  }

  /**
   * Get charges for an account.
   *
   * <p>GET /api/bank-charges/account/1234567890
   */
  @GetMapping("/account/{accountNumber}")
  public ResponseEntity<List<BankChargeEntity>> getAccountCharges(
      @PathVariable String accountNumber) {

    return ResponseEntity.ok(bankChargeService.getAccountCharges(accountNumber));
  }

  /**
   * Get charges for a month.
   *
   * <p>GET /api/bank-charges/month/2026-08
   */
  @GetMapping("/month/{chargeMonth}")
  public ResponseEntity<List<BankChargeEntity>> getChargesByMonth(
      @PathVariable String chargeMonth) {

    return ResponseEntity.ok(bankChargeService.getChargesByMonth(chargeMonth));
  }
}
