package service;

public class BillingService {

    private static final double EMERGENCY_SURCHARGE = 0.50;

    public double calculateBill(int consultationFee, boolean emergency, int testsCost) {

        if (consultationFee < 0 || testsCost < 0) {
            throw new IllegalArgumentException("Invalid cost values");
        }

        double total = consultationFee + testsCost;

        if (emergency) {
            total += total * EMERGENCY_SURCHARGE;
        }

        return total;
    }

    public String formatBillReceipt(int pid, String name, double amount) {

        return "\n=================================\n"
                + "        HOSPITRON BILL\n"
                + "=================================\n"
                + "Patient ID   : " + pid + "\n"
                + "Patient Name : " + name + "\n"
                + "---------------------------------\n"
                + "Total Amount : ₹" + String.format("%.2f", amount) + "\n"
                + "=================================\n"
                + "        Thank You Visit Again\n"
                + "=================================\n";
    }

    public String generateDetailedReceipt(int pid, String name, int consultationFee, int testsCost, boolean emergency) {
        double subtotal = consultationFee + testsCost;
        double surcharge = emergency ? subtotal * EMERGENCY_SURCHARGE : 0.0;
        double total = subtotal + surcharge;

        return "=========================================================\n"
             + "              HOSPITRON HEALTHCARE SYSTEM               \n"
             + "           Central University of Kashmir Wards           \n"
             + "                OFFICIAL MEDICAL INVOICE                \n"
             + "=========================================================\n"
             + String.format("Invoice Date : %s\n", java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")))
             + String.format("Patient ID   : %d\n", pid)
             + String.format("Patient Name : %s\n", name)
             + String.format("Care Type    : %s\n", (emergency ? "EMERGENCY / CRITICAL" : "ROUTINE OUTPATIENT"))
             + "---------------------------------------------------------\n"
             + "ITEM DESCRIPTION                         AMOUNT (₹)      \n"
             + "---------------------------------------------------------\n"
             + String.format("1. Doctor Consultation Fee               ₹ %10.2f\n", (double) consultationFee)
             + String.format("2. Diagnostics & Lab Tests               ₹ %10.2f\n", (double) testsCost)
             + "---------------------------------------------------------\n"
             + String.format("Subtotal                                 ₹ %10.2f\n", subtotal)
             + String.format("Emergency Surcharge (%s)                 ₹ %10.2f\n", (emergency ? "50%" : "0%"), surcharge)
             + "=========================================================\n"
             + String.format("TOTAL AMOUNT PAYABLE                     ₹ %10.2f\n", total)
             + "=========================================================\n"
             + " Payment Status: COMPLETED / VERIFIED                    \n"
             + " Thank you for choosing HospiTron. Wishing you health!  \n"
             + "=========================================================\n";
    }
}