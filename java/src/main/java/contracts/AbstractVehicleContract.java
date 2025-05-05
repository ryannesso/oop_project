package contracts;

import company.InsuranceCompany;
import objects.Person;
import objects.Vehicle;
import payment.ContractPaymentData;

public abstract class AbstractVehicleContract extends AbstractContract {


    protected Person beneficiary;

    public AbstractVehicleContract(String contractNumber, InsuranceCompany insurer, Person beneficiary, Person policyHolder, ContractPaymentData contractPaymentData, int coverageAmount) {
        super(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount);
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
