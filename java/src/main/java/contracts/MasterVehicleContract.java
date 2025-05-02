package contracts;

import company.InsuranceCompany;
import objects.Person;
import payments.ContractPaymentData;

import java.util.LinkedHashSet;

public class MasterVehicleContract extends AbstractVehicleContract {
    private final LinkedHashSet<SingleVehicleContract> childContracts;

    public MasterVehicleContract(String contractNumber, InsuranceCompany insurer, Person policyHolder, ContractPaymentData contractPaymentData, int coverageAmount, Person beneficiary, boolean isActive, LinkedHashSet<SingleVehicleContract> childContracts) {
        super(contractNumber, insurer, policyHolder, null, 0, beneficiary, isActive);
        if(policyHolder.getLegalForm) {}
        //todo finish LGEAL form for policyHolder
        this.childContracts = childContracts;
    }

    public LinkedHashSet<SingleVehicleContract> getChildContracts() {
        return childContracts;
    }

    void addChildContract(SingleVehicleContract childContract) {
        childContracts.add(childContract);
    }

    //todo UML!!!!!!



}
