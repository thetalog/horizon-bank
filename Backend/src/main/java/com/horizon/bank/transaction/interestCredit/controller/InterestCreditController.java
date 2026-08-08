package com.horizon.bank.transaction.interestCredit.controller;

import com.horizon.bank.transaction.interestCredit.entity.InterestCreditEntity;
import com.horizon.bank.transaction.interestCredit.enums.InterestCreditType;
import com.horizon.bank.transaction.interestCredit.service.InterestCreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interest-credits")
@RequiredArgsConstructor
public class InterestCreditController {

  private final InterestCreditService interestCreditService;

  /*
   * Manually credit interest.
   *
   * POST
   * /api/interest-credits/credit/1234567890?type=SAVINGS
   */
  @PostMapping("/credit/{accountNumber}")
  public ResponseEntity<InterestCreditEntity> creditInterest(
      @PathVariable String accountNumber, @RequestParam InterestCreditType type) {

    return ResponseEntity.ok(interestCreditService.creditInterest(accountNumber, type));
  }

  /*
   * Get all interest credits.
   *
   * GET
   * /api/interest-credits
   */
  @GetMapping
  public ResponseEntity<List<InterestCreditEntity>> getAllInterestCredits() {

    return ResponseEntity.ok(interestCreditService.getAllInterestCredits());
  }

  /*
   * Get interest history of an account.
   *
   * GET
   * /api/interest-credits/account/1234567890
   */
  @GetMapping("/account/{accountNumber}")
  public ResponseEntity<List<InterestCreditEntity>> getAccountInterest(
      @PathVariable String accountNumber) {

    return ResponseEntity.ok(interestCreditService.getAccountInterest(accountNumber));
  }

  /*
   * Get interest credits for a month.
   *
   * GET
   * /api/interest-credits/month/2026-08
   */
  @GetMapping("/month/{creditMonth}")
  public ResponseEntity<List<InterestCreditEntity>> getInterestByMonth(
      @PathVariable String creditMonth) {

    return ResponseEntity.ok(interestCreditService.getInterestByMonth(creditMonth));
  }

  /*
   * Get interest credits by type.
   *
   * GET
   * /api/interest-credits/type/SAVINGS
   */
  @GetMapping("/type/{creditType}")
  public ResponseEntity<List<InterestCreditEntity>> getInterestByType(
      @PathVariable InterestCreditType creditType) {

    return ResponseEntity.ok(interestCreditService.getInterestByType(creditType));
  }
}
