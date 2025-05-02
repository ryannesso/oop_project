package payments;

import java.time.LocalDateTime;

public class ContractPaymentData {
    private int premium;
    private PremiumPaymentFrequency premiumPaymentFrequency;
    private LocalDateTime nextPaymentTime;
    private int outstandingBalance;

    public ContractPaymentData(int premium, PremiumPaymentFrequency frequency, LocalDateTime nextPaymentTime, int outstandingBalance) {
        if(premium <= 0) {
            throw new IllegalArgumentException("premium cannot be negative");
        }
        if(frequency == null) {
            throw new IllegalArgumentException("frequency cannot be null");
        }
        if(nextPaymentTime == null) {
            throw new IllegalArgumentException("nextPaymentTime cannot be null");
        }
        this.premium = premium;
        this.premiumPaymentFrequency = frequency;
        this.nextPaymentTime = nextPaymentTime;
        this.outstandingBalance = outstandingBalance;
    }

    public int getPremium() {
        return premium;
    }
    public void setPremium(int premium) {
        if(premium > 0) this.premium = premium;
    }
    public void setOutstandingBalance(int outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
    public int getOutstandingBalance() {
        return outstandingBalance;
    }
    public void setPremiumPaymentFrequency(PremiumPaymentFrequency premiumPaymentFrequency) {
        this.premiumPaymentFrequency = premiumPaymentFrequency;
    }
    public PremiumPaymentFrequency getPremiumPaymentFrequency() {
        return premiumPaymentFrequency;
    }
    public LocalDateTime getNextPaymentTime() {
        return nextPaymentTime;
    }
    public void updateNextPaymentTime(LocalDateTime nextPaymentTime) {
        int addmonths = premiumPaymentFrequency.getMonthsValue();
        this.nextPaymentTime = this.nextPaymentTime.plusMonths(addmonths);
    }
}
