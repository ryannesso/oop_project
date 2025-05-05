package contracts;

import company.InsuranceCompany;
import objects.Person;
import payment.ContractPaymentData;

import java.util.Set;

public class TravelContract extends AbstractContract {
    private Set<Person> insuredPerson;

    public TravelContract(String contractNumber, InsuranceCompany insuranceCompany, Person person, ContractPaymentData contractPaymentData, int coverageAmount, Set<Person> personsToInsure) {
        super(contractNumber, insuranceCompany, person, contractPaymentData, coverageAmount);
        if(personsToInsure == null && personsToInsure.isEmpty()) {
            throw new IllegalArgumentException("Null or empty persons to insure");
        }
        if(contractPaymentData == null) {
            throw new IllegalArgumentException("Null ContractPaymentData");
        }
        this.insuredPerson = personsToInsure;
    }
    public Set<Person> getInsuredPerson() {
        return insuredPerson;
    }
}
