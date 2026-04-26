package com.p2p.service;

import com.p2p.domain.*;
import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager; // import Log4j API
import org.apache.logging.log4j.Logger; // import Logger class

public class LoanService {

    // Membuat logger untuk mencatat aktivitas LoanService
    private static final Logger logger = LogManager.getLogger(LoanService.class);

    public Loan createLoan(Borrower borrower, BigDecimal amount) {
        logger.info("Memulai proses pengajuan loan"); // catat awal proses

        validateBorrower(borrower);
        validateAmount(amount);

        Loan loan = new Loan();

        if (borrower.getCreditScore() >= 600) {
            loan.approve();
            logger.info("Loan APPROVED - credit score: {}", borrower.getCreditScore()); // catat loan disetujui
        } else {
            loan.reject();
            logger.warn("Loan REJECTED - credit score rendah: {}", borrower.getCreditScore()); // catat loan ditolak
        }

        return loan;
    }

    private void validateBorrower(Borrower borrower) {
        if (!borrower.canApplyLoan()) {
            logger.error("Borrower tidak terverifikasi - loan ditolak"); // catat error borrower
            throw new IllegalArgumentException("Borrower not verified");
        }
        logger.info("Borrower terverifikasi"); // catat borrower valid
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("Amount tidak valid: {}", amount); // catat error amount
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        logger.info("Amount valid: {}", amount); // catat amount valid
    }
}