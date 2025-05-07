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
        // 1. Проверяем базовую активность (флаг isActive)
        if (!super.isActive()) {
            return false;
        }

        // 2. Если нет дочерних контрактов - неактивен
        if (this.childContracts.isEmpty()) {
            return false;
        }

        // 3. Активен, если хотя бы один дочерний контракт активен
        for (SingleVehicleContract child : this.childContracts) {
            if (child.isActive()) {
                return true;
            }
        }

        return false;
    }

    public void requestAdditionOfChildContract(SingleVehicleContract childContract) {
        childContracts.add(childContract);
    }

    @Override
    public void setInactive() {
        super.setInactive(); // Деактивируем сам мастер-контракт
        this.childContracts.forEach(AbstractContract::setInactive); // И все дочерние
    }
    //todo UML!!!!!!

    //todo откл подсказки

}
