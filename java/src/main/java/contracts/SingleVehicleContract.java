package contracts;

import company.InsuranceCompany;
import objects.Person;
import objects.Vehicle;
import payments.ContractPaymentData;

public class SingleVehicleContract extends AbstractVehicleContract {
    private final Vehicle insuredVehicle;

    public SingleVehicleContract(String contractNumber, InsuranceCompany insurer, Person policyHolder, ContractPaymentData contractPaymentData, int coverageAmount, Person beneficiary, boolean isActive, Vehicle insuredVehicle) {
        super(contractNumber, insurer, policyHolder, contractPaymentData, coverageAmount, beneficiary, isActive);
        this.insuredVehicle = insuredVehicle;
    }

    public Vehicle getInsuredVehicle() {
        return insuredVehicle;
    }

    public static void validateConst(ContractPaymentData contractPaymentData, Vehicle insuredVehicle) {
        if(insuredVehicle == null || contractPaymentData == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }

    }
}
