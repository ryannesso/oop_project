package payment;

public enum PremiumPaymentFrequency {
    ANNUAL(12), SEMI_ANNUAL(6), QUARTERLY(3), MONTHLY(1);
    private int monthsValue;
    PremiumPaymentFrequency(int value) {
        this.monthsValue = value;
    }
    public int getValueInMonths() {
        return monthsValue;
    }
}
