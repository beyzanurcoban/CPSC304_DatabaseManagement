package ca.ubc.cs304.model;
/**
 * The intent for this class is to update/store information about a single location
 */
public class Location {
    private final String locationID;
    private final String city;
    private final String province;
    private final String streetName;
    private final String streetNumber;
    private final String unitNumber;
    private final String postalCode;
    private final String note;

    public Location(String locationID, String city, String province, String streetName, String streetNumber, String unitNumber, String postalCode, String note){
        this.locationID = locationID;
        this.city = city;
        this.province = province;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.unitNumber = unitNumber;
        this.postalCode = postalCode;
        this.note = note;
    }

    public String getLocationID() {
        return locationID;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getNote() {
        return note;
    }
}
