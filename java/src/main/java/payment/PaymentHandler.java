package payment;

import company.InsuranceCompany;
import contracts.AbstractContract;
import contracts.MasterVehicleContract;
import contracts.SingleVehicleContract;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PaymentHandler {
    private Map<AbstractContract, Set<PaymentInstance>> paymentHistory;
    private final InsuranceCompany insurer;

    public PaymentHandler(Map<AbstractContract, Set<PaymentInstance>> paymentHistory, InsuranceCompany insurer) {
        this.paymentHistory = paymentHistory;
        this.insurer = insurer;
    }

    public Map<AbstractContract, Set<PaymentInstance>> getPaymentHistory() {
        return paymentHistory;
    }
    public void pay(MasterVehicleContract contract, int amount) {
        if(contract == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid contract or amount");
        }
        if(!contract.isActive() || !contract.getPolicyHolder().equals(this.insurer) || contract.getChildContracts().isEmpty()) {
            throw new IllegalArgumentException("Invalid contract or amount");
        }

        for(SingleVehicleContract child : contract.getChildContracts()) {
            ContractPaymentData contractPaymentData = contract.getContractPaymentData();
            if(contractPaymentData.getOutstandingBalance() > 0) {
                if (amount >= contractPaymentData.getOutstandingBalance()) {
                    amount -= contractPaymentData.getOutstandingBalance();
                    contractPaymentData.setOutstandingBalance(0);
                } else {
                    contractPaymentData.setOutstandingBalance(contractPaymentData.getOutstandingBalance() - amount);
                    amount = 0;
                }
            }

        }
        if(amount > 0) {
            for (SingleVehicleContract child : contract.getChildContracts()) {
                ContractPaymentData contractPaymentData = contract.getContractPaymentData();
                if(amount >= contractPaymentData.getPremium()) {
                    amount -= contractPaymentData.getPremium();
                    contractPaymentData.setOutstandingBalance(contractPaymentData.getOutstandingBalance() - contractPaymentData.getPremium());
                } else {
                    contractPaymentData.setOutstandingBalance(contractPaymentData.getOutstandingBalance() - amount);
                    amount = 0;
                }
                if (amount <= 0) {
                    break;
                }
            }
        }
        PaymentInstance paymentInstance = new PaymentInstance(LocalDateTime.now(), amount);
        paymentHistory.putIfAbsent(contract, new TreeSet<>());
        paymentHistory.get(contract).add(paymentInstance);
    }
    public void pay(AbstractContract contract, int amount) {
            if(contract == null || amount <= 0) {
                throw new IllegalArgumentException("Invalid contract or amount");
            }
            if(!contract.isActive() || !contract.getPolicyHolder().equals(this.insurer)) {
                throw new IllegalArgumentException("Invalid contract or amount");
            }
            ContractPaymentData contractPaymentData = contract.getContractPaymentData();
            if(contractPaymentData.getOutstandingBalance() > 0) {
                if(amount >= contractPaymentData.getOutstandingBalance()) {
                    amount -=  contractPaymentData.getOutstandingBalance();
                    contractPaymentData.setOutstandingBalance(0);
                }
                else {
                    contractPaymentData.setOutstandingBalance(contractPaymentData.getOutstandingBalance() - amount);
                    amount = 0;
                }
                PaymentInstance paymentInstance = new PaymentInstance(LocalDateTime.now(), amount);
                paymentHistory.putIfAbsent(contract, new HashSet<>());
                paymentHistory.get(contract).add(paymentInstance);
            }
    }
}
