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
        if (contract == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid contract or amount");
        }
        if (!contract.isActive() || contract.getChildContracts().isEmpty()) {
            throw new IllegalArgumentException("Invalid contract or amount");
        }

        // Сохраняем исходную сумму для записи в историю
        int initialAmount = amount;

        // Сначала покрываем все долги
        for (SingleVehicleContract child : contract.getChildContracts()) {
            if (!child.isActive()) continue;

            ContractPaymentData childData = child.getContractPaymentData();
            if (childData == null) continue;

            if (childData.getOutstandingBalance() > 0) {
                if (amount >= childData.getOutstandingBalance()) {
                    amount -= childData.getOutstandingBalance();
                    childData.setOutstandingBalance(0);
                } else {
                    childData.setOutstandingBalance(childData.getOutstandingBalance() - amount);
                    amount = 0;
                    break;
                }
            }
        }

        // Затем создаем переплаты циклически, пока есть средства
        while (amount > 0) {
            boolean anyPaymentApplied = false;

            for (SingleVehicleContract child : contract.getChildContracts()) {
                if (!child.isActive()) continue;

                ContractPaymentData childData = child.getContractPaymentData();
                if (childData == null) continue;

                int premium = childData.getPremium();
                if (amount >= premium) {
                    amount -= premium;
                    childData.setOutstandingBalance(childData.getOutstandingBalance() - premium);
                    anyPaymentApplied = true;
                } else {
                    childData.setOutstandingBalance(childData.getOutstandingBalance() - amount);
                    amount = 0;
                    anyPaymentApplied = true;
                    break;
                }
            }

            if (!anyPaymentApplied) break;
        }

        // Запись в историю платежей (используем исходную сумму)
        PaymentInstance paymentInstance = new PaymentInstance(
                insurer.getCurrentTime(),  // Используем текущее время страховой компании
                initialAmount
        );        paymentHistory.putIfAbsent(contract, new TreeSet<>());
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
