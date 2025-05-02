package contracts;

import company.InsuranceCompany;
import objects.Person;
import payments.ContractPaymentData;

public abstract class AbstractContract {

    private final String contractNumber;
    private final InsuranceCompany insurer;
    private final Person policyHolder;
    private final ContractPaymentData contractPaymentData;
    private int coverageAmount;
    private boolean isActive;

    public AbstractContract(String contractNumber, InsuranceCompany insuranceCompany, Person policyHolder, ContractPaymentData contractPaymentData, int coverageAmount, boolean isActive) {
        if(insuranceCompany == null || policyHolder == null || contractPaymentData == null || coverageAmount < 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        //todo change this exceptions

        this.contractNumber = contractNumber;
        this.insurer = insuranceCompany;
        this.policyHolder = policyHolder;
        this.contractPaymentData = contractPaymentData;
        this.coverageAmount = coverageAmount;
        this.isActive = isActive;
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
        return isActive;
    }

    public void setInactive() {
        isActive = false;
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

    }

    public void updateBalance() {

    }
}
