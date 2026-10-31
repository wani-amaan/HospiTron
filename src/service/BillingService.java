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
}