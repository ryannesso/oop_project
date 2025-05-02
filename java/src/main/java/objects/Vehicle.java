package objects;

public class Vehicle {
    private String licensePlate;
    private int originalValue;

    public Vehicle(String licensePlate, int originalValue) {
        this.licensePlate = licensePlate;
        this.originalValue = originalValue;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
    public int getOriginalValue() {
        return originalValue;
    }
}
