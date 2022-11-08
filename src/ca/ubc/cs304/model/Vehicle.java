package ca.ubc.cs304.model;
/**
 * The intent for this class is to update/store information about a single vehicle
 */
public class Vehicle {
    private final String vehicleID;
    private final String color;
    private final String type;
    private final String licensePlate;
    private final String description;
    private final String cargoCapacity;

    public Vehicle(String vehicleID, String color, String type, String licensePlate, String description, String cargoCapacity){
        this.vehicleID = vehicleID;
        this.color = color;
        this.type = type;
        this.licensePlate = licensePlate;
        this.description = description;
        this.cargoCapacity = cargoCapacity;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getDescription() {
        return description;
    }

    public String getCargoCapacity() {
        return cargoCapacity;
    }
}
