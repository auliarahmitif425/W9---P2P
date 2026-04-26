package com.p2p;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import com.p2p.domain.Borrower;
import com.p2p.service.LoanService;
import com.p2p.domain.Loan;

public class LoanServiceTest {
    // TC - 01
    @Test
    void shouldRejectLoanWhenBorrowerNotVerified() {
        Borrower borrower = new Borrower(false, 700);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount);
        });
    }

    // TC - 02
    @Test
    void shouldRejectLoanWhenAmountIsZeroOrNegative() {
    // =========================
    // Arrange (Initial Condition)
    // =========================
    // Borrower sudah lolos KYC
    Borrower borrower = new Borrower(true, 700);

    // Service untuk pengajuan loan
    LoanService loanService = new LoanService();

    // Amount tidak valid (0)
    BigDecimal amount = BigDecimal.valueOf(0);

    // =========================
    // Act & Assert
    // =========================
    // Sistem harus lempar exception karena amount = 0
    assertThrows(IllegalArgumentException.class, () -> {
        loanService.createLoan(borrower, amount);
    });
  }

   // TC-03
   @Test
   void shouldApproveLoanWhenCreditScoreHigh() {

    // =====================================================
    // SCENARIO:
    // Borrower sudah terverifikasi (KYC = true)
    // Credit score tinggi (>= 600)
    // Maka sistem harus APPROVE loan
    // =====================================================

    // =========================
    // Arrange (Initial Condition)
    // =========================
    // Borrower sudah lolos KYC, credit score tinggi
    Borrower borrower = new Borrower(true, 700);

    // Service untuk pengajuan loan
    LoanService loanService = new LoanService();

    // Jumlah pinjaman valid
    BigDecimal amount = BigDecimal.valueOf(1000);

    // =========================
    // Act (Action)
    // =========================
    Loan loan = loanService.createLoan(borrower, amount);
    // =========================
    // Assert (Expected Result)
    // =========================
    assertEquals(Loan.Status.APPROVED, loan.getStatus());
  }

  // TC-04
  @Test
  void shouldRejectLoanWhenCreditScoreLow() {

    // =====================================================
    // SCENARIO:
    // Borrower sudah terverifikasi (KYC = true)
    // Credit score rendah (< 600)
    // Maka sistem harus REJECT loan
    // =====================================================

    // =========================
    // Arrange (Initial Condition)
    // =========================
    // Borrower sudah lolos KYC, credit score rendah
    Borrower borrower = new Borrower(true, 500);

    // Service untuk pengajuan loan
    LoanService loanService = new LoanService();

    // Jumlah pinjaman valid
    BigDecimal amount = BigDecimal.valueOf(1000);

    // =========================
    // Act (Action)
    // =========================
    Loan loan = loanService.createLoan(borrower, amount);

    // =========================
    // Assert (Expected Result)
    // =========================
    assertEquals(Loan.Status.REJECTED, loan.getStatus());
 }
}