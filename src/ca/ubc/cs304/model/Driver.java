package ca.ubc.cs304.model;
/**
 * The intent for this class is to update/store information about a single driver
 */
public class Driver {
    private final String driverID;
    private final String name;
    private final String licenseNumber;
    private final String sin;
    private final String email;
    private final String phoneNumber;

    public Driver(String driverID, String name, String licenseNumber, String sin, String email, String phoneNumber){
        this.driverID = driverID;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.sin = sin;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getDriverID() { return driverID; }

    public String getName() { return name; }

    public String getLicenseNumber() { return licenseNumber; }

    public String getSin() { return sin; }

    public String getEmail() {
        if (email == null) {
            return "";
        }
        return email;
    }

    public String getPhoneNumber() {
        if (phoneNumber == null) {
            return "";
        }
        return phoneNumber;
    }



}
