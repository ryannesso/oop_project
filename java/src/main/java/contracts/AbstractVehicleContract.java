package contracts;

import company.InsuranceCompany;
import objects.Person;
import payments.ContractPaymentData;

public abstract class AbstractVehicleContract extends AbstractContract {


    protected Person beneficiary;

    public AbstractVehicleContract(String contractNumber, InsuranceCompany insurer, Person policyHolder, ContractPaymentData contractPaymentData, int coverageAmount, Person beneficiary, boolean isActive) {
        super(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount, isActive);
        validateBeneficiary(beneficiary, policyHolder);
        this.beneficiary = beneficiary;

    }

    public Person getBeneficiary() {
        return beneficiary;
    }
    public void setBeneficiary(Person newBeneficiary) {
        validateBeneficiary(getPolicyHolder(), newBeneficiary);
        this.beneficiary = newBeneficiary;
    }

    private static void validateBeneficiary(Person policyHolder, Person beneficiary) {
        if(beneficiary != null && beneficiary.equals(policyHolder)) {
            throw new IllegalArgumentException("Invalid beneficiary");
        }
    }
}
