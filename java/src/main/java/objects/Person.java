package objects;

import company.InsuranceCompany;
import contracts.AbstractContract;
import contracts.AbstractVehicleContract;

import java.time.LocalDate;
import java.time.Year;
import java.util.LinkedHashSet;
import java.util.Set;

public class Person {


    private String id;
    private LegalForm legalForm;
    private int paidOutAmount;
    private Set<AbstractContract> contracts;

    public Person(String id) {
        this.id = id;
        if (isValidBirthNumber(id)) {
            this.legalForm = LegalForm.NATURAL;
        } else if (isValidRegistrationNumber(id)) {
            this.legalForm = LegalForm.LEGAL;
        } else {
            throw new IllegalArgumentException("Illegal person id");
        }
        this.contracts = new LinkedHashSet<>();
        this.paidOutAmount = 0;
    }

    public boolean isValidBirthNumber(String birthNumber) {
        if(birthNumber == null) {
            return false;
        }

        try {
            int check = Integer.parseInt(birthNumber);
        }
        catch (Exception e) {
            return false;
        }

        int idLength = birthNumber.length();
        if(idLength != 6 &&  idLength != 8 && idLength != 9 && idLength != 10) {
            return false;
        }

        int rr = Integer.parseInt(birthNumber.substring(0, 2));
        int rcYearPast = rr + 1900;
        int rcYearFuture = rr + 2000;
        int rcMonth = Integer.parseInt(id.substring(2, 4));
        if(rcMonth > 50) {
            rcMonth -= 50;
        }
        int rcDay = Integer.parseInt(birthNumber.substring(4, 6));
        if(idLength == 9) {
            if(rr > 53) {
                return false;
            }
            try {
                LocalDate.of(rcYearPast, rcMonth, rcDay);
            }
            catch (Exception e) {
                return false;
            }
            return true;
        }
        if(idLength == 10) {

            if(rr <= 53) {
                return false;
            }
            int check = 0;
            for(int i = 0; i < 10; i++) {
                int digit = Character.getNumericValue(birthNumber.charAt(i));
                check += (i % 2 == 0 ? 1 : -1) * digit;
            }
            if(check % 11 != 0) {
                return false;
            }
            try {
                LocalDate.of(rcYearFuture, rcMonth, rcDay);
            }
            catch (Exception e) {
                return false;
            }
        }
        return true;
    }
    public boolean isValidRegistrationNumber(String registrationNumber) {
        if(registrationNumber == null) {
            return false;
        }
        try {
            int check = Integer.parseInt(registrationNumber);
        }
        catch (Exception e) {
            return false;
        }

        int idLength = registrationNumber.length();
        if(idLength != 6 &&  idLength != 8 && idLength != 9 && idLength != 10) {
            return false;
        }

        int rr = Integer.parseInt(registrationNumber.substring(0, 2));
        int rcYearPast = rr + 1900;
        int rcYearFuture = rr + 2000;
        int rcMonth = Integer.parseInt(registrationNumber.substring(2, 4));
        if(rcMonth > 50) {
            rcMonth -= 50;
        }
        if(idLength == 6 || idLength == 8) {
            try {
                int ICO = Integer.parseInt(registrationNumber);
            }
            catch (Exception e) {
                return false;
            }

        }
        return true;
    }
    public String getId() {
        return id;
    }
    public int getPaidOutAmount() {
        return paidOutAmount;
    }
    public LegalForm getLegalForm() {
        return legalForm;
    }
    public Set<AbstractContract> getContracts() {
        return contracts;
    }
    public void addContract(AbstractContract contract) {
        if(contract == null) {
            throw new IllegalArgumentException("contract cannot be null");
        }
        contracts.add(contract);

    }
    public void payout(int paidOutAmount) {
        if(paidOutAmount <= 0) {
            throw new IllegalArgumentException("paidOutAmount cannot be negative");
        }
        this.paidOutAmount += paidOutAmount;
    }
}
