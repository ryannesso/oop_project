package contracts;

import company.InsuranceCompany;
import objects.Person;
import objects.Vehicle;
import payment.ContractPaymentData;

public class SingleVehicleContract extends AbstractVehicleContract {
    private final Vehicle insuredVehicle;

    public SingleVehicleContract(String contractNumber, InsuranceCompany insurer,Person beneficiary, Person policyHolder, ContractPaymentData contractPaymentData, int coverageAmount,  Vehicle insuredVehicle) {
        super(contractNumber, insurer, beneficiary, policyHolder, contractPaymentData, coverageAmount);
        if(insuredVehicle == null || contractPaymentData == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        this.insuredVehicle = insuredVehicle;
    }

    public Vehicle getInsuredVehicle() {
        return insuredVehicle;
    }
}
