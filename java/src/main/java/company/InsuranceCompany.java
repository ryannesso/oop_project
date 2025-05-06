package company;

import contracts.AbstractContract;
import contracts.MasterVehicleContract;
import contracts.SingleVehicleContract;
import contracts.TravelContract;
import objects.Person;
import objects.Vehicle;
import payment.ContractPaymentData;
import payment.PaymentHandler;
import payment.PaymentInstance;
import payment.PremiumPaymentFrequency;

import java.time.LocalDateTime;
import java.util.*;

public class InsuranceCompany {
    private final Set<AbstractContract> contracts;
    private final PaymentHandler handler;
    private LocalDateTime currentTime;

    public InsuranceCompany(LocalDateTime currentTime) {
        if(currentTime == null) {
            throw new IllegalArgumentException("currentTime");
        }
        this.currentTime = currentTime;
        this.contracts = new LinkedHashSet<>();
        Map<AbstractContract, Set<PaymentInstance>> contractHistory = new LinkedHashMap<>();
        this.handler = new PaymentHandler(contractHistory, this);
    }
    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }
    public Set<AbstractContract> getContracts() {
        return contracts;
    }
    public PaymentHandler getHandler() {
        return handler;
    }
    public SingleVehicleContract insureVehicle(String contractNumber, Person beneficiary, Person policyHolder, int proposedPremium, PremiumPaymentFrequency proposedPaymentFrequency, Vehicle vehicleToInsure) {
        if(vehicleToInsure == null || proposedPaymentFrequency == null || currentTime == null) {
            throw new IllegalArgumentException("Vehicle does not exist");
        }
        //todo throws for all exceptions
        for(AbstractContract contract : contracts) {
            if(contract.getContractNumber().equals(contractNumber)) {
                throw new IllegalArgumentException("Contract already exists");
            }
        }
        double vehicleValue = vehicleToInsure.getOriginalValue();
        int periods = 12 / proposedPaymentFrequency.getMonthsValue();
        if(proposedPremium * periods < 0.02 * vehicleValue) {
            throw new IllegalArgumentException("Premium payment not enough");
        }
        ContractPaymentData contractPaymentData = new ContractPaymentData(proposedPremium, proposedPaymentFrequency, currentTime, 0);
        if (proposedPremium * periods < 0.02 * vehicleToInsure.getOriginalValue()) {
            throw new IllegalArgumentException("Proposed premium is less than the required 2% of the vehicle value");
        }
        int coverageAmount = vehicleToInsure.getOriginalValue() / 2;
        SingleVehicleContract contract = new SingleVehicleContract(contractNumber, this, beneficiary, policyHolder, contractPaymentData, coverageAmount, vehicleToInsure);

        contracts.add(contract);
        policyHolder.addContract(contract);
        return contract;

        //todo все аргументы должны быть валидные, если нет то исклбчение
    }
    public TravelContract insurePersons(String contractNumber, Person policyHolder, int proposedPremium, PremiumPaymentFrequency proposedPaymentFrequency, Set<Person> personsToInsure ) {
        for(AbstractContract contract : contracts) {
            if(contract.getContractNumber().equals(contractNumber)) {
                throw new IllegalArgumentException("Contract already exists");
            }

        }
        int insCount = personsToInsure.size();
        if(proposedPremium <= 5 * insCount) {
            throw new IllegalArgumentException("Premium payment not enough");
        }
        int coverageAmount = 10 *  insCount;
        ContractPaymentData contractPaymentData = new ContractPaymentData(proposedPremium, proposedPaymentFrequency, currentTime, 0);
        TravelContract travelContract = new TravelContract(
                contractNumber,
                this,
                policyHolder,
                contractPaymentData,
                coverageAmount,
                personsToInsure
        );
        contracts.add(travelContract);
        policyHolder.addContract(travelContract);
        return travelContract;
    }
    public MasterVehicleContract createMasterVehicleContract(String contractNumber, Person beneficiary, Person policyHolder) {
        for(AbstractContract contract : contracts) {
            if (contract.getContractNumber().equals(contractNumber)) {
                throw new IllegalArgumentException("Contract already exists");
            }
        }
        MasterVehicleContract masterVehicleContract = new MasterVehicleContract(
                contractNumber,
                this,
                beneficiary,
                policyHolder

        );
        contracts.add(masterVehicleContract);
        policyHolder.addContract(masterVehicleContract);
        return masterVehicleContract;
    }
    public void moveSingleVehicleContractToMasterVehicleContract(MasterVehicleContract masterVehicleContract, SingleVehicleContract singleVehicleContract) {

    }
    public void chargePremiumOnContracts() {
        for(AbstractContract contract : contracts) {
            if(contract.isActive()) {
                contract.updateBalance();
            }
        }
    }
    public void chargePremiumOnContract(MasterVehicleContract contract) {
        Set<SingleVehicleContract> child = contract.getChildContracts();
        for(SingleVehicleContract singleVehicleContract : child) {
            chargePremiumOnContract(singleVehicleContract);
        }
    }
    public void chargePremiumOnContract(AbstractContract contract) {
        while(contract.getContractPaymentData().getNextPaymentTime().isBefore(currentTime)) {
            if(contract.getContractPaymentData().getNextPaymentTime().isEqual(currentTime) &&
                    contract.getContractPaymentData().getNextPaymentTime().isBefore(currentTime)) {
                int balance = contract.getContractPaymentData().getOutstandingBalance();
                balance += contract.getContractPaymentData().getPremium();
                contract.getContractPaymentData().setPremium(balance);
                contract.getContractPaymentData().updateNextPaymentTime();
                if(contract.getContractPaymentData().getNextPaymentTime().isAfter(currentTime)) {
                    break;
                }
            }
        }
        //todo заменить получение ContractPaymentData из метода вызова на создание объекта

    }
    public void processClaim(TravelContract travelContract, Set<Person> affectedPersons) {
        if(travelContract == null || affectedPersons == null ||  affectedPersons.isEmpty()) {
            throw new IllegalArgumentException("Travel contract or persons cannot be empty");

        }
        if(!travelContract.isActive()) {
            throw new IllegalArgumentException("Travel not active");
            //todo InvalidContractsException
        }
        int payoutAmount = travelContract.getCoverageAmount() / affectedPersons.size();
        for (Person person : affectedPersons) {
            person.payout(payoutAmount);
        }
        travelContract.setInactive();

    }
    public void processClaim(SingleVehicleContract singleVehicleContract, int expectedDamages) {
        if(singleVehicleContract == null || expectedDamages <= 0) {
            throw new IllegalArgumentException("Invalid request");
        }
        if(!singleVehicleContract.isActive()) {
            throw new IllegalArgumentException("Invalid request");
            //todo InvalidContractExeption UML
        }
        if(expectedDamages >= 0.7 * singleVehicleContract.getInsuredVehicle().getOriginalValue()) {
            singleVehicleContract.setInactive();
        }
        if(singleVehicleContract.getBeneficiary() != null) {
            singleVehicleContract.getBeneficiary().payout(singleVehicleContract.getCoverageAmount());
        }
        else {
            singleVehicleContract.getPolicyHolder().payout(singleVehicleContract.getCoverageAmount());
        }

        //todo заменить получение бенефициара на объект
    }

}

