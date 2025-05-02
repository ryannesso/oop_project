package contracts;

import company.InsuranceCompany;
import objects.LegalForm;
import objects.Person;
import payments.ContractPaymentData;

import java.util.LinkedHashSet;

public class MasterVehicleContract extends AbstractVehicleContract {
    private final LinkedHashSet<SingleVehicleContract> childContracts;

    public MasterVehicleContract(String contractNumber, InsuranceCompany insurer, Person policyHolder, ContractPaymentData contractPaymentData, int coverageAmount, Person beneficiary, boolean isActive, LinkedHashSet<SingleVehicleContract> childContracts) {
        super(contractNumber, insurer, policyHolder, null, 0, beneficiary, isActive);
        if(policyHolder.getLegalForm() == LegalForm.NATURAL) {
            throw new IllegalArgumentException("Policy holder must be a legal person for MasterVehicleContract");
        }
        this.childContracts = childContracts;
    }

    public LinkedHashSet<SingleVehicleContract> getChildContracts() {
        return childContracts;
    }

    void requestAdditionOfChildContract(SingleVehicleContract childContract) {
        childContracts.add(childContract);
    }

    public void setInactive() {
        this.isActive = false;

        for(SingleVehicleContract childContract : childContracts) {
            childContract.setInactive();
        }
    }

    //todo UML!!!!!!



}
