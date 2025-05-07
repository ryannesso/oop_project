package contracts;

import company.InsuranceCompany;
import objects.Person;
import payment.ContractPaymentData;
import payment.PaymentHandler;

public abstract class AbstractContract {

    private final String contractNumber;
    protected final InsuranceCompany insurer;
    protected final Person policyHolder;
    protected final ContractPaymentData contractPaymentData;
    protected int coverageAmount;
    protected boolean isActive = true;

    public AbstractContract(String contractNumber, InsuranceCompany insuranceCompany, Person policyHolder, ContractPaymentData contractPaymentData, int coverageAmount) {
        if(insuranceCompany == null || policyHolder == null || coverageAmount < 0 || contractNumber == null ||  contractNumber.isEmpty()) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        //todo change this exceptions

        this.contractNumber = contractNumber;
        this.insurer = insuranceCompany;
        this.policyHolder = policyHolder;
        this.contractPaymentData = contractPaymentData;
        this.coverageAmount = coverageAmount;
        this.isActive = true;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public Person getPolicyHolder() {
        return policyHolder;
    }

    public InsuranceCompany getInsurer() {
        return insurer;
    }
    public int getCoverageAmount() {
        return coverageAmount;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setInactive() {
        this.isActive = false;
    }

    public void setCoverageAmount(int coverageAmount) {
        if(coverageAmount < 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        this.coverageAmount = coverageAmount;
    }


    public ContractPaymentData getContractPaymentData() {
        return contractPaymentData;
    }

    public void pay(int amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        PaymentHandler paymentHandler = this.insurer.getHandler();

        if(this instanceof MasterVehicleContract) {
            paymentHandler.pay((MasterVehicleContract) this, amount);
        }else {
            paymentHandler.pay((AbstractContract) this, amount);
        }
    }

    public void updateBalance() {
        insurer.chargePremiumOnContract(this);
    }
}
