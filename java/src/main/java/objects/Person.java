package objects;

import contracts.AbstractContract;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

public class Person {


    private final String id;
    private final LegalForm legalForm;
    private int paidOutAmount;
    private final Set<AbstractContract> contracts;

    public Person(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be null or empty");
        }
        this.id = id;
        if (isValidBirthNumber(id)) {
            this.legalForm = LegalForm.NATURAL;
        }else if (isValidRegistrationNumber(id)) {
            this.legalForm = LegalForm.LEGAL;
        }
        else {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
        this.contracts = new LinkedHashSet<>();
        this.paidOutAmount = 0;
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
        if (contract == null) {
            throw new IllegalArgumentException("contract cannot be null");
        }
        contracts.add(contract);

    }

    public void payout(int paidOutAmount) {
        if (paidOutAmount <= 0) {
            throw new IllegalArgumentException("paidOutAmount cannot be negative");
        }
        this.paidOutAmount += paidOutAmount;
    }

    public boolean isValidBirthNumber(String birthNumber) {
        if (birthNumber == null) return false;

        if (!birthNumber.chars().allMatch(Character::isDigit)) return false;

        int length = birthNumber.length();
        if (length != 9 && length != 10) return false;

        int rr = Integer.parseInt(birthNumber.substring(0, 2));
        int mm = Integer.parseInt(birthNumber.substring(2, 4));
        int dd = Integer.parseInt(birthNumber.substring(4, 6));

        if (length == 9 && rr > 53) return false;
        if (mm > 50) mm -= 50;
        if (mm < 1 || mm > 12) return false;

        int year;
        if (length == 9) {
            year = rr + 1900;
        } else {
            year = rr + 2000;
        }
        try {
            LocalDate.of(year, mm, dd);
        } catch (Exception e) { return false;}

        if (length == 10) {
            int check = 0;
            for (int i = 0; i < 10; i++) {
                int digit = Character.getNumericValue(birthNumber.charAt(i));
                check += (i % 2 == 0 ? 1 : -1) * digit;
            }
            if (check % 11 != 0) return false;
        }
        return true;
    }

    public boolean isValidRegistrationNumber(String registrationNumber) {
        if (registrationNumber == null) return false;
        if (!registrationNumber.chars().allMatch(Character::isDigit)) return false;
        int length = registrationNumber.length();

        if (length == 6 || length == 8) {
            return true;
        }
        else {
            return false;
        }
    }

}
