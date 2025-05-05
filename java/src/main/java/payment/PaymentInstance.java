package payment;

import java.time.LocalDateTime;

public class PaymentInstance implements Comparable<PaymentInstance> {
    private LocalDateTime paymentTime;
    private int paymentAmount;

    public PaymentInstance(LocalDateTime paymentTime, int paymentAmount) {
        this.paymentTime = paymentTime;
        this.paymentAmount = paymentAmount;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }
    public int getPaymentAmount() {
        return paymentAmount;
    }

    @Override
    public int compareTo(PaymentInstance paymentInstance) {
        return this.paymentTime.compareTo(paymentInstance.paymentTime);
    }
}
