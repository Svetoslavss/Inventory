package Payment;

import java.util.Date;

public class Payments {
    private double paidAmount;
    private Date paymentDate;
    private PaymentStatus status;

    public enum PaymentStatus{
        PENDING,
        COMPLETED,
        FAILED
    }

    public Payments(double paidAmount){
        this.paidAmount = paidAmount;
        this.paymentDate = new Date();
        this.status = PaymentStatus.PENDING;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public boolean validatePayment(double totalCost){
        if (paidAmount >= totalCost){
            status = PaymentStatus.COMPLETED;
            return true;
        } else {
            status = PaymentStatus.FAILED;
            return false;
        }
    }

    public String getPaymentDetails(){
        return "Payment Amount: $" + paidAmount + "\nPayment Date: " + paymentDate + "\nPayment Status: " + status;
    }
}
