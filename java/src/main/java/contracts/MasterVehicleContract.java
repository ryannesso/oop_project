package contracts;

import company.InsuranceCompany;
import objects.LegalForm;
import objects.Person;
import payment.ContractPaymentData;

import java.util.LinkedHashSet;
import java.util.Set;

public class MasterVehicleContract extends AbstractVehicleContract {
    private final Set<SingleVehicleContract> childContracts;

    public MasterVehicleContract(String contractNumber, InsuranceCompany insurer,Person beneficiary, Person policyHolder) {
        super(contractNumber, insurer, beneficiary, policyHolder,null, 0);
        if(policyHolder.getLegalForm() == LegalForm.NATURAL) {
            throw new IllegalArgumentException("Policy holder must be a legal person for MasterVehicleContract");
        }
        this.childContracts = new LinkedHashSet<>();
    }

    public Set<SingleVehicleContract> getChildContracts() {
        return childContracts;
    }

    @Override
    public boolean isActive() {
        boolean ok = this.childContracts.stream().anyMatch(AbstractContract::isActive);
        if(!ok) {
            return false;
        }
        return super.isActive();
    }

    public void requestAdditionOfChildContract(SingleVehicleContract childContract) {
        childContracts.add(childContract);
    }

    public void setInactive() {
        this.isActive = false;

        for(SingleVehicleContract childContract : childContracts) {
            childContract.setInactive();
        }
    }

    //todo UML!!!!!!

    //todo откл подсказки

}
