package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Modern Java billing and invoice generation service.
 */
public final class BillingService {

    private static final double EMERGENCY_SURCHARGE = 0.50;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public double calculateBill(int consultationFee, boolean emergency, int testsCost) {
        if (consultationFee < 0 || testsCost < 0) {
            throw new IllegalArgumentException("Cost values cannot be negative.");
        }

        double subtotal = consultationFee + testsCost;
        double surcharge = emergency ? subtotal * EMERGENCY_SURCHARGE : 0.0;
        return subtotal + surcharge;
    }

    public String formatBillReceipt(int pid, String name, double amount) {
        return """

                =================================
                        HOSPITRON BILL
                =================================
                Patient ID   : %d
                Patient Name : %s
                ---------------------------------
                Total Amount : ₹%.2f
                =================================
                        Thank You Visit Again
                =================================
                """.formatted(pid, name, amount);
    }

    public String generateDetailedReceipt(int pid, String name, int consultationFee, int testsCost, boolean emergency) {
        double subtotal = consultationFee + testsCost;
        double surcharge = emergency ? subtotal * EMERGENCY_SURCHARGE : 0.0;
        double total = subtotal + surcharge;
        String date = LocalDate.now().format(DATE_FORMATTER);
        String careType = emergency ? "EMERGENCY / CRITICAL CARE" : "ROUTINE OUTPATIENT";

        return """
                =========================================================
                              HOSPITRON HEALTHCARE SYSTEM
                           Clinical & Inpatient Care Division
                                OFFICIAL MEDICAL INVOICE
                =========================================================
                Invoice Date : %s
                Patient ID   : %d
                Patient Name : %s
                Care Type    : %s
                ---------------------------------------------------------
                ITEM DESCRIPTION                         AMOUNT (₹)
                ---------------------------------------------------------
                1. Doctor Consultation Fee               ₹ %10.2f
                2. Diagnostics & Lab Tests               ₹ %10.2f
                ---------------------------------------------------------
                Subtotal                                 ₹ %10.2f
                Emergency Surcharge (%s)                 ₹ %10.2f
                =========================================================
                TOTAL AMOUNT PAYABLE                     ₹ %10.2f
                =========================================================
                 Payment Status: COMPLETED / VERIFIED
                 Thank you for choosing HospiTron. Wishing you health!
                =========================================================
                """.formatted(
                date,
                pid,
                name,
                careType,
                (double) consultationFee,
                (double) testsCost,
                subtotal,
                (emergency ? "50%" : "0%"),
                surcharge,
                total
        );
    }
}